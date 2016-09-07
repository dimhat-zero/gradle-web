package org.dimhat.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.dimhat.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2016/9/7.
 */
public interface SeckillDao {

    /**
     *
     * @param seckillId
     * @param killTime
     * @return 更新的数量
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    Seckill queryById(long seckillId);

    /**
     * 多个参数默认arg0,arg1,单个参数没有问题
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
}
