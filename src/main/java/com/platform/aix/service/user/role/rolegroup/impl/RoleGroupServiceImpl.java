package com.platform.aix.service.user.role.rolegroup.impl;

import com.platform.aix.cmd.biz.baseconf.cmd40610.Cmd40610Req;
import com.platform.aix.cmd.biz.baseconf.cmd40620.Cmd40620Req;
import com.platform.aix.cmd.biz.baseconf.cmd40630.Cmd40630Req;
import com.platform.aix.cmd.biz.baseconf.cmd40630.Cmd40630Resp;
import com.platform.aix.cmd.biz.baseconf.cmd40640.Cmd40640Req;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.user.role.rolegroup.RoleGroupService;
import com.platform.common.util.UUIDUtils;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.user.role.RoleGroupRepository;
import com.platform.repo.pg.repo.user.role.rolegroup.RoleGroupInsertRepository;
import com.platform.repo.pg.repo.user.role.rolegroup.RoleGroupUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 角色组
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Service
public class RoleGroupServiceImpl extends AbstractServiceImpl implements RoleGroupService {

    @Autowired
    RoleGroupRepository mbRoleGroupRepository;

    @Autowired
    RoleGroupInsertRepository fMbRoleGroupInsertRepository;
    @Autowired
    RoleGroupUpdateRepository fMbRoleGroupUpdateRepository;


    @Override
    public Page<Cmd40630Resp> queryDxRoleGroup(Cmd40630Req cmd40630Req) {
        BasePlpgsqlModel basePlpgsqlModel = mbRoleGroupRepository.queryDxRoleGroup(cmd40630Req.getRolegrpid(),
                cmd40630Req.getCountflag(),
                cmd40630Req.getOffset(),
                cmd40630Req.getPagesize());
        vaildateRetCode(basePlpgsqlModel.getRetcode(),basePlpgsqlModel.getRetvalue());
        return paginationContent(basePlpgsqlModel,Cmd40630Resp.class);

    }

    @Override
    public BasePlpgsqlModel insertDxRoleGroup(Cmd40610Req cmd40610Req) {
        return fMbRoleGroupInsertRepository.fMbRoleGroupInsert(UUIDUtils.getUUID(), cmd40610Req.getRolegrpname(),cmd40610Req.getRolegrpicon());
    }

    @Override
    public BasePlpgsqlModel updateDxRoleGroup(Cmd40640Req cmd40640Req) {
        return fMbRoleGroupUpdateRepository.fMbRoleGroupUpdate(cmd40640Req.getRolegrpid(), cmd40640Req.getRolegrpname(),cmd40640Req.getRolegrpicon());
    }

    @Override
    public BasePlpgsqlModel deleteDxRoleGroup(Cmd40620Req cmd40620Req) {
        return mbRoleGroupRepository.deleteDxRoleGroup(cmd40620Req.getRolegrpid());
    }

}
