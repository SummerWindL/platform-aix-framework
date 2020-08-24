package com.platform.aix.service.base;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.util.JsonAdaptor;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
public class AbstractServiceImpl {

    private static JsonAdaptor jsonAdaptor = getJsonAdaptor();

    private static JsonAdaptor getJsonAdaptor() {
        JsonAdaptor jsonAdaptor = new JsonAdaptor();
        jsonAdaptor.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        return jsonAdaptor;
    }

    public <T> T basePlpgsqlModel2Clz(BasePlpgsqlModel basePlpgsqlModel, Class<T> clz) {
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

    /*** 
    * @Description: 统一分页 
    * @Param: [basePlpgsqlModel, t] 
    * @return: org.springframework.data.domain.Page<T> 
    * @Author: fuyl 
    * @Date: 2019/7/17 
    */ 
    public <T> Page<T> paginationContent(BasePlpgsqlModel basePlpgsqlModel , Class<T> t){

        T[] arr = (T[]) Array.newInstance(t, 0);

        T[] arrBean = (T[]) basePlpgsqlModel2Clz(basePlpgsqlModel, arr.getClass());

        List<T> arrBeanList = CollectionUtils.arrayToList(arrBean);
        Page<T> arrBeanPage = new PageImpl<T>(arrBeanList, null, (long) basePlpgsqlModel.getRetcode());

        return arrBeanPage;
    }

    /*** 
    * @Description: 统一处理异常错误 dry原则
    * @Param: [retcode] 
    * @return: void 
    * @Author: fuyl 
    * @Date: 2019/7/17 
    */ 
    public void vaildateRetCode(int retcode){
        if(retcode == -11){ //-11 doctor app already register
            throw new BIZException(ResponseResult.BIZ_ERROR_MB_USER_DOCTOR_APP_IS_REGISTERED);
        }else if(retcode == -10){ //-10 admin user not delete
            throw new BIZException(ResponseResult.BIZ_ERROR_ADMIN_NOT_DELETE);
        }else if(retcode == -9){ //-9 data already sync,syncflag = 10001
            throw new BIZException(ResponseResult.BIZ_ERROR_DATA_ALREADY_SYNC);
        }else if(retcode == -8){ //-8 admin not exist
            throw new BIZException(ResponseResult.BIZ_ERROR_USER_NOT_EXIST);
        }else if(retcode == -7){ //-7 param is not correct
            throw new BIZException(ResponseResult.HTTP_ERROR_INVALID_PARAM);
        }else if(retcode == -6){ //-6 field value is null or empty
            throw new BIZException(ResponseResult.COMMON_ERROR_PARAM_NOT_NULL);
        }else if(retcode == -5){ //-5 update syncflag error
            throw new BIZException(ResponseResult.BIZ_ERROR_UPDATE_DATA_SYNC);
        }else if(retcode == -4){ //-4 account or pwd not correct
            throw new BIZException(ResponseResult.BIZ_ERROR_USER_PWD_WRONG);
        }else if(retcode == -3){ //-3 linked other data
            throw new BIZException(ResponseResult.BIZ_ERROR_LINK_OTHER_DATA);
        }else if(retcode == -2){ //-2 record not exist
            throw new BIZException(ResponseResult.DB_ERROR_RECORD_NOTEXIST);
        }else if(retcode == -1){ //-1 record already exist
            throw new BIZException(ResponseResult.DB_ERROR_RECORD_EXIST);
        }else if(retcode < 0){
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    public void vaildateRetCode(int retcode, String retvalue){
        if(retcode == 0){ //成功
            log.debug("数据库操作日志：{}",retvalue);
        }else if(retcode <0) {
            log.error("数据库错误日志：{}",retvalue);
            if(retcode == -11){ //-11 doctor app already register
                throw new BIZException(ResponseResult.BIZ_ERROR_MB_USER_DOCTOR_APP_IS_REGISTERED);
            }else if(retcode == -10){ //-10 admin user not delete
                throw new BIZException(ResponseResult.BIZ_ERROR_ADMIN_NOT_DELETE);
            }else if(retcode == -9){ //-9 data already sync,syncflag = 10001
                throw new BIZException(ResponseResult.BIZ_ERROR_DATA_ALREADY_SYNC);
            }else if(retcode == -8){ //-8 admin not exist
                throw new BIZException(ResponseResult.BIZ_ERROR_USER_NOT_EXIST);
            }else if(retcode == -7){ //-7 param is not correct
                throw new BIZException(ResponseResult.HTTP_ERROR_INVALID_PARAM);
            }else if(retcode == -6){ //-6 field value is null or empty
                throw new BIZException(ResponseResult.COMMON_ERROR_PARAM_NOT_NULL);
            }else if(retcode == -5){ //-5 update syncflag error
                throw new BIZException(ResponseResult.BIZ_ERROR_UPDATE_DATA_SYNC);
            }else if(retcode == -4){ //-4 account or pwd not correct
                throw new BIZException(ResponseResult.BIZ_ERROR_USER_PWD_WRONG);
            }else if(retcode == -3){ //-3 linked other data
                throw new BIZException(ResponseResult.BIZ_ERROR_LINK_OTHER_DATA);
            }else if(retcode == -2){ //-2 record not exist
                throw new BIZException(ResponseResult.DB_ERROR_RECORD_NOTEXIST);
            }else if(retcode == -1){ //-1 record already exist
                throw new BIZException(ResponseResult.DB_ERROR_RECORD_EXIST);
            }else if(retcode < 0){
                throw new BIZException(ResponseResult.DB_ERROR);
            }
        }


    }
}
