package com.zy.cn.config;

/***
 * 定义任务转发接口
 * @param <E>
 */
public interface Dispatcher<E> {
    // 是否转发
    boolean canDispatch(E e);
    // 转发
    boolean dispatch(E e);
}