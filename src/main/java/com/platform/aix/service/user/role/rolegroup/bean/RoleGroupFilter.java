package com.platform.aix.service.user.role.rolegroup.bean;

import com.platform.aix.cmd.bean.filter.BasePageFilter;
import lombok.Data;

@Data
public class RoleGroupFilter extends BasePageFilter {

    private String rolegrpid = "";
    private String rolegrpname = "";

    private String userid = "";
}