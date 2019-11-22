package com.zy.cn.config;

/***
 * 取消任务转发的接口
 * @param <E>
 */
public interface CancelDispatch<E> extends Dispatcher<E>{
    boolean cancel(E e);
}