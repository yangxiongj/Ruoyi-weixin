package com.ruoyi.spider.utils;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.ExceptionUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.spider.baiduyun.domain.SpiFile;
import com.sun.jndi.toolkit.url.UrlUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoAutoNewsCrawler extends BreadthCrawler {
    public static int pageSize =0;
    public static Long startPage = 100l;
    static List<String> list = new ArrayList<>();//url种子
    public List<SpiFile> spiFiles = new ArrayList<>();//url种子
    /**
     *
     * @param crawlPath 是维护目录的路径 此爬虫的信息
     *
     * @param autoParse 如果autoparse为true，则breadthcrawler将自动提取 与PAG中的regex规则匹配的链接
     */
    public DemoAutoNewsCrawler(String crawlPath, boolean autoParse) {
          /* 开始页面
        this.addSeed（“ https://blog.github.com/ ”）;
        for（int pageIndex =  2 ; pageIndex <=  5 ; pageIndex ++）{
            String seedUrl =  String 。格式（“ https://blog.github.com/page/%d/ ”，的PageIndex）;
            这个。addSeed（seedUrl）;
        } */
       /*获取网址，如“https://blog.github.com/2018-07-13-graphql-for-octokit/”
        this.addRegex（ “ https://blog.github.com/[0-9]{4}-[0-9]{2}-[0-9]{2}-[^/ ]+/ ”）;
        / *不要获取jpg | png | gif * /
        // this.addRegex（“ - 。* \\。（jpg | png | gif）。*”）;
        / *不要获取url包含＃* /
        // this.addRegex（“ - 。*＃。*”）;

        setThreads（50）;
        getConf（）。setTopN（100）;
        * /
        /**开启分页*/
        super("百度云爬虫",autoParse);
        for(int pageIndex = 1;pageIndex<=pageSize;pageIndex++){
            String seedUrl = String.format(crawlPath, pageIndex+startPage);
            this.addSeed(seedUrl);
            System.out.println(seedUrl);
        }
        //线程
        setThreads(800);
        //最大线程
        getConf().setTopN(1600);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        Document doc = null;
        crawlDatums.addAndReturn("");
            try {
                doc =page.doc();
            } catch (Exception e) {
                // 当捕捉到异常时，且认为这个网页需要重新爬取时
                // 应该使用ExceptionUtils.fail(e)
                // 无视或者throw异常在编译时会报错，因为visit方法没有throws异常
                // 该方法会抛出RuntimeException，不会强制要求visit方法加上throws
                ExceptionUtils.fail(e);
            }
            String title  =  doc.select(".tops").text();
            if(title.startsWith("该内容已经删除")){
                return;
            }
            //获取列表
            Elements elements =  doc.select(".tags ul li");
            elements.select("b").remove();
            SpiFile file = new SpiFile();
            for(int li=0;li<elements.size();li++){
                Element element =elements.get(li);
                int row = li+1;
                switch (row){
                    case 1: //资源目录
                            String menu =element.text();
                            file.setMenu(menu);
                        break;
                    case 2:
                        String userName =element.text();
                        //分享用户
                        file.setUserName(userName);
                        break;
                    case 3:
                        String html = element.html();
                        if(html==null){
                            continue;
                        }
                        //文件格式
                        String fileType = html.substring(0,html.indexOf("&"));
                        file.setFileType(fileType);
                        //文件大小
                        String fileSize = html.substring(html.lastIndexOf(";")+1);
                        file.setFileSize(fileSize);
                        break;
                    case 4:
                         html = element.html();
                        if(html==null){
                            continue;
                        }
                        //文件格式
                        String lookConut = html.substring(0,html.indexOf("&"));
                        file.setLookCount(lookConut);
                        //文件大小
                        String downCount = html.substring(html.lastIndexOf(";")+1);
                        file.setDownCounr(downCount);
                        break;
                    case 5:
                        String data = element.text();
                        if(data.length()<12){
                            data = data+" 0:00:00";
                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(data,formatter);
                        file.setCreateData(dateTime);
                        break;
                    case 6:
                        String burl = elements.get(li).select("a").attr("href");
                        if(burl==null){
                            continue;
                        }
                        String sid = getParamByUrl(burl,"id");
                        file.setId(Long.parseLong(sid));
                        String bt = burl.substring(burl.indexOf("bt=")+3);
                        file.setRemark(bt);
                        if(StringUtils.isNotNull(file.getMenu())){
                            spiFiles.add(file);
                        }
                        break;
                }
            }
        }

    public List<SpiFile> getSpiFiles() {
        return spiFiles;
    }

    public void setSpiFiles(List<SpiFile> spiFiles) {
        this.spiFiles = spiFiles;
    }
    public static String getParamByUrl(String url, String name) {
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";

        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(url);
        if (m.find( )) {
            return m.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }

}