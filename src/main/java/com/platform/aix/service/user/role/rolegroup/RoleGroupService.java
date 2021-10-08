package com.platform.aix.service.user.role.rolegroup;


import com.platform.aix.cmd.biz.baseconf.cmd40610.Cmd40610Req;
import com.platform.aix.cmd.biz.baseconf.cmd40620.Cmd40620Req;
import com.platform.aix.cmd.biz.baseconf.cmd40630.Cmd40630Req;
import com.platform.aix.cmd.biz.baseconf.cmd40630.Cmd40630Resp;
import com.platform.aix.cmd.biz.baseconf.cmd40640.Cmd40640Req;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import org.springframework.data.domain.Page;

/**
 * 角色组
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
public interface RoleGroupService {

    Page<Cmd40630Resp> queryDxRoleGroup(Cmd40630Req cmd40630Req);

    BasePlpgsqlModel insertDxRoleGroup(Cmd40610Req cmd40610Req);

    BasePlpgsqlModel updateDxRoleGroup(Cmd40640Req cmd40640Req);

    BasePlpgsqlModel deleteDxRoleGroup(Cmd40620Req cmd40620Req);
}
