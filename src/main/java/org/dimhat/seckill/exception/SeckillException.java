package org.dimhat.seckill.exception;

/**
 * Created by user on 2016/9/7.
 */
public class SeckillException extends  RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
