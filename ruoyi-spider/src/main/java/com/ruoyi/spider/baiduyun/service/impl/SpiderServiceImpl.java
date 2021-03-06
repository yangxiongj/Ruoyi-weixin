package com.ruoyi.spider.baiduyun.service.impl;

import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.spider.baiduyun.domain.SpiFile;
import com.ruoyi.spider.baiduyun.service.ISpiFileService;
import com.ruoyi.spider.baiduyun.service.SpiderService;
import com.ruoyi.spider.factory.AsyncSpiderFactory;
import com.ruoyi.spider.nextfilter.RedisBloomFilter;
import com.ruoyi.spider.utils.DemoAutoNewsCrawler;
import com.ruoyi.spider.utils.RedisConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SpiderServiceImpl implements SpiderService {
    @Autowired
    private ISpiFileService spiFileService;
    @Override
    public List<SpiFile> getFiles(int page) {
        List<SpiFile> files = new ArrayList<>();
        RedisBloomFilter filter = RedisBloomFilter.getFilter(new RedisConsts("sobaidupan"),200);
        for (Long i=924841l;i<10000000000l;i+=1600){
            String url = "http://m.sobaidupan.com/file-%d.html";
            DemoAutoNewsCrawler crawler = new DemoAutoNewsCrawler(url,false);
            //断点重爬
            crawler.setResumable(true);
            //rediskey 为搜百度 失效时间 200
            DemoAutoNewsCrawler.pageSize=1600;
            DemoAutoNewsCrawler.startPage = i;
            try {
                crawler.start(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
             files = crawler.getSpiFiles();
            if(files.size()>0){
                AsyncManager.me().execute(AsyncSpiderFactory.saveFiles(files,spiFileService));
            }
        }
        return files;
    }
}
