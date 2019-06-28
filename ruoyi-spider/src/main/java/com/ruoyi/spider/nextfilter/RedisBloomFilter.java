/*
 * Copyright (C) 2017 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.ruoyi.spider.nextfilter;

import cn.edu.hfut.dmic.webcollector.fetcher.NextFilter;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import com.ruoyi.spider.utils.BloomFilter;
import com.ruoyi.spider.utils.RedisConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;

/**
 * Filter nextItem whose key is contained in the hashset
 * @author hu
 */
public class RedisBloomFilter extends BloomFilter{
    //volatile 关键字，解决双重校验带来的弊端
    private static volatile RedisBloomFilter filter = null ;

    private RedisBloomFilter(RedisConsts redisConsts, int expireDays) {
        this.redisConsts = redisConsts;
        this.expireDays = expireDays;
    }

    //单例模式获取去重对象  自己定制key 及失效时间
    public static RedisBloomFilter getFilter(RedisConsts redisConsts, int expireDays) {
        if(filter ==null){
            synchronized(RedisBloomFilter.class){
                if ( filter == null ) {
                    filter = new RedisBloomFilter(redisConsts,expireDays) ;
                }
            }
        }
        return filter;
    }
    //单例模式获取默认去重对象
    public static RedisBloomFilter getFilter() {
        if(filter ==null){
            synchronized(RedisBloomFilter.class){
                if ( filter == null ) {
                    filter = new RedisBloomFilter() ;
                }
            }
        }
        return filter;
    }

    public RedisBloomFilter() {
    }
}
