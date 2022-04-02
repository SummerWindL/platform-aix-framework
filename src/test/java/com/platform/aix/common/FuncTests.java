package com.platform.aix.common;

import com.platform.aix.PlatformAixApplicationTests;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Advance
 * @date 2022年03月08日 15:48
 * @since V1.0.0
 */
public class FuncTests extends PlatformAixApplicationTests {

    @Test
    public void test(){

    }
    protected interface FunctionEx {
        void apply(String a);
    }

    public void adddd(String a){
        System.out.println("adddd---" + a);
        add(a,this::doAdd);
    }

    public String doAdd(String a){
        System.out.println("doAdd---" + a);
        return "";
    }

    public void add(String a,FunctionEx functionEx){
        System.out.println("add---" + a);
        functionEx.apply(a);  // 其实这里执行的就是doAdd
    }

//    public static void main(String[] args) {
//        FuncTests test = new FuncTests();
//        System.out.println("main---");
//        test.adddd("Jly");
//    }

    public static void main(String[] args) {
//        List<Integer> list = new ArrayList() {
//            {
//                add(1);
//                add(1);
//                add(1);
//            }
//        };
//        long count = list.stream().distinct().count();
//        boolean isRepeat = count == list.size();
//        System.out.println(count);//输出2
//        System.out.println(isRepeat);//输出true

        List<Integer> list = new ArrayList();
        list.add(1000);
        list.add(2000);
        list.add(2000);
        list.add(2000);
        list.add(2000);
        AtomicLong entrustNum = new AtomicLong(0L);//委托数量 （T_INSTRUCTION）
//        long entrustNum = 0L;
        list.forEach(item ->{
            entrustNum.addAndGet(item.longValue());
//            entrustNum += item.longValue();
        });
//        for(Integer i:list){
//            entrustNum += i;
//        }
        System.out.printf("++++++:"+entrustNum);
    }

}
