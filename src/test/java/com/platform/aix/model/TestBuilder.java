package com.platform.aix.model;

/**
 * @author Advance
 * @date 2022年03月08日 16:10
 * @since V1.0.0
 */
public class TestBuilder {
    public static void main(String[] args) {

        CompanyClient client = new CompanyClient.Builder()
                .setCompanyName("百度")
                .setCompanyAddress("海定区百度大厦")
                .setCompanyRegfunds(5)
                .setmPerson("1000人以上")
                .build();
        System.out.println("构造出一个公司：" + client.toString());

        System.out.println("---------------------分隔符");

        CompanyClient.Builder builder = new CompanyClient.Builder();
        builder.setCompanyName("华为");
        builder.setCompanyAddress("海定区百度大厦");
        builder.setCompanyRegfunds(20);
        builder.setmType("通信科技行业");
        CompanyClient client1 = builder.build();
        System.out.println("构造出另一个公司：" + client1.toString());
    }

}
