package com.platform.aix.common.handler;
import cn.hutool.core.util.ObjectUtil;
import com.platform.aix.common.spring.AppService;
import com.platform.common.util.JsonAdaptor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgresql.util.PGobject;
import java.sql.*;
import java.io.IOException;
/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年05月08日 18:16
 */
public class JsonbTypeHandler<T> extends BaseTypeHandler<T> {
    private JsonAdaptor jsonAdaptor = AppService.getBean("jsonAdaptor");

    private final Class<T> type;

    public JsonbTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("jsonb");
        try {
            pgObject.setValue(jsonAdaptor.writeValueAsString(parameter));
        } catch (IOException e) {
            throw new SQLException("Error converting object to JSON string", e);
        }
        ps.setObject(i, pgObject);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJson(json);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJson(json);
    }

    private T parseJson(String json) {
        if (json == null){
            return null;
        }
        try {
            if(ObjectUtil.isEmpty(jsonAdaptor)){
                jsonAdaptor = new JsonAdaptor();
            }
            return jsonAdaptor.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON: " + json, e);
        }
    }
}
