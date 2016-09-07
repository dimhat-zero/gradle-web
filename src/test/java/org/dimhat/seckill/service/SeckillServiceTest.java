package org.dimhat.seckill.service;

import org.dimhat.seckill.dto.Exposer;
import org.dimhat.seckill.dto.SeckillExecution;
import org.dimhat.seckill.entity.Seckill;
import org.dimhat.seckill.exception.RepeatKillException;
import org.dimhat.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list = {}",seckillList);
    }

    @Test
    public void getById() throws Exception {
        long seckillId=1000L;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        Long id = 1000L;
        long phone = 12345678L;
        String md5="c997101c6a48a6d39fc9db85bc0dd09d";
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("result={}",execution);
    }

    //测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic(){
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);

            long phone = 12345678L;
            String md5=exposer.getMd5();
            try{
                SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }

        }else{
            logger.warn("秒杀未开启 exposer={}",exposer);
        }

    }

}