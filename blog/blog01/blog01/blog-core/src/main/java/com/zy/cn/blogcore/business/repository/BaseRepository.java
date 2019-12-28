package com.zy.cn.blogcore.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
/**
 * @ClassName BaseRepository
 * @Description 自定义一个Repository,它是JpaRepository的功能基础上继承增强
 * @Description 在上面添加@NoRepositoryBean标注，这样Spring Data Jpa在启动时就不会去实例化BaseRepository这个接口
 * @Author 狗蛋
 * @Date 2019/12/28 上午8:47
 * @Version 1.0
 */
@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable>
        extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {
}
