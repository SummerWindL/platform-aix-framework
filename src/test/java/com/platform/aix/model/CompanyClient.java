package com.platform.aix.model;

/**
 * @author Advance
 * @date 2022年03月08日 16:10
 * @since V1.0.0
 */
public class CompanyClient {
    public final String companyName;
    public final String companyAddress;

    public final double companyRegfunds;
    public final String mPerson;
    public final String mType;


    //构造方法
    public CompanyClient() {
        this(new Builder());
    }

    //构造方法
    public CompanyClient(Builder builder){
        this.companyName = builder.companyName;
        this.companyAddress = builder.companyAddress;
        this.companyRegfunds = builder.companyRegfunds;
        this.mPerson = builder.person;
        this.mType = builder.type;
    }
    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public double getCompanyRegfunds() {
        return companyRegfunds;
    }

    public String getmPerson() {
        return mPerson;
    }

    public String getmType() {
        return mType;
    }



    public Builder newBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        return "CompanyClient{" +
                "companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyRegfunds=" + companyRegfunds +"千万"+
                ", mPerson=" + mPerson +
                ", mType='" + mType + '\'' +
                '}';
    }
    /**
     *静态内部类 Builder
     */

    public static class Builder{
        public String companyName;
        public String companyAddress;
        public  double companyRegfunds;
        public  String person;
        public String type;
        //构造方法
        public Builder() {
            companyName = companyName;
            companyAddress = companyAddress;
            companyRegfunds = companyRegfunds;
            person = person;
            type = type;

        }
        //构造方法
        Builder(CompanyClient companyClient){
            this.companyName = companyClient.companyName;
            this.companyAddress = companyClient.companyAddress;
            this.companyRegfunds = companyClient.companyRegfunds;
            this.person = companyClient.mPerson;
            this.type = companyClient.mType;
        }

        public Builder setCompanyName(String name) {
            companyName = name;
            return this;
        }

        public Builder setCompanyAddress(String address) {
            companyAddress = address;
            return this;
        }

        public Builder setCompanyRegfunds(double regfunds) {
            companyRegfunds = regfunds;
            return this;
        }

        public Builder setmPerson(String per) {
            person = per;
            return this;
        }

        public Builder setmType(String typeStr) {
            type = typeStr;
            return this;
        }
        //构建一个实体
        public CompanyClient build() {
            return new CompanyClient(this);
        }
    }

}
