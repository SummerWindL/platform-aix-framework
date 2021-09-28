package com.platform.aix.service.conf.prj;

import com.platform.aix.cmd.biz.baseconf.cmd41110.Cmd41110Req;
import com.platform.aix.cmd.biz.baseconf.cmd41120.Cmd41120Req;
import com.platform.aix.cmd.biz.baseconf.cmd41130.Cmd41130Req;
import com.platform.aix.cmd.biz.baseconf.cmd41140.Cmd41140Req;
import com.platform.aix.service.conf.prj.bean.TPrj;
import org.springframework.data.domain.Page;

/**
 * 项目
 *
 * @author: Allen
 * @date: 2018/8/5
 * @description:
 */
public interface PrjService {

    public void insertPrj(Cmd41110Req cmd41110Req);

    public void deletePrj(Cmd41120Req cmd41120Req);

    public void updatePrj(Cmd41140Req cmd41140Req);

    public Page<TPrj> queryPrj(Cmd41130Req cmd41130Req);
}
