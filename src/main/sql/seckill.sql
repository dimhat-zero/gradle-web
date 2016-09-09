DELIMITER $$ -- console
--定义存储过程
--参数：in输入参数，out输出参数
--row_count前一行影响行数
CREATE PROCEDURE `seckill`.`execite_seckill`
  (in v_seckill_id bitint,int v_phone bigint,in v_kill_time timestamp,out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION;
    insert ignore into success_killed(seckill_id,user_phone,craete_time)
      values(v_seckill_id,v_phone,v_kill_time);
    select row_count() into insert_count;
    IF (insert_count=0) THEN
      ROLLBACK ;
      r_result = -1;
    ELSEIF(insert_count<0)
      ROLLBACK ;
      r_result = -2;
    ELSE
      update seckill
      set number=  number-1
      where seckill_id  = v_seckill_id
        and end_time>v_kill_time
        and start_time<v_kill_time
        and nubmer > 0
      select row_count() into insert_count;
      IF(insert_count==0) THEN
        ROLLBACK ;
        r_result = 0;
      ELSEIF(insert_count<0) THEN
        ROLLBACK ;
        r_result = -2;
      ELSE
        COMMIT;
        set r_result=1;
      END IF;
    END IF;
  END;
$$
--存储过程结束
DELIMITER;

set @r_result=-3;
call execute_seckill(1003,10086,now(),@r_result);
select @r_result;
