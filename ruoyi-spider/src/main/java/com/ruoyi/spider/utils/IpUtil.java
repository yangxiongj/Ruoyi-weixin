package com.ruoyi.spider.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.spider.domin.Ip;
import org.apache.commons.lang.math.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取代理IP，需要 com.alibaba.fastjson.JSONObject以及Jsoup
 */
@Component
public class IpUtil {
    private Logger logger = LoggerFactory.getLogger(IpUtil.class);
    @Autowired
    private BloomFilter bloomFilter;

    @Value("${ipaget.baseurl}")
    private List<String> baseurl;

    @Value("${ipaget.totalpage}")
    private Integer totalpage;

    @Value("${ipaget.wantednumber}")
    private Integer wantedNumber;
    /**
     * 每次都是在方法内部创建的连接，那么线程之间自然不存在线程安全问题。 但是这样会有一个致命的影响： 导致服务器压力非常大，并且严重影响程序执行性能。
     * 由于在方法中需要频繁地开启和关闭数据库连接，
     * 这样不尽严重影响程序执行效率，还可能导致服务器压力巨大因为ThreadLocal在每个线程中对该变量会创建一个副本
     * ，即每个线程内部都会有一个该变量，且在线程内部任何地方都可以使用，线程之间互不影响， 这样一来就不存在线程安全问题，也不会严重影响程序执行性能。
     * 但是要注意，虽然ThreadLocal能够解决上面说的问题，但是由于在每个线程中都创建了副本
     * ，所以要考虑它对资源的消耗，比如内存的占用会比不使用ThreadLocal要大。
     */
    ThreadLocal<Integer> localWantedNumber = new ThreadLocal<Integer>();
    ThreadLocal<List<ProxyInfo>> localProxyInfos = new ThreadLocal<List<ProxyInfo>>();
    RedisConsts redisConsts = new RedisConsts("ipaget");

    /**
     * 参数 url 类型：System.String 此代理服务器 URL。 friendlyName 类型：System.String
     * 此服务器的显示名称 (可能为空)。 site 类型：System.String 此代理服务器站点 (可能为空)。 description
     * 类型：System.String 此代理服务器的说明。 flags
     * 类型：Microsoft.TeamFoundation.VersionControl.Common.ProxyFlags 此代理的标志。
     *
     * @param args
     */
    public static void main(String[] args) {
        IpUtil proxyCrawler = new IpUtil();
        /**
         * 想要获取的代理IP个数，由需求方自行指定。（如果个数太多，将导致返回变慢）
         */
        proxyCrawler.startCrawler(2);
    }

    /**
     * 暴露给外部模块调用的入口
     *
     * @param wantedNumber 调用方期望获取到的代理IP个数
     */
    public String startCrawler(Integer wantedNumber) {  //构造初始化 放要爬的网址 返回 动态代理对象
        localWantedNumber.set(wantedNumber);
        //agent("https://www.youdaili.net/Daili/guowai/4848.html", 50);
        List<String> baseUrl = baseurl;
        while(true){
            if(getCurrentProxyNumber() >= localWantedNumber.get()) {
                break;
            }
            agent(baseUrl.get(RandomUtils.nextInt(baseUrl.size())),totalpage);
        }
        /**
         * 构造返回数据
         */
        ProxyResponse response = new ProxyResponse();
        response.setSuccess("true");
        Map<String, Object> dataInfoMap = new HashMap<String, Object>();
        dataInfoMap.put("numFound", localProxyInfos.get().size());
        dataInfoMap.put("pageNum", 1);
        dataInfoMap.put("proxy", localProxyInfos.get());
        response.setData(dataInfoMap);
        String responseString = JSONObject.toJSON(response).toString();
        System.out.println(responseString);
        return responseString;
    }

