package com.platform.aix.service.conf.prj.impl;

import com.platform.aix.cmd.biz.baseconf.cmd41110.Cmd41110Req;
import com.platform.aix.cmd.biz.baseconf.cmd41120.Cmd41120Req;
import com.platform.aix.cmd.biz.baseconf.cmd41130.Cmd41130Req;
import com.platform.aix.cmd.biz.baseconf.cmd41140.Cmd41140Req;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.conf.prj.PrjService;
import com.platform.aix.service.conf.prj.bean.TPrj;
import com.platform.common.util.UUIDUtils;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.conf.prj.PrjInsertRepository;
import com.platform.repo.pg.repo.conf.prj.PrjQueryRepository;
import com.platform.repo.pg.repo.conf.prj.PrjRepository;
import com.platform.repo.pg.repo.conf.prj.PrjUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 项目
 *
 * @author: fyw
 * @date: 2018/8/5
 * @description:
 */
@Service
public class PrjServiceImpl extends AbstractServiceImpl implements PrjService {

    @Autowired
    PrjRepository mbPrjRepository;

    @Autowired
    PrjQueryRepository fMbPrjQueryRepository;

    @Autowired
    PrjInsertRepository fMbPrjInsertRepository;

    @Autowired
    PrjUpdateRepository fMbPrjUpdateRepository;

    @Override
    public void insertPrj(Cmd41110Req cmd41110Req) {
        BasePlpgsqlModel basePlpgsqlModel = fMbPrjInsertRepository.fMbPrjInsert(UUIDUtils.getUUID(),
                cmd41110Req.getProjectname(), cmd41110Req.getProjectdesc(),cmd41110Req.getHospcode());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public void deletePrj(Cmd41120Req cmd41120Req) {
        BasePlpgsqlModel basePlpgsqlModel = mbPrjRepository.deletePrj(cmd41120Req.getProjectid());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public void updatePrj(Cmd41140Req cmd41140Req) {
        BasePlpgsqlModel basePlpgsqlModel = fMbPrjUpdateRepository.fMbPrjUpdate(cmd41140Req.getProjectid(),
                cmd41140Req.getProjectname(), cmd41140Req.getProjectdesc(),cmd41140Req.getHospcode());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public Page<TPrj> queryPrj(Cmd41130Req cmd41130Req) {
        BasePlpgsqlModel basePlpgsqlModel = fMbPrjQueryRepository.fMbPrjQuery(cmd41130Req.getProjectid(),
                cmd41130Req.getProjectname(),
                cmd41130Req.getHospcode(),
                cmd41130Req.getCountflag(),
                cmd41130Req.getOffset(),
                cmd41130Req.getPagesize() );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        Page<TPrj> tDxPrjPage = paginationContent(basePlpgsqlModel, TPrj.class);
        /*TPrj[] tPrjs = basePlpgsqlModel2Clz(basePlpgsqlModel, TPrj[].class);
        List<TPrj> tPrjList = CollectionUtils.arrayToList(tPrjs);
        Page<TPrj> tDxPrjPage = new PageImpl<TPrj>(tPrjList, null, (long) basePlpgsqlModel.getRetcode());*/
        return tDxPrjPage;
    }

}
