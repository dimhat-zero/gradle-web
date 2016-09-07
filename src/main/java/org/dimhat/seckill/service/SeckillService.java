package org.dimhat.seckill.service;

import org.dimhat.seckill.dto.Exposer;
import org.dimhat.seckill.dto.SeckillExecution;
import org.dimhat.seckill.entity.Seckill;
import org.dimhat.seckill.exception.RepeatKillException;
import org.dimhat.seckill.exception.SeckillCloseException;
import org.dimhat.seckill.exception.SeckillException;

import java.util.List;

/**
 * 站在使用者角度设计接口
 * 方法定义粒度：执行秒杀
 * 参数：简单
 * 返回类型：友好，可抛出异常
 */
public interface SeckillService {

    List<Seckill> getSeckillList();

    Seckill getById(long seckillId);

    /**
     * 秒杀开启时 输出秒杀接口
     * 否则输出系统时间和秒杀时间
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,SeckillCloseException,RepeatKillException;


}
