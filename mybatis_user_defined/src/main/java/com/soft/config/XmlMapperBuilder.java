package com.soft.config;

import com.soft.pojo.Configuration;
import com.soft.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 封装mapper文件信息
     * @param inputStream
     */
    public void parse(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);
        Element element = document.getRootElement();
        String namespace = element.attributeValue("namespace");

        List<Element> list = element.selectNodes("//select");
        for (Element e : list) {
            String id = e.attributeValue("id");
            String resultType = e.attributeValue("resultType");
            String paramterType = e.attributeValue("paramterType");
            String sql = e.getTextTrim();
            MappedStatement statement = new MappedStatement();
            statement.setId(id);
            statement.setSql(sql);
            statement.setResultType(resultType);
            statement.setParamterType(paramterType);
            configuration.getMap().put(namespace + "." + id, statement);
        }
    }
}
