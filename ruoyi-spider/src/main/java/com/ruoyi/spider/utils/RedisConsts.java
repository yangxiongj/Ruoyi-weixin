package com.ruoyi.spider.utils;

public class RedisConsts {
    private String boolmFilter;

    public RedisConsts(String boolmFilter) {
        this.boolmFilter = boolmFilter;
    }

    public String getBoolmFilter() {
        return boolmFilter;
    }

    public void setBoolmFilter(String boolmFilter) {
        this.boolmFilter = boolmFilter;
    }
}
