package org.dimhat.seckill.service.impl;

import org.dimhat.seckill.dao.SeckillDao;
import org.dimhat.seckill.dao.SuccessKilledDao;
import org.dimhat.seckill.dto.Exposer;
import org.dimhat.seckill.dto.SeckillExecution;
import org.dimhat.seckill.entity.Seckill;
import org.dimhat.seckill.entity.SuccessKilled;
import org.dimhat.seckill.enums.SeckillStatEnum;
import org.dimhat.seckill.exception.RepeatKillException;
import org.dimhat.seckill.exception.SeckillCloseException;
import org.dimhat.seckill.exception.SeckillException;
import org.dimhat.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注解事务优点
 * 1.简单
 * 2.保证事务方法执行时间短，不要有RPC/HTTP请求
 * 3.不是所有方法都需要事务，只修改一条或只读不需要事务
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String salt="saadgdhsdasfrhfnf23434345";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Transactional
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill==null) {
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        String md5=getMD5(seckillId);
        return new Exposer(true,seckillId,md5);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+salt;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 优化insert在update之前，减少update的锁竞争
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        if(md5==null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        try{
            int insertCount = successKilledDao.insertSuccesskilled(seckillId, userPhone);
            if(insertCount<=0){
                throw new RepeatKillException("seckill repeated");
            }else{
                int reduceNumber = seckillDao.reduceNumber(seckillId, new Date());
                if(reduceNumber<=0){
                    throw new SeckillCloseException("seckill is colsed");
                }else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS,successKilled);
                }
            }

        }catch (SeckillCloseException e){
            throw e;
        } catch (RepeatKillException e){
            throw e;
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }

    }

    public SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5){
        if(md5==null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime  = new Date();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",null);
        try {
            seckillDao.killByProcedure(map);
            //MapUtils.getInteger(map,"result",-2);
            int result = map.get("result")==null?-2: (Integer) map.get("result");
            if(result ==1 ){
                SuccessKilled sk  = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,sk);
            }else{
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }
}
