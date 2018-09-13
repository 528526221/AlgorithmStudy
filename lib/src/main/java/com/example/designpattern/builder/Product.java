package com.example.designpattern.builder;

/**
 * Date：2018/9/11
 * Desc：
 * Created by xulc.
 */

public class Product {
    private String name;
    private String desc;

    private Product(Builder builder) {
        this.name = builder.name;
        this.desc = builder.desc;
    }

    public static class Builder{
        private String name;
        private String desc;

        public Builder buildName(String name){
            this.name = name;
            return this;
        }
        public Builder buildDesc(String desc){
            this.desc = desc;
            return this;
        }

        public Product build(){
            return new Product(this);
        }

    }

}
