package com.soft.sqlSession;

import com.soft.pojo.Configuration;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        //去simpleExecutor中得query进行处理
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, configuration.getMap().get(statementid), params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = this.selectList(statementid, params);
        if(objects.size() == 1) {
            return (T) objects.get(0);
        }
        throw  new RuntimeException("查询结果为空或者相应结果过多");
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        return null;
    }
}
