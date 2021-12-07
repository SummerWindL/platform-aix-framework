package com.platform.aix.demo;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Advance
 * @date 2021年10月12日 17:12
 * @since V1.0.0
 */
public class DBTest {
    private static Connection conn;

    private final static String CRLF = "\n";
    private final static int MAX_WRITE_ROWS = 10000;//一次性最大写入行数
    private DBTest(){
        try {
            throw new Exception("can't be instance");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getInstance(){
        if(conn == null){
            initConnection();
        }
        return conn;
    }
    private static void initConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:orcl";
            String user = "tams0509";
            String password = "tams0509";
            conn = DriverManager.getConnection(url, user, password);// 获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     * 测试连接成功
     */
    public static void main(String[] args) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "DECLARE  \n" +
                "  clobValue RSK_ISUP_INDICATOR_PARA_DTL.CONTENT%TYPE;  \n" +
                "BEGIN  \n" +
                "  clobValue := 'WITH temp AS (\n" +
                "SELECT\n" +
                "  m.FINPROD_ID,\n" +
                "  m.FINPROD_TYPE2,\n" +
                "  CASE\n" +
                "    WHEN m.FINPROD_TYPE2 = ''F01'' THEN m.ISSUER\n" +
                "    ELSE m.FINANCIER\n" +
                "  END org_code\n" +
                "FROM\n" +
                "  FIN_PRODUCT m\n" +
                "INNER JOIN FIN_PRODUCT_TYPE fpt ON\n" +
                "  m.FINPROD_ID = fpt.FINPROD_ID\n" +
                "  AND fpt.BRANCH = 0\n" +
                "  AND ( (fpt.TYPE_8 = ''F06''\n" +
                "  AND fpt.TYPE_6 IN(''P01'',\n" +
                "  ''P11''))\n" +
                "  OR ( fpt.TYPE_8 = ''F01''\n" +
                "  AND fpt.TYPE_6 = ''B18''))),\n" +
                "finprod AS (\n" +
                "SELECT\n" +
                "  fp.FINPROD_ID,\n" +
                "  CASE\n" +
                "    fp.FINPROD_TYPE2\n" +
                "    WHEN ''F01'' THEN ISSUER\n" +
                "    WHEN ''F18'' THEN financier\n" +
                "    ELSE ''null''\n" +
                "  END AS ORGID\n" +
                "FROM\n" +
                "  FIN_PRODUCT fp\n" +
                "INNER JOIN FIN_PRODUCT_TYPE fpt ON\n" +
                "  fp.FINPROD_ID = fpt.FINPROD_ID\n" +
                "  AND fpt.BRANCH = 0\n" +
                "  AND ( (fpt.TYPE_8 = ''F06''\n" +
                "  AND fpt.TYPE_6 IN(''P01'',\n" +
                "  ''P11''))\n" +
                "  OR ( fpt.TYPE_8 = ''F01''\n" +
                "  AND fpt.TYPE_6 = ''B18''))) \n" +
                "  --  \n" +
                " SELECT\n" +
                "  RKEY,\n" +
                "  SUM(RVALUE) RVALUE\n" +
                "FROM\n" +
                "  (\n" +
                "  SELECT\n" +
                "    NVL(SUM(NVL(DSC_COST_AMT, 0)+ NVL(TDY_FLOAT_INGPL, 0)+ NVL(TDY_INTINCEXP, 0)+ NVL(DELAY_PAY_AMT, 0)), 0) AS RVALUE ,\n" +
                "    m.PORTFOLIO_ID || '','' || g.org_code AS RKEY\n" +
                "  FROM\n" +
                "    PTL_SEC_VALUTION m\n" +
                "  INNER JOIN (\n" +
                "    SELECT\n" +
                "      FINPROD_ID,\n" +
                "      org_code,\n" +
                "      org_code org_code_union\n" +
                "    FROM\n" +
                "      temp\n" +
                "  UNION ALL\n" +
                "    SELECT\n" +
                "      DISTINCT c.FINPROD_ID,\n" +
                "      a.org_code,\n" +
                "      b.COR_ORGID org_code_union\n" +
                "    FROM\n" +
                "      temp a\n" +
                "    INNER JOIN INF_ORG_CORRELATION b ON\n" +
                "      a.org_code = b.ORG_NO\n" +
                "    INNER JOIN temp c ON\n" +
                "      c.org_code = b.COR_ORGID ) g ON\n" +
                "    m.FINPROD_ID = g.FINPROD_ID\n" +
                "  WHERE\n" +
                "    m.CDATE = DATE ''${valDate}''-1\n" +
                "  GROUP BY\n" +
                "    m.PORTFOLIO_ID,\n" +
                "    g.org_code\n" +
                "UNION ALL\n" +
                "  SELECT\n" +
                "    NVL(SUM(DECODE(n.PS, ''T01'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) -NVL(SUM(DECODE(n.PS, ''T02'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) AS RVALUE,\n" +
                "    m.PORTFOLIO_ID || '','' || t2.ORGID AS RKEY\n" +
                "  FROM\n" +
                "    TRD_PRODUCT_DEAL_ADD m\n" +
                "  INNER JOIN TRD_PRODUCT_DEAL n ON\n" +
                "    m.TRADE_ID = n.TRADE_ID\n" +
                "  INNER JOIN finprod t2 ON\n" +
                "    n.FINPROD_ID = t2.FINPROD_ID\n" +
                "    AND t2.ORGID IN (\n" +
                "    SELECT\n" +
                "      org_code\n" +
                "    FROM\n" +
                "      temp)\n" +
                "  WHERE\n" +
                "    n.TRADE_DATE = DATE ''${valDate}''\n" +
                "    AND n.PS IN (''T01'',\n" +
                "    ''T02'',\n" +
                "    ''T07'',\n" +
                "    ''T08'')\n" +
                "  GROUP BY\n" +
                "    m.PORTFOLIO_ID,t2.ORGID\n" +
                "UNION ALL\n" +
                "  SELECT\n" +
                "    NVL(SUM(DECODE(n.PS, ''T01'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) -NVL(SUM(DECODE(n.PS, ''T02'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) AS RVALUE,\n" +
                "    m.PORTFOLIO_ID || '','' || t2.ORGID AS RKEY\n" +
                "  FROM\n" +
                "    WFL_TRD_PRODUCT_DEAL_ADD m\n" +
                "  INNER JOIN WFL_TRD_PRODUCT_DEAL n ON\n" +
                "    m.TRADE_ID = n.TRADE_ID\n" +
                "    AND n.TRADE_STATUS != ''03''\n" +
                "  INNER JOIN finprod t2 ON\n" +
                "    n.FINPROD_ID = t2.FINPROD_ID\n" +
                "    AND t2.ORGID IN (\n" +
                "    SELECT\n" +
                "      org_code\n" +
                "    FROM\n" +
                "      temp)\n" +
                "  WHERE\n" +
                "    n.TRADE_DATE = DATE ''${valDate}''\n" +
                "    AND n.PS IN (''T01'',\n" +
                "    ''T02'')\n" +
                "  GROUP BY\n" +
                "    m.PORTFOLIO_ID,t2.ORGID\n" +
                "  UNION ALL\n" +
                "  ( SELECT\n" +
                "    NVL(SUM(NVL(DSC_COST_AMT, 0)+ NVL(TDY_FLOAT_INGPL, 0)+ NVL(TDY_INTINCEXP, 0)+ NVL(DELAY_PAY_AMT, 0)), 0) AS RVALUE ,\n" +
                "    ''fbfc72ca76454cb087476e8c86e12ff2,'' || g.org_code AS RKEY\n" +
                "  FROM\n" +
                "    PTL_SEC_VALUTION m\n" +
                "  INNER JOIN (\n" +
                "    SELECT\n" +
                "      FINPROD_ID,\n" +
                "      org_code,\n" +
                "      org_code org_code_union\n" +
                "    FROM\n" +
                "      temp\n" +
                "  UNION ALL\n" +
                "    SELECT\n" +
                "      DISTINCT c.FINPROD_ID,\n" +
                "      a.org_code,\n" +
                "      b.COR_ORGID org_code_union\n" +
                "    FROM\n" +
                "      temp a\n" +
                "    INNER JOIN INF_ORG_CORRELATION b ON\n" +
                "      a.org_code = b.ORG_NO\n" +
                "    INNER JOIN temp c ON\n" +
                "      c.org_code = b.COR_ORGID ) g ON\n" +
                "    m.FINPROD_ID = g.FINPROD_ID\n" +
                "  WHERE\n" +
                "    m.CDATE = DATE ''${valDate}''-1\n" +
                "  GROUP BY\n" +
                "    g.org_code\n" +
                "UNION ALL\n" +
                "  SELECT\n" +
                "    NVL(SUM(DECODE(n.PS, ''T01'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) -NVL(SUM(DECODE(n.PS, ''T02'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) AS RVALUE,\n" +
                "    ''fbfc72ca76454cb087476e8c86e12ff2,'' || t2.ORGID AS RKEY\n" +
                "  FROM\n" +
                "    TRD_PRODUCT_DEAL_ADD m\n" +
                "  INNER JOIN TRD_PRODUCT_DEAL n ON\n" +
                "    m.TRADE_ID = n.TRADE_ID\n" +
                "  INNER JOIN finprod t2 ON\n" +
                "    n.FINPROD_ID = t2.FINPROD_ID\n" +
                "    AND t2.ORGID IN (\n" +
                "    SELECT\n" +
                "      org_code\n" +
                "    FROM\n" +
                "      temp)\n" +
                "  WHERE\n" +
                "     n.TRADE_DATE = DATE ''${valDate}''\n" +
                "    AND n.PS IN (''T01'',\n" +
                "    ''T02'',\n" +
                "    ''T07'',\n" +
                "    ''T08'')\n" +
                "  GROUP BY\n" +
                "    t2.ORGID\n" +
                "UNION ALL\n" +
                "  SELECT\n" +
                "    NVL(SUM(DECODE(n.PS, ''T01'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) -NVL(SUM(DECODE(n.PS, ''T02'', DECODE(TRADE_STATUS, ''03'', n.TRADE_AMT*-1, n.TRADE_AMT))), 0) AS RVALUE,\n" +
                "    ''fbfc72ca76454cb087476e8c86e12ff2,'' || t2.ORGID AS RKEY\n" +
                "  FROM\n" +
                "    WFL_TRD_PRODUCT_DEAL_ADD m\n" +
                "  INNER JOIN WFL_TRD_PRODUCT_DEAL n ON\n" +
                "    m.TRADE_ID = n.TRADE_ID\n" +
                "    AND n.TRADE_STATUS != ''03''\n" +
                "  INNER JOIN finprod t2 ON\n" +
                "    n.FINPROD_ID = t2.FINPROD_ID\n" +
                "    AND t2.ORGID IN (\n" +
                "    SELECT\n" +
                "      org_code\n" +
                "    FROM\n" +
                "      temp)\n" +
                "  WHERE\n" +
                "     n.TRADE_DATE = DATE ''${valDate}''\n" +
                "    AND n.PS IN (''T01'',\n" +
                "    ''T02'')\n" +
                "  GROUP BY\n" +
                "    t2.ORGID) )\n" +
                "GROUP BY\n" +
                "  RKEY'; --字段内容  \n" +
                "  UPDATE RSK_ISUP_INDICATOR_PARA_DTL T SET T.content = clobValue WHERE para_code = 'TEST' AND PARA_GRANULARITY = '01'; \n" +
                "  COMMIT;  \n" +
                "END;";
        Connection con = getInstance();
        try {
//			pst = con.prepareStatement(sql);
//			rs = pst.executeQuery();
//            pst.execute();
			/*if(rs.next()){
				System.out.println(rs);
			}*/
            /*sql ="select fee_id,\n" +
                    "       fee_name,\n" +
                    "       apply_type,\n" +
                    "       fee_type,\n" +
                    "       basic_bill,\n" +
                    "       basis,\n" +
                    "       charge_type,\n" +
                    "       org_type,\n" +
                    "       org_type2,\n" +
                    "       remark,\n" +
                    "       create_user,\n" +
                    "       create_dept,\n" +
                    "       create_time,\n" +
                    "       update_user,\n" +
                    "       update_time\n" +
                    "  from mst_feeinfo\n";*/
            sql="select seq_no,\n" +
                    "\n" +
                    "       bookset_id,\n" +
                    "\n" +
                    "       bookset_name,\n" +
                    "\n" +
                    "       profit_type,\n" +
                    "\n" +
                    "       val_date,\n" +
                    "\n" +
                    "       detail_dist,\n" +
                    "\n" +
                    "       layering_id,\n" +
                    "\n" +
                    "       subject_no,\n" +
                    "       subject_name,\n" +
                    "       fsubject_id,\n" +
                    "       num_amt,\n" +
                    "       unit_cost,\n" +
                    "       cost,\n" +
                    "       cost_percent,\n" +
                    "       close_price,\n" +
                    "       market_value,\n" +
                    "       value_percent,\n" +
                    "       value_increment,\n" +
                    "       bal_flag,\n" +
                    "       shadow_price_value,\n" +
                    "       market_val_date,\n" +
                    "       create_user,\n" +
                    "       create_dept,\n" +
                    "       create_time,\n" +
                    "       update_user,\n" +
                    "       update_time,\n" +
                    "       o_ccy,\n" +
                    "       exchange_rate,\n" +
                    "       o_cost,\n" +
                    "       o_market_value\n" +
                    "  from bok_val_table_data ";
            getDataByTableName(rs,sql,con);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(rs,pst,con);
        }
    }

    private static void getDataByTableName(ResultSet rs,String sql,Connection con) throws SQLException {
        Statement st = null;
        //st = conn.createStatement();
        //st = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        //rs = st.executeQuery(sql);
        //ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
        //int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
        StringBuffer sb = new StringBuffer("");
        String a = "|";
        int count = 1;


        //获取总行数
        Statement statement = con.createStatement();
        ResultSet cs = statement.executeQuery("select count(*) as rowCount from (" + sql + ")");
        cs.next();
        int rows = cs.getInt("rowCount");
        int onerun = 100000;  // 每次读取记录数
        int lastrow = 0; // 每次读取后的最后一条记录

        int datanum = cs.getInt("rowCount");
        int runnum = datanum % onerun == 0 ? (datanum / onerun) : (datanum / onerun) + 1;

        // 分批读取数据
        st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        StringBuffer text = new StringBuffer();

        int l=0;//数组下标
        int temp = 1;//第一批开始
        int from = l;
        int to = temp * MAX_WRITE_ROWS;

        for (int r = 0; r < runnum; r++){
            System.out.println("getDataByTableName--" + datanum + " 开始查询第" + (r + 1) + "批数据");
            sql = "SELECT *\n" +
                    "  FROM (SELECT rownum rn, " + "seq_no,\n" +
                    "\n" +
                    "       bookset_id,\n" +
                    "\n" +
                    "       bookset_name,\n" +
                    "\n" +
                    "       profit_type,\n" +
                    "\n" +
                    "       val_date,\n" +
                    "\n" +
                    "       detail_dist,\n" +
                    "\n" +
                    "       layering_id,\n" +
                    "\n" +
                    "       subject_no,\n" +
                    "       subject_name,\n" +
                    "       fsubject_id,\n" +
                    "       num_amt,\n" +
                    "       unit_cost,\n" +
                    "       cost,\n" +
                    "       cost_percent,\n" +
                    "       close_price,\n" +
                    "       market_value,\n" +
                    "       value_percent,\n" +
                    "       value_increment,\n" +
                    "       bal_flag,\n" +
                    "       shadow_price_value,\n" +
                    "       market_val_date,\n" +
                    "       create_user,\n" +
                    "       create_dept,\n" +
                    "       create_time,\n" +
                    "       update_user,\n" +
                    "       update_time,\n" +
                    "       o_ccy,\n" +
                    "       exchange_rate,\n" +
                    "       o_cost,\n" +
                    "       o_market_value\n"  + " FROM bok_val_table_data ORDER BY rownum ASC)\n" +
                    " WHERE rn > " + lastrow;
            st.setMaxRows(onerun);
            st.setFetchSize(100000); // 当调用rs.next时，ResultSet会一次性从服务器上取1000行数据回来，在下次rs.next时，它可以直接从内存中获取出数据而不需要网络交互，从而提高效率
            rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            //生成数据
            int k = 1;
            System.out.printf("from %d to %d%n", from, to);
            sb = new StringBuffer();
            while(rs.next()){
                /*if(l==MAX_WRITE_ROWS * temp) {
                    //这批结束了,把StringBuilder里面的数据用掉再清空
                    //System.out.println(sb.toString());
                    //写文件
                    createFileByAddTo("F://data.dat",sb.toString());
                    sb = new StringBuffer();//清空

                    temp++;//下一批
                    from = l;
                    to = temp * MAX_WRITE_ROWS;
                    System.out.printf("from %d to %d%n", from, to);
                }*/
                if(false && count == 1){
                    for(int i=1;i<=columnCount;i++){
                        String fieldName = md.getColumnName(i);
                        sb.append(fieldName);
                        if(i != columnCount){
                            sb.append(a);
                        }
                    }
                    //if(StringUtils.isNotEmpty("|$|")){
                    sb.append("|$|").append(CRLF);
                    //}
                }
                for (int j = 1; j <= columnCount; j++) {// 列
                    String type = md.getColumnTypeName(j);// 列
                    if ("DATE".equals(type)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = rs.getDate(j);
                        sb.append(date != null?format.format(date) : "");
                    } else if ("TIMESTAMP".equals(type)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        java.util.Date date = rs.getDate(j);
                        sb.append(date != null ? format.format(date) : "");
                    } else {
                        sb.append(rs.getObject(j) != null ? rs.getObject(j): "");
                    }
                    if(j != columnCount || StringUtils.isNotEmpty("|$|")){
                        sb.append(a);
                    }
                }
                count ++;
                if(StringUtils.isNotEmpty("|$|")){
                    sb.append("|$|");
                }
                //log.info("是否最后一行：{}",rs.isLast());
                if(rs.isLast()){
                    if(StringUtils.isNotEmpty("0") && "0".equalsIgnoreCase("0")){//否 不需要最后的换行
                        //不作处理
                    }else{
                        sb.append(CRLF);
                    }
                }else{
                    sb.append(CRLF);
                }
                l++;
                if (k % 100000 == 0) {
                    // 创建文件，并追加内容
                    createFileByAddTo("F://data.dat", sb.toString());
                    sb = new StringBuffer("");
                }
                k++;
            }
            if (sb.length() > 10) {
                createFileByAddTo("F://data.dat", sb.toString());
            }
            lastrow += onerun;
        }


        /*int k=0;//数组下标
        int temp = 1;//第一批开始
        int from = k;
        int to = temp * MAX_WRITE_ROWS;
        System.out.printf("from %d to %d%n", from, to);
        sb = new StringBuilder();
        while(rs.next()) {
            if(k==MAX_WRITE_ROWS * temp) {
                //这批结束了,把StringBuilder里面的数据用掉再清空
                //System.out.println(sb.toString());
                sb = new StringBuilder();//清空

                temp++;//下一批
                from = k;
                to = temp * MAX_WRITE_ROWS;
                System.out.printf("from %d to %d%n", from, to);
            }
            *//*if(false && count == 1){
                for(int i=1;i<=columnCount;i++){
                    String fieldName = md.getColumnName(i);
                    sb.append(fieldName);
                    if(i != columnCount){
                        sb.append(a);
                    }
                }
                //if(StringUtils.isNotEmpty("|$|")){
                    sb.append("|$|").append(CRLF);
                //}
            }
            for (int j = 1; j <= columnCount; j++) {// 列
                String type = md.getColumnTypeName(j);// 列
                if ("DATE".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = rs.getDate(j);
                    sb.append(date != null?format.format(date) : "");
                } else if ("TIMESTAMP".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = rs.getDate(j);
                    sb.append(date != null ? format.format(date) : "");
                } else {
                    sb.append(rs.getObject(j) != null ? rs.getObject(j): "");
                }
                if(j != columnCount || StringUtils.isNotEmpty("|$|")){
                    sb.append(a);
                }
            }
            count ++;
            if(StringUtils.isNotEmpty("|$|")){
                sb.append("|$|");
            }
            //log.info("是否最后一行：{}",rs.isLast());
            if(rs.isLast()){
                if(StringUtils.isNotEmpty("0") && "0".equalsIgnoreCase("0")){//否 不需要最后的换行
                    //不作处理
                }else{
                    sb.append(CRLF);
                }
            }else{
                sb.append(CRLF);
            }*//*
            k++;
        }*/

        //String data = sb.toString();
        //log.info(data);
        //createFileByAddTo("F://data.dat",data);
    }

    private static void writerData(StringBuffer sb, ResultSet rs, int count, int columnCount, ResultSetMetaData md, String a) throws SQLException {
        while (rs.next()) {// 行
            if(false && count == 1){
                for(int i=1;i<=columnCount;i++){
                    String fieldName = md.getColumnName(i);
                    sb.append(fieldName);
                    if(i != columnCount){
                        sb.append(a);
                    }
                }
                if(StringUtils.isNotEmpty("|$|")){
                    sb.append("|$|").append(CRLF);
                }
            }
            for (int j = 1; j <= columnCount; j++) {// 列
                String type = md.getColumnTypeName(j);// 列
                if ("DATE".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = rs.getDate(j);
                    sb.append(date != null?format.format(date) : "");
                } else if ("TIMESTAMP".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = rs.getDate(j);
                    sb.append(date != null ? format.format(date) : "");
                } else {
                    sb.append(rs.getObject(j) != null ? rs.getObject(j): "");
                }
                if(j != columnCount || StringUtils.isNotEmpty("|$|")){
                    sb.append(a);
                }
            }
            count ++;
            if(StringUtils.isNotEmpty("|$|")){
                sb.append("|$|");
            }
            //log.info("是否最后一行：{}",rs.isLast());
            if(rs.isLast()){
                if(StringUtils.isNotEmpty("0") && "0".equalsIgnoreCase("0")){//否 不需要最后的换行
                    //不作处理
                }else{
                    sb.append(CRLF);
                }
            }else{
                sb.append(CRLF);
            }
        }
    }

    public static void close(ResultSet rs, PreparedStatement pst, Connection connection) {
        try {
            if(rs!=null){
                rs.close();
                rs=null;
            }
            if(pst!=null){
                pst.close();
                pst=null;
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建文件（在原有文件内容上进行追加）
     * 默认编码UTF-8
     * @param filePathAndName
     * @param fileContent
     */
    public static void createFileByAddTo(String filePathAndName, String fileContent) {
        try {
            File file = new File(filePathAndName);
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(file, true)); // 参数true表示向文件中追加内容
                bw.write(fileContent);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("新建文件（在原有文件内容上进行追加）出错：");
            e.printStackTrace();
        }
    }

}
