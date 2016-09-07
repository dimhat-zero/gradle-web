package org.dimhat.seckill.dao;

import org.dimhat.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by user on 2016/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccesskilled(){
        long id = 1000;
        long phone = 10086L;
        int count = successKilledDao.insertSuccesskilled(id, phone);
        //insert ignore忽略主键冲突
        System.out.println(count);
    }

    @Test
    public void testQueryByIdWithSeckill(){
        long seckillId=1000L;
        long userPhone=10086L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}
