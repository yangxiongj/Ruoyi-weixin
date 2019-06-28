package com.ruoyi.spider.domin;

import java.time.Duration;
import java.time.Instant;

public class Ip {
    private String ip;
    private Integer port;
    private String deal;
    private Duration timeout;
    private Instant timestamp = Instant.now();

    public Ip(String ip, Integer port, String deal, Duration timeout, Instant timestamp) {
        this.ip = ip;
        this.port = port;
        this.deal = deal;
        this.timeout = timeout;
        this.timestamp = timestamp;
    }

    public Ip() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    @Override
    public String toString() {
        return "Ip{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", deal='" + deal + '\'' +
                ", timeout=" + timeout +
                ", timestamp=" + timestamp +
                '}';
    }
}
