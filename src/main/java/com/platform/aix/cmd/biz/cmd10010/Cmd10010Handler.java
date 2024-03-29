package com.platform.aix.cmd.biz.cmd10010;

import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.annotation.Access;
import com.platform.aix.common.annotation.AccessLimit;
import com.platform.aix.common.annotation.DisableAccess;
import com.platform.aix.common.annotation.DisableCheckAuthHeader;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.exception.BIZException;
import com.platform.aix.common.handler.CommandBaseHandler;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.service.user.inspection.UserInspectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * @description:
 * @author: fuyl
 * @create: 2020-08-24 15:26
 **/
//@DisableAccess
@Access
@Controller(Constants.CLIENT_REQ_URL+"/cmd_10010")
@DisableCheckAuthHeader
public class Cmd10010Handler extends CommandBaseHandler {

//    @Autowired
//    private MemberService memberService;
    @Autowired
    private UserInspectionDataService userInspectionDataService;

    @Override
    public Class<? extends BaseRequest> getRequestClass() {
        return Cmd10010Req.class;
    }

    @Override
    @AccessLimit(limit = 1,sec = 10) //该接口访问限制 10秒一次
    public APIResponse execute(BaseRequest request) throws BIZException, IOException {
        Cmd10010Req req = (Cmd10010Req) request;
        //1.初始化 member 信息
//        memberService.getMemberInfo(req.getMember());
        //2.数据转换
//        List<InspectionDatas> inspectionDatas = InspectionAdapter.convertData(req);
//        userInspectionDataService.batchInsertInspectionData(inspectionDatas);
        return new APIResponse(0);
    }
}
