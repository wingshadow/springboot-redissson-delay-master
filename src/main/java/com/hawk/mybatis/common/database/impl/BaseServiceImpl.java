package com.hawk.mybatis.common.database.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.hawk.mybatis.common.base.BaseDO;
import com.hawk.mybatis.common.database.BaseMapper;
import com.hawk.mybatis.common.database.BaseService;
import com.hawk.mybatis.common.seq.snowflake.Snowflake;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 14:36
 */
public abstract class BaseServiceImpl<T extends BaseDO> implements BaseService<T> {

    @Resource(name = "snowflake")
    private Snowflake snowflake;

    @Resource
    private BaseMapper<T> baseMapper;

    private Class<T> cache = null;


    private Long getNextId(){
        return snowflake.nextId();
    }
    @Override
    public Long insert(T bean) {
        if (Objects.isNull(bean.getId()) || bean.getId() <= 0L) {
            bean.setId(getNextId());
        }
        baseMapper.insert(bean);
        return bean.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<T> paramBeans) {
        for (T item : paramBeans) {
            Long newId = getNextId();
            item.setId(newId);
            baseMapper.insert(item);
        }
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatchByPrimaryKeys(String ids) {
        String[] stringList = StringUtils.split(ids, ",");
        List<Long> idLst = Arrays.stream(stringList).map(Long::parseLong).collect(Collectors.toList());
        deleteBatchByPrimaryKeys(idLst);
    }

    @Override
    public void updateByPrimaryKeySelective(T paramBean) {
        baseMapper.updateByPrimaryKeySelective(paramBean);
    }

    @Override
    public T getByPrimaryKey(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> listByConditions(T paramBean) {
        return baseMapper.select(paramBean);
    }

    @Override
    public List<T> listAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T listOne(T paramBean) {
        return baseMapper.selectOne(paramBean);
    }

    @Override
    public PageInfo<T> listByPage(T paramBean, int pageNum, int pageSize) {
        PageMethod.startPage(pageNum, pageSize);
        List<T> lst = baseMapper.select(paramBean);
        return new PageInfo<>(lst);
    }

    @Override
    public void deleteBatchByPrimaryKeys(List<Long> ids) {
        ids.forEach(this::deleteByPrimaryKey);
    }


    @Override
    public Class<T> getTypeArguement() {
        if (cache == null) {
            cache = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return cache;
    }
}