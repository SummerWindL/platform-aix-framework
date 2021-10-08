package com.platform.aix.cmd.biz.baseconf.cmd40630;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.user.role.rolegroup.RoleGroupService;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * 查询角色
 *
 * @author: Advance
 * @date: 2018/7/28
 * @description:
 */
@Slf4j
@Controller(Constants.CLIENT_REQ_URL + "/cmd_40630")
@DS("master")
public class Cmd40630Handler extends CommandBaseHandler {

    @Autowired
    RoleGroupService roleGroupService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd40630Req.class;
    }

    @Override
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        MysqlDataTest mysql= new MysqlDataTest(jdbcTemplate);
        log.info("mysql查询结果：{}",mysql.selectAll());
        Cmd40630Req req = (Cmd40630Req) request;
        Page<Cmd40630Resp> cmd40630Resps = this.roleGroupService.queryDxRoleGroup(req);
        List<Cmd40630Resp> content = cmd40630Resps.getContent();
        Page<Cmd40630Resp> cmd40630RespsPage = new PageImpl<Cmd40630Resp>(content,PageRequest.of(0,20),cmd40630Resps.getTotalElements());
        return new APIResponse(cmd40630RespsPage);
    }
}
