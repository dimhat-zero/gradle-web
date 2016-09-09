package org.dimhat.seckill.dao.cache;

import org.dimhat.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by think on 2016/9/9.
 */
public class RedisDao {

    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    private  JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckillId:"+seckillId;
                String json = jedis.get(key);
                if(json!=null){
                    Seckill seckill = new Seckill();//json to obj
                    return  seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckillId:"+seckill.getSeckillId();
                jedis.setex(key,60*60,seckill.toString());//set json str
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
