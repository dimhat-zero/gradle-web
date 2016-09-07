package org.dimhat.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.dimhat.seckill.entity.SuccessKilled;

/**
 * Created by user on 2016/9/7.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccesskilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
