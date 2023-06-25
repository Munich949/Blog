package com.dlnu.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 这个类是 DateTypeHandler，是一个自定义的类型处理器，用于在使用 MyBatis 与数据库交互时将 java.util.Date 映射为 String 类型。
 */
@MappedJdbcTypes(JdbcType.DATE)
@MappedTypes(String.class)
public class DateTypeHandler implements TypeHandler<String> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) {
        // 设置 PreparedStatement 的参数
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        // 从 ResultSet 中获取结果，并将其转换为 String 类型
        return sdf.format(rs.getDate(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        // 从 ResultSet 中获取结果，并将其转换为 String 类型
        return sdf.format(rs.getDate(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 从 CallableStatement 中获取结果，并将其转换为 String 类型
        return sdf.format(cs.getDate(columnIndex));
    }
}