package com.soft.config;

import com.soft.utils.ParameterMapping;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoundSql {
    /**
     * 解析后的sql
     */
    private String sqlText;

    /**
     * 存放sql解析后#{}中的参数值
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();
}
