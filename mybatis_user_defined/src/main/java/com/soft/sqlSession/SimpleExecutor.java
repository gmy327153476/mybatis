package com.soft.sqlSession;

import com.soft.config.BoundSql;
import com.soft.pojo.Configuration;
import com.soft.pojo.MappedStatement;
import com.soft.utils.GenericTokenParser;
import com.soft.utils.ParameterMapping;
import com.soft.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor{
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 1.注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2.获取sql语句
        String sql = mappedStatement.getSql();
        // 对sql语句进行转义处理
        BoundSql boundSql = getBoundSql(sql);
        // 3.获取预处理对象
        PreparedStatement statement = connection.prepareStatement(boundSql.getSqlText());
        // 4.设置参数
        // 获取参数对象的全路径
        String parameterType = mappedStatement.getParamterType();
        Class<?> parameterTypeClass = getClassType(parameterType);

        //#{}中的参数名称
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            //存放的参数名称
            String parameterName = parameterMapping.getContent();

            //反射
            Field field = parameterTypeClass.getDeclaredField(parameterName);
            //暴力访问
            field.setAccessible(true);
            Object o = field.get(params[0]);

            statement.setObject(i + 1, o);
        }
        // 5.执行sql
        ResultSet resultSet = statement.executeQuery();
        // 获取返回值类型的全路径
        String resultType = mappedStatement.getResultType();
        Class<?> resultClassType = getClassType(resultType);

        // 6.封装返回结果集
        ArrayList<Object> list = new ArrayList<>();
        while (resultSet.next()) {
            Object o = resultClassType.newInstance();
            // 元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射或者内省，根据数据库和表的对应关系，进行封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClassType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);
            }
            list.add(o);
        }

        return (List<E>) list;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType != null) {
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    private BoundSql getBoundSql(String sql) {
        //使用mybatis中的标记解析器完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析后的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql();
        boundSql.setSqlText(parseSql);
        boundSql.setParameterMappingList(parameterMappingList);
        return boundSql;
    }
}
