package com.platform.aix.service.user.menu.impl;

import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.menu.UserAccountFunctionMenuService;
import com.platform.common.util.TreeJsonUtil;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.user.menu.UserAccountMenuRepository;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @date: 2018/4/19
 * @description:
 */
@Service
public class UserAccountFunctionMenuServiceImpl extends AbstractServiceImpl implements UserAccountFunctionMenuService {

    @Autowired
    private UserAccountMenuRepository userAccountMenuRepository;

    @Override
    public JSONArray getUserAccountFunctionMenuList(String acc) {
        BasePlpgsqlModel basePlpgsqlModel = this.userAccountMenuRepository.menuQuery(acc, 2);        //	TODO 强制指定为标准版，=2
        JSONArray menuJarr = new JSONArray();    //	定义要返回给前端使用的功能菜单JSONArray
        //	调用存储过程成功
        if (0 == basePlpgsqlModel.getRetcode()) {
            menuJarr = TreeJsonUtil.toMenuJson(basePlpgsqlModel.getRetvalue());
        } else {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
        return menuJarr;
    }
}
