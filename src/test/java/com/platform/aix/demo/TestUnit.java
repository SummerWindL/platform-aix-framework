package com.platform.aix.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author Advance
 * @date 2021年09月26日 19:05
 * @since V1.0.0
 */
public class TestUnit {
    public static void main(String[] args) {
        String sql = "clobWITH temp AS (\n" +
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
                "  RKEY";

        String str = sql.substring(sql.indexOf("clob")+4);
        System.out.println(str);
    }
    @Test
    public void test01() throws SQLException {
        HikariConfig config=new HikariConfig();
        config.setJdbcUrl("jdbc:oracle:thin:@//localhost:1521/orcl");//获取连接路径
        config.setUsername("AIX");//密码
        config.setPassword("AIX");//账号
        HikariDataSource hd=new HikariDataSource(config);//进行连接
        System.out.println(hd.getConnection());//输出是否连接成功
    }

    @Test
    public void contextLoads() {
        System.out.println("===== 构建一个returnVO模型开始 =====");
//        ReturnVOProto.ReturnVO.Builder builder = ReturnVOProto.ReturnVO.newBuilder();
//        builder.setCode("200");
//        builder.setData("OK");
//        builder.setMessage("GO");
//
//
//        ReturnVOProto.ReturnVO returnVO = builder.build();
//        System.out.println(returnVO.toString());
//        System.out.println("===== 构建returnVO模型结束 =====");
//
//        System.out.println("===== returnVO Byte 开始=====");
//        for(byte b : returnVO.toByteArray()){
//            System.out.print(b);
//        }
//        System.out.println("\n" + "returnVO" + returnVO.toByteString().size());
//        System.out.println("===== returnVO Byte 结束 =====");
//
//        System.out.println("===== returnVO 反序列化生成对象开始 =====");
//        ReturnVOProto.ReturnVO returnVO1 = null;
//        try {
//            returnVO1 =ReturnVOProto.ReturnVO.parseFrom(returnVO.toByteArray());
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//        System.out.print(returnVO1.toString());
        System.out.println("===== returnVO 反序列化生成对象结束 =====");

    }
}
