package com.soft.sqlSession;

import com.soft.pojo.Configuration;

import java.lang.reflect.*;
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
        Object proxyInstance = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;
                Type genericReturnType = method.getGenericReturnType();
                //判断是否实现泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> list = selectList(statementId, args);
                    return list;
                }

                return selectOne(statementId, args);
            }
        });

        return (T) proxyInstance;
    }
}
