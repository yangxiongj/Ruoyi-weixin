package com.ruoyi.spider.baiduyun.service;

import com.ruoyi.spider.baiduyun.domain.SpiFile;

import java.util.List;

/**
 * 爬虫业务类
 */
public interface SpiderService {
    /**
     * 爬虫实例一获取 m.sobaidupan.com url
     */
    List<SpiFile> getFiles(int page);
}
