package com.zy.cn.config;

import java.util.List;

public interface Loader<E> {

    List<E> load();
}