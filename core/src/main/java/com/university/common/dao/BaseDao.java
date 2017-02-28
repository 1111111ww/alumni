package com.university.common.dao;


import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * 基础数据库操作类
 *
 * @author ss
 */
public interface BaseDao<T> {

    /**
     * 保存一个对象
     *
     * @param o
     * @return
     */
    public Serializable save(T o);

    /**
     * 删除一个对象
     *
     * @param o
     */
    public void delete(T o);

    /**
     * 更新一个对象
     *
     * @param o
     */
    public void update(T o);

    /**
     * 保存或更新对象
     *
     * @param o
     */
    public void saveOrUpdate(T o);

    /**
     * 查询
     *
     * @param hql
     * @return
     */
    public List<T> find(String hql);

    /**
     * 查询集合
     *
     * @param hql
     * @param param
     * @return
     */
    public List<T> find(String hql, Object[] param);

    /**
     * 查询集合
     *
     * @param hql
     * @param param
     * @return
     */
    public List<T> find(String hql, List<Object> param);

    /**
     * 查询集合(带分页)
     *
     * @param hql
     * @param param
     * @param page  查询第几页
     * @param rows  每页显示几条记录
     * @return
     */
    public List<T> find(String hql, Object[] param, Integer page, Integer rows);

    /**
     * 查询集合(带分页)
     *
     * @param hql
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public List<T> find(String hql, List<Object> param, Integer page, Integer rows);

    /**
     * 获得一个对象
     *
     * @param c  对象类型
     * @param id
     * @return Object
     */
    public T get(Class<T> c, Serializable id);

    /**
     * 获得一个对象
     *
     * @param hql
     * @param param
     * @return Object
     */
    public T get(String hql, Object[] param);

    /**
     * 获得一个对象
     *
     * @param hql
     * @param param
     * @return
     */
    public T get(String hql, List<Object> param);

    /**
     * select count(*) from 类
     *
     * @param hql
     * @return
     */
    public Long count(String hql);

    /**
     * select count(*) from 类
     *
     * @param hql
     * @param param
     * @return
     */
    public Long count(String hql, Object[] param);

    /**
     * select count(*) from 类
     *
     * @param hql
     * @param param
     * @return
     */
    public Long count(String hql, List<Object> param);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @return 响应数目
     */
    public Integer executeHql(String hql);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param param
     * @return 响应数目
     */
    public Integer executeHql(String hql, Object[] param);

    /**
     * 执行HQL语句
     *
     * @param hql
     * @param param
     * @return
     */
    public Integer executeHql(String hql, List<Object> param);


    JdbcTemplate getJdbcTemplate();
}
