package com.soft.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {

    /**
     * 存放数据库连接信息
     */
    private DataSource dataSource;

    /**
     * 存放mapper文件数据
     */
    Map<String, MappedStatement> map = new HashMap<>();
}
