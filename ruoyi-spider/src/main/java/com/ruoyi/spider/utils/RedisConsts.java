package com.ruoyi.spider.utils;

import org.springframework.beans.factory.annotation.Value;

public class RedisConsts {
    @Value("${spider.redisbloom:bloomfilter}")
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

    public RedisConsts() {

    }
}
