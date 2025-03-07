package com.platform.aix.demo;

import com.platform.common.util.DateUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Advance
 * @date 2024年10月31日 20:34
 * @since V1.0.0
 */
public class PrdPastTest {

    public static void main(String[] args) throws ParseException {
        Date endDate= DateUtil.parseDate("2024-10-31", "yyyy-MM-dd");
        Date startDate = getStartDate(DateUtil.parseDate("2024-10-31", "yyyy-MM-dd"), DateUtil.parseDate("2021-02-22", "yyyy-MM-dd"), 1, 1);
        System.out.println(DateUtil.format(startDate,"yyyy-MM-dd"));
        System.out.println(DateUtil.getBetweenDayNumber(startDate,endDate));
        Date proDate= DateUtil.parseDate("2024-10-18", "yyyy-MM-dd");
        System.out.println(DateUtil.format(DateUtil.addDays(proDate,-20)));
    }
    public static Date getStartDate(Date endDate, Date vdate, int count, int type){
        Date startDate=null;
        if(type==0){
            startDate= DateUtil.addDay(endDate,-count);
        }else if(type==1){
            startDate=DateUtil.addMonths(endDate,-count);
        }else if(type==2){
            startDate=DateUtil.addYear(endDate,-count);
        }else if(type==3){
            int year=Integer.parseInt(DateUtil.getYearStr(DateUtil.format(endDate)))-count;
            startDate=DateUtil.parse(year+"-12-31");
        }
        if(startDate.before(vdate)){
            return null;
        }
        return startDate;
    }
}
