package com.soft.sqlSession;

import com.soft.pojo.Configuration;
import com.soft.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, Exception;

}
