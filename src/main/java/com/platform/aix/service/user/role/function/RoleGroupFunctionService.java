package com.platform.aix.service.user.role.function;

import com.platform.aix.cmd.biz.baseconf.cmd40910.Cmd40910Req;
import com.platform.aix.cmd.biz.baseconf.cmd40920.Cmd40920Req;
import com.platform.aix.cmd.biz.baseconf.cmd40930.Cmd40930Req;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import net.sf.json.JSONObject;

/**
 * 角色组功能关联
 *
 * @author: Advance
 * @date: 2018/8/1
 * @description:
 */
public interface RoleGroupFunctionService {

    JSONObject queryRoleGroupFunction(Cmd40910Req cmd40910Req);

    BasePlpgsqlModel saveRoleGroupFunction(Cmd40920Req cmd40920Req);

    BasePlpgsqlModel deleteRoleGroupFunction(Cmd40930Req cmd40930Req);
}
