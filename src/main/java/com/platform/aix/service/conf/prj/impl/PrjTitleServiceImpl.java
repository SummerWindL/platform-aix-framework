package com.platform.aix.service.conf.prj.impl;

import com.platform.aix.cmd.biz.baseconf.cmd41210.Cmd41210Req;
import com.platform.aix.cmd.biz.baseconf.cmd41220.Cmd41220Req;
import com.platform.aix.cmd.biz.baseconf.cmd41230.Cmd41230Req;
import com.platform.aix.cmd.biz.baseconf.cmd41240.Cmd41240Req;
import com.platform.aix.cmd.biz.baseconf.cmd41250.Cmd41250Req;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.service.base.AbstractServiceImpl;
import com.platform.aix.service.conf.prj.PrjTitleService;
import com.platform.aix.service.conf.prj.bean.TPrj;
import com.platform.aix.service.conf.prj.bean.TPrjTitle;
import com.platform.common.enumeration.EnumCountflagType;
import com.platform.common.util.UUIDUtils;
import com.platform.repo.pg.model.base.BasePlpgsqlModel;
import com.platform.repo.pg.repo.conf.prj.PrjQueryRepository;
import com.platform.repo.pg.repo.conf.prj.PrjRepository;
import com.platform.repo.pg.repo.conf.prj.PrjTitleRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 项目标题
 *
 * @author: Advance
 * @date: 2018/8/5
 * @description:
 */
@Service
public class PrjTitleServiceImpl extends AbstractServiceImpl implements PrjTitleService {

    @Autowired
    PrjTitleRepository mbPrjTitleRepository;

    @Autowired
    PrjRepository mbPrjRepository;

    @Autowired
    PrjQueryRepository fMbPrjQueryRepository;

    @Override
    public void insertPrjTitle(Cmd41210Req cmd41210Req) {
        BasePlpgsqlModel basePlpgsqlModel = mbPrjTitleRepository.insertPrjTitle(cmd41210Req.getProjectid(),
                UUIDUtils.getUUID(), cmd41210Req.getTitlename(), cmd41210Req.getTitlelogo());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public void deletePrjTitle(Cmd41220Req cmd41220Req) {
        BasePlpgsqlModel basePlpgsqlModel = mbPrjTitleRepository.deletePrjTitle(cmd41220Req.getProjectid(), cmd41220Req.getTitleid());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public void updatePrjTitle(Cmd41240Req cmd41240Req) {
        BasePlpgsqlModel basePlpgsqlModel = mbPrjTitleRepository.updatePrjTitle(cmd41240Req.getProjectid(),
                cmd41240Req.getTitleid(), cmd41240Req.getTitlename(), cmd41240Req.getTitlelogo());

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }
    }

    @Override
    public Page<TPrjTitle> queryPrjTitle(Cmd41230Req cmd41230Req) {

        BasePlpgsqlModel basePlpgsqlModel = mbPrjTitleRepository.queryPrjTitle(cmd41230Req.getProjectid(),
                cmd41230Req.getCountflag(),
                cmd41230Req.getOffset(),
                cmd41230Req.getPagesize()
        );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        TPrjTitle[] tPrjTitles = basePlpgsqlModel2Clz(basePlpgsqlModel, TPrjTitle[].class);
        List<TPrjTitle> tPrjTitleList = CollectionUtils.arrayToList(tPrjTitles);
        Page<TPrjTitle> tDxPrjTitlePage = new PageImpl<TPrjTitle>(tPrjTitleList, null, (long) basePlpgsqlModel.getRetcode());
        return tDxPrjTitlePage;
    }


    @Override
    public JSONArray queryPrjAndTitle(Cmd41250Req cmd41250Req) {
        BasePlpgsqlModel basePlpgsqlModel = fMbPrjQueryRepository.fMbPrjQuery("", "",cmd41250Req.getHospcode(),
                EnumCountflagType.LIST.getCountflag(),
                0,
                Integer.MAX_VALUE
        );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        TPrj[] tPrjs = basePlpgsqlModel2Clz(basePlpgsqlModel, TPrj[].class);
        List<TPrj> tDxPrjList = CollectionUtils.arrayToList(tPrjs);


        basePlpgsqlModel = mbPrjTitleRepository.queryPrjTitle("",
                EnumCountflagType.LIST.getCountflag(),
                0,
                Integer.MAX_VALUE
        );

        if (0 > basePlpgsqlModel.getRetcode()) {
            throw new BIZException(ResponseResult.DB_ERROR);
        }

        TPrjTitle[] tPrjTitles = basePlpgsqlModel2Clz(basePlpgsqlModel, TPrjTitle[].class);
        List<TPrjTitle> tPrjTitleList = CollectionUtils.arrayToList(tPrjTitles);

        JSONArray json = new JSONArray();
        for (TPrj tDxPrj : tDxPrjList) {
            JSONArray titleJson = new JSONArray();
            for (TPrjTitle tDxPrjTitle : tPrjTitleList) {
                if(tDxPrj.getProjectid().equals(tDxPrjTitle.getProjectid())){
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("value", tDxPrjTitle.getTitleid());
                    jsonObj.put("label", tDxPrjTitle.getTitlename());
                    titleJson.add(jsonObj);
                }
            }

            JSONObject jsonPrj = new JSONObject();
            jsonPrj.put("value", tDxPrj.getProjectid());
            jsonPrj.put("label", tDxPrj.getProjectname());
            if(titleJson.size()>0){
                jsonPrj.put("children", titleJson);
            }
            json.add(jsonPrj);
        }
        return json;
    }
}