    private void agent(String baseUrl, int totalPage) {//穿入网址url 翻的页书 获取可用ip
        String ipReg = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3} \\d{1,6}"; //HTTP[s]
        Pattern ipPtn = Pattern.compile(ipReg);
        /**
         * Java正则表达式Pattern和Matcher类详解 Pattern类的作用在于编译正则表达式后创建一个匹配模式.
         * Matcher类使用Pattern实例提供的模式信息对正则表达式进行匹配
         * 详情 :https://blog.csdn.net/yin380697242/article/details/52049999
         */
        for (int i = 1; i < totalPage; i++) {
            if (getCurrentProxyNumber() >= localWantedNumber.get()) {
                return;
            }
            try {
                Document doc = Jsoup.connect(baseUrl + i + "/")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate, sdch")
                        .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6").header("Cache-Control", "max-age=0")
                        .header("User-Agent", new UserAgent().getuserAgent())
                        .header("Cookie", "channelid=0; sid=1524061228171804; _ga=GA1.2.1186214781.1524062511;"
                                + " _gid=GA1.2.1268790863.1524062511; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1524062511;"
                                + " Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1524067278")
                        //.header("Host", "www.kuaidaili.com").header("Referer", "http://www.kuaidaili.com/free/intr/")
                        .timeout(30 * 1000).get();

                Matcher m = ipPtn.matcher(doc.text());

                while (m.find()) {

                    if (getCurrentProxyNumber() >= localWantedNumber.get()) {
                        break;
                    }
                    String[] ips = m.group().split(" "); //通过空格分割
                    String ip = ips[0];
                    String port = ips[1];
                    if (bloomFilter.contains(ip + port, redisConsts)) {
                        logger.info("以存在");
                    }
                    logger.info(ip + "  :" + port);
                    if (checkProxy(ip, Integer.parseInt(port))) {
                        logger.info("有效的: "+ip + "  :" + port);
                        addProxy(ip, port, "http");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkProxy(String ip, Integer port) { //验证是否有效 传ip  端口返回 true ,false
        try {
            // http://ip138.com 可以换成任何比较快的网页
            Jsoup.connect("http://ip138.com").timeout(2 * 1000).proxy(ip, port).get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int getCurrentProxyNumber() {
        List<ProxyInfo> proxyInfos = localProxyInfos.get();
        if (proxyInfos == null) {
            proxyInfos = new ArrayList<ProxyInfo>();
            localProxyInfos.set(proxyInfos);
            return 0;
        } else {
            return proxyInfos.size();
        }
    }

    private void addProxy(String ip, String port, String protocol) {
        List<ProxyInfo> proxyInfos = localProxyInfos.get();
        if (proxyInfos == null) {
            proxyInfos = new ArrayList<ProxyInfo>();
            proxyInfos.add(new ProxyInfo(ip, port, protocol));
        } else {
            proxyInfos.add(new ProxyInfo(ip, port, protocol));
        }
    }

    //获取代理ip
    public List<Ip> getip(int count) {
        /**
         * 想要获取的代理IP个数，由需求方自行指定。（如果个数太多，将导致返回变慢）
         */
        String joStr = startCrawler(count);

        JSONObject jso = JSON.parseObject(joStr);//json字符串转换成jsonobject对象
        jso = jso.getJSONObject("data");
        JSONArray jsarr = jso.getJSONArray("proxy");//jsonobject对象取得some对应的jsonarray数组

        Iterator<Object> address = jsarr.iterator();
        List<Ip> ips = new ArrayList<>();

        while (address.hasNext()) {
            JSONObject jobj = (JSONObject) address.next();
            Ip ip = new Ip();
            ip.setIp(jobj.getString("ip"));
            ip.setPort(jobj.getInteger("port"));
            ip.setDeal(jobj.getString("type"));
            ips.add(ip);
        }

        return ips;
    }
    public List<String> getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(List<String> baseurl) {
        this.baseurl = baseurl;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public Integer getWantedNumber() {
        return wantedNumber;
    }

    public void setWantedNumber(Integer wantedNumber) {
        this.wantedNumber = wantedNumber;
    }
}

class ProxyInfo {
    private String userName = "";
    private String ip;
    private String password = "";
    private String type;
    private String port;
    private int is_internet = 1;

    public ProxyInfo(String ip, String port, String type) {
        this.ip = ip;
        this.type = type;
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getIs_internet() {
        return is_internet;
    }

    public void setIs_internet(int is_internet) {
        this.is_internet = is_internet;
    }

}

class ProxyResponse {
    private String success;
    private Map<String, Object> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //// z追踪真实际ip
    /**
     * Firefox 跟 Chrome支持WebRTC可以向STUN服务器请求，返回内外网IP，不同于XMLHttpRequest请求，
     * STUN请求开发者工具当中看不到网络请求的。 //get the IP addresses associated with an account
     * function getIPs(callback){ var ip_dups = {};
     *
     * //compatibility for firefox and chrome var RTCPeerConnection =
     * window.RTCPeerConnection || window.mozRTCPeerConnection ||
     * window.webkitRTCPeerConnection; var mediaConstraints = { optional:
     * [{RtpDataChannels: true}] };
     *
     * //firefox already has a default stun server in about:config //
     * media.peerconnection.default_iceservers = // [{"url":
     * "stun:stun.services.mozilla.com"}] var servers = undefined;
     *
     * //add same stun server for chrome if(window.webkitRTCPeerConnection)
     * servers = {iceServers: [{urls: "stun:stun.services.mozilla.com"}]};
     *
     * //construct a new RTCPeerConnection var pc = new
     * RTCPeerConnection(servers, mediaConstraints);
     *
     * //listen for candidate events pc.onicecandidate = function(ice){
     *
     * //skip non-candidate events if(ice.candidate){
     *
     * //match just the IP address var ip_regex =
     * /([0-9]{1,3}(\.[0-9]{1,3}){3})/ var ip_addr =
     * ip_regex.exec(ice.candidate.candidate)[1];
     *
     * //remove duplicates if(ip_dups[ip_addr] === undefined) callback(ip_addr);
     *
     * ip_dups[ip_addr] = true; } };
     *
     * //create a bogus data channel pc.createDataChannel("");
     *
     * //create an offer sdp pc.createOffer(function(result){
     *
     * //trigger the stun server request pc.setLocalDescription(result,
     * function(){});
     *
     * }, function(){}); }
     *
     * //Test: Print the IP addresses into the console
     * getIPs(function(ip){console.log(ip);});
     */
}
