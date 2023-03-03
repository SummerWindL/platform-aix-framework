package com.platform.aix.common.util;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.common.util.JsonAdaptor;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author Advance
 * @date 2022年06月21日 15:16
 * @since V1.0.0
 */
public class BeanUtils  {

    private static JsonAdaptor jsonAdaptor = getJsonAdaptor();

    private static JsonAdaptor getJsonAdaptor() {
        JsonAdaptor jsonAdaptor = new JsonAdaptor();
        jsonAdaptor.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return jsonAdaptor;
    }
    public static <T> T basePlpgsqlModel2Clz(BasePlpgsqlModel basePlpgsqlModel, Class<T> clz) {
        if (0 <= basePlpgsqlModel.getRetcode()) {
            String jsonstr = basePlpgsqlModel.getRetvalue();
            if (null != jsonstr) {
                T t = null;
                try {
                    t = jsonAdaptor.readValue(jsonstr, clz);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BIZException(ResponseResult.BIZ_ERROR_DBJSON2CLZ);
                }
                return t;
            }
        }
        return null;
    }
}
