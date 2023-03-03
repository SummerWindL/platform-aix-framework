package com.platform.aix.demo;

import com.platform.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Advance
 * @date 2023年02月17日 15:42
 * @since V1.0.0
 */
public class DataTest {
    private final static Logger log = LoggerFactory.getLogger(DataTest.class);

    protected static final String CRLF = "\n";
    public static void main(String[] args) {
        try {
            getDataByTableName("SELECT CREATE_USER ,CREATE_TIME ,UPDATE_TIME  FROM PA_TRD_PRODUCT_DEAL ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过表名称查询表数据
     * @param sql
     * @return
     */
    public static String getDataByTableName( String sql) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Class.forName("oracle.jdbc.driver.OracleDriver");
        log.info("dataSource config dbUrl: {},dbUserName: {},dbPwd: {}","jdbc:oracle:thin:@//10.25.4.216:1521/fits","JJTG_112501","JJTG_112501");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@//10.25.4.216:1521/fits","JJTG_112501","JJTG_112501");

        st = conn.createStatement();
        rs = st.executeQuery(sql);
        ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
        int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数

        StringBuffer sb = new StringBuffer("");
        String a = "|";
        int count = 1;
        while (rs.next()) {// 行
            if(false && count == 1){
                for(int i=1;i<=columnCount;i++){
                    String fieldName = md.getColumnName(i);
                    sb.append(fieldName);
                    if(i != columnCount){
                        sb.append(a);
                    }
                }
                if(StringUtil.isNotEmpty("|$|")){
                    sb.append("|$|").append(CRLF);
                }
            }
            for (int j = 1; j <= columnCount; j++) {// 列
                String type = md.getColumnTypeName(j);// 列
                if ("DATE".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = rs.getDate(j);
                    sb.append(date != null?format.format(date) : "");
                } else if ("TIMESTAMP".equals(type)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = rs.getDate(j);
                    sb.append(date != null ? format.format(date) : "");
                } else {
                    sb.append(rs.getObject(j) != null ? rs.getObject(j): "");
                }
                if(j != columnCount || StringUtil.isNotEmpty("|$|")){
                    sb.append(a);
                }
            }
            count ++;
            if(StringUtil.isNotEmpty("|$|")){
                sb.append("|$|");
            }
            sb.append(CRLF);
        }
        String data = sb.toString();
        if(StringUtil.isNotEmpty("0") && "0".equalsIgnoreCase("0") && data.length() > 0){
            data = data.substring(0,data.length()-1);
        }
        if (conn != null) {
            conn.close();
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
        return data;



    }
}
