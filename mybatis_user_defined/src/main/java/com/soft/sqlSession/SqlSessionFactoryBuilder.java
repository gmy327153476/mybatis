package com.soft.sqlSession;

import com.soft.config.XmlConfigBuilder;
import com.soft.pojo.Configuration;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        //使用dom4j解析xml，并封装到configuration对象
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        //生产sqlSession：会话对象
        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
