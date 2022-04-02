package com.platform.aix.demo;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Advance
 * @date 2021年10月12日 9:08
 * @since V1.0.0
 */
@Slf4j
public class StringTest {
    public static void main(String[] args) {
        /*log.info("\n原值{}， 值{}",2000000/600000,Math.round(2000000/600000));

//        System.out.printf();
        String sql = "select seq_no,\n" +
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

        System.out.println(sql.substring(6));*/




        /*String s = "[[\"1\",\"lcxxpl\",\"oracle.jdbc.driver.OracleDriver\",\"jdbc:oracle:thin:@//localhost:1521/orcl\",\"tams0509\",\"tams0509\",\"127.0.0.1\",21,\"ftpuser\",\"Admin1234\",\"F:\\\\localftp\\\\download\",\"/rep\",\"GBK\",\"0\",\"|\",\"|$|\",\"1\",\"1\",\"1\",\"finish.flag\",1633881600000,\"0\",\".dat\",null]]";
        JSONArray json = JSONArray.parseArray(s);
        List<String> a = new ArrayList<>();
        json.forEach(item -> {
            //log.info(item.toString().replace("[","{").replace("]","}"));
            a.add(item.toString().replace("[","{").replace("]","}"));
        });
        System.out.println(a.toString());*/


        String modulePath = "\\/tams\\/public\\/js\\/modules\\";
        String path = modulePath.lastIndexOf('/') != modulePath.length() ? modulePath + '/' : modulePath;

        System.out.println(path);

    }
}
