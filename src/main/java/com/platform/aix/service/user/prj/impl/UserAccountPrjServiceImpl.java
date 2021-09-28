package com.platform.aix.service.user.prj.impl;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.prj.UserAccountPrjService;
import com.platform.aix.service.user.prj.bean.TUserAccountPrj;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.user.prj.UserAccountPrjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户项目配置
 *
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Service
public class UserAccountPrjServiceImpl extends AbstractServiceImpl implements UserAccountPrjService {

    @Autowired
    private UserAccountPrjRepository mbUserAccountPrjRepository;

    @Override
    public TUserAccountPrj[] queryUserAccountPrj(String userid) {
        BasePlpgsqlModel basePlpgsqlModel = mbUserAccountPrjRepository.queryUserAccountPrj(userid);

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        TUserAccountPrj[] tUserAccountPrjs = basePlpgsqlModel2Clz(basePlpgsqlModel, TUserAccountPrj[].class);

        return tUserAccountPrjs;
    }

    @Override
    public void saveUserAccountPrj(String userid, String projectid, String titleid) {

        BasePlpgsqlModel basePlpgsqlModel = mbUserAccountPrjRepository.saveUserAccountPrj(userid, projectid, titleid);

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }
}
