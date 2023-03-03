package com.platform.aix.common;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.collectors.PlatformCollectors;
import com.platform.common.util.ArithmeticUtil;
import com.platform.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Advance
 * @date 2022年09月27日 17:53
 * @since V1.0.0
 */
public class AmountCalculate {
    public static void main(String[] args) {

        List<PtlCashPositionFlow> flowList = new ArrayList<>();
        PtlCashPositionFlow positionFlow = new PtlCashPositionFlow();
        positionFlow.setAcctId("321:TGH0831");
        positionFlow.setSeqno("5c2b1c7471a340a59708eb5037f5bc9d");
        positionFlow.setCdate(DateUtil.parse("2022-09-27"));
        positionFlow.setHodingType("03");
        positionFlow.setPortfolioId("0912");
        positionFlow.setAmount(new BigDecimal(10000000000L));
        flowList.add(positionFlow);
        PtlCashPositionFlow positionFlow1 = new PtlCashPositionFlow();
        positionFlow1.setAcctId("321:TGH0831");
        positionFlow1.setSeqno("5c2b1c7471a340a59708eb5037f5bc9d");
        positionFlow1.setCdate(DateUtil.parse("2022-09-27"));
        positionFlow1.setHodingType("03");
        positionFlow1.setPortfolioId("0913");
        positionFlow1.setAmount(new BigDecimal(10000000000L));
        flowList.add(positionFlow1);
        PtlCashPositionFlow positionFlow2 = new PtlCashPositionFlow();
        positionFlow2.setAcctId("321:TGH0831");
        positionFlow2.setSeqno("5c2b1c7471a340a59708eb5037f5bc9d");
        positionFlow2.setCdate(DateUtil.parse("2022-09-27"));
        positionFlow2.setHodingType("03");
        positionFlow2.setPortfolioId("0913");
        positionFlow2.setAmount(new BigDecimal(-9999999999L));
        flowList.add(positionFlow2);
        Map<String, List<PtlCashPositionFlow>> ptlCashFlowGroupMap = flowList.stream().collect(PlatformCollectors.groupingBy(x -> x.getPortfolioId() + "," + x.getAcctId()+ "," + DateUtil.format(x.getCdate(),"yyyyMMdd")));
        Map<String,Object> portfolioIdAcctCdateMap = new HashMap<>();
        ptlCashFlowGroupMap.forEach((key,value)->{
            AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(new BigDecimal(0));
            value.stream().forEach(item ->{
                BigDecimal bigDecimal = ArithmeticUtil.doAdd(item.getAmount());
                totalAmount.updateAndGet(v->v.add(Optional.ofNullable(bigDecimal).orElse(new BigDecimal(0.00))));
            });
            portfolioIdAcctCdateMap.put(key,totalAmount);//value =  amount
        });
        System.out.println(JSONObject.toJSONString(portfolioIdAcctCdateMap));
    }
}
