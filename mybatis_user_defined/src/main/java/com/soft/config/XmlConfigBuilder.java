package com.soft.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.soft.io.Resources;
import com.soft.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder {
    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 封装数据源对象及mapper文件信息
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);
        Element element = document.getRootElement();
        //封装数据源
        List<Element> list = element.selectNodes("//property");
        Properties properties = new Properties();
        for (Element o : list) {
            properties.setProperty(o.attributeValue("name"), o.attributeValue("value"));

        }
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setDriverClass(properties.getProperty("driver"));
        pooledDataSource.setJdbcUrl(properties.getProperty("url"));
        pooledDataSource.setUser(properties.getProperty("username"));
        pooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(pooledDataSource);

        XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
        //封装mapper文件信息
        List<Element> mapperList = element.selectNodes("//mapper");
        for (Element e : mapperList) {
            String path = e.attributeValue("resource");
            //InputStream stream = this.getClass().getResourceAsStream("/" + path);
            InputStream stream = Resources.getResourceAsStream(path);
            xmlMapperBuilder.parse(stream);
        }
        return configuration;
    }
}
