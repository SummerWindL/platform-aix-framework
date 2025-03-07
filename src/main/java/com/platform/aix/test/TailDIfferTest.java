package com.platform.aix.test;

import com.platform.common.util.ArithmeticUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Advance
 * @date 2024年02月22日 17:40
 * @since V1.0.0
 */
public class TailDIfferTest {
    public static void main(String[] args) {
        BigDecimal settFeeAmtTemp = ArithmeticUtil.round(
                        ArithmeticUtil.divide(new BigDecimal(150), new BigDecimal(13), 12, RoundingMode.HALF_UP), 2, RoundingMode.DOWN);
        System.out.println(settFeeAmtTemp);
    }

    private final static String pledgeDimension = "0.1";

    /**
     * 尾差处理
     *
     * @param repoEntrustPledgeDtos 组合分配信息
     * @param tailDifference 尾差
     * @author jiangpeng
     * @date 2021/12/20 14:40
     */
//    private void handleTailDifference(List<RepoEntrustPledgeDto> repoEntrustPledgeDtos, BigDecimal tailDifference) {
//        //递增维度
//        BigDecimal addDimension = new BigDecimal(pledgeDimension);;
//        int size = repoEntrustPledgeDtos.size();
//        //委托维度 已经完全分配的集合
//        Set<String> set = new HashSet<>();
//        while (tailDifference.compareTo(BigDecimal.ZERO) > 0 && size > 0) {
//            if (tailDifference.compareTo(ArithmeticUtil.multiply(new BigDecimal(pledgeDimension), new BigDecimal(10))) >= 0) {
//                addDimension = ArithmeticUtil.divide(tailDifference, size, 4);
//            }
//            for (RepoEntrustPledgeDto item : repoEntrustPledgeDtos) {
//                if ((tailDifference.compareTo(BigDecimal.ZERO) <= 0)) {
//                    if (set.add(item.getEntrustId())) {[文件]
//
//                        size--;
//                    }[文件]
//
//                } else {
//                    item.setPledgePrin(ArithmeticUtil.doAdd(item.getPledgePrin(), addDimension));
//                    tailDifference = ArithmeticUtil.subtract(tailDifference, addDimension);
//                }
//                if(tailDifference.compareTo(BigDecimal.ZERO) <= 0){
//                    break;
//                }
//            }
//        }
//    }
}
