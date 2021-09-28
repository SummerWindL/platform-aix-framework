package com.platform.aix.service.conf.prj;

import com.platform.aix.cmd.biz.baseconf.cmd41210.Cmd41210Req;
import com.platform.aix.cmd.biz.baseconf.cmd41220.Cmd41220Req;
import com.platform.aix.cmd.biz.baseconf.cmd41230.Cmd41230Req;
import com.platform.aix.cmd.biz.baseconf.cmd41240.Cmd41240Req;
import com.platform.aix.cmd.biz.baseconf.cmd41250.Cmd41250Req;
import com.platform.aix.service.conf.prj.bean.TPrjTitle;
import net.sf.json.JSONArray;
import org.springframework.data.domain.Page;

/**
 * 项目标题
 *
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
public interface PrjTitleService {

    public void insertPrjTitle(Cmd41210Req cmd41210Req);

    public void deletePrjTitle(Cmd41220Req cmd41220Req);

    public Page<TPrjTitle> queryPrjTitle(Cmd41230Req cmd41230Req);

    public void updatePrjTitle(Cmd41240Req cmd41240Req);

    public JSONArray queryPrjAndTitle(Cmd41250Req cmd41250Req);
}
