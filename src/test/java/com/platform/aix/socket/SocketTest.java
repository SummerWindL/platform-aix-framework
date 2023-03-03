package com.platform.aix.socket;

import com.platform.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Advance
 * @date 2023年02月02日 11:15
 * @since V1.0.0
 */
public class SocketTest {
    public static void main(String[] args) {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><!--ESB返回报文头开始（不要轻易修改）--><SYS_HEAD><Mac>0000000000000000</Mac><!--MAC值--><MsgId>0bf6af2d-be27-4803-9f1b-4bc3b8bbe212</MsgId><!--服务消息ID--><SourceSysId>70209</SourceSysId><!--源发起渠道编号--><ConsumerId>201190</ConsumerId><!--服务调用方系统ID--><ServiceCode>15005000004</ServiceCode><!--服务码--><ServiceScene>10</ServiceScene><!--服务场景--><ReplyAdr/><!--服务响应地址--><ExtendContent/><!--扩展内容--></SYS_HEAD><APP_HEAD><TranDate>20230201</TranDate><!--交易日期--><TranTime>190620</TranTime><!--交易时间--><TranTellerNo>V00066</TranTellerNo><!--交易柜员--><TranSeqNo>2011900A19808E00167e06bd3bc00000</TranSeqNo><!--交易流水号--><GlobalSeqNo>2011900A19808E00167e06bd3bc00000</GlobalSeqNo><!--全局流水号--><BranchId>08101</BranchId><!--机构代码--><TerminalCode/><!--终端号--><CityCode/><!--城市代码--><AuthrTellerNo/><!--授权柜员--><AuthrPwd/><!--授权密码--><AuthrCardFlag/><!--授权柜员有无卡标志--><AuthrCardNo/><!--授权柜员卡序号--><LangCode>CHINESE</LangCode><!--用户语言--></APP_HEAD><BODY><PdFlg/><DataTp>88</DataTp><!--数据类型--><SendDt>20221215</SendDt><!--发送日期--><FileNm/><!--文件名--><Rmk1>1</Rmk1><!--dealStatus--><Rmk2>处理成功</Rmk2><!--dealMessage--><Rmk3>d09c933cc9414579b5d308d2997c7fd0</Rmk3><!--资管GlobalSeqNo--></BODY></service>";
        String pkgLength=(SocketUtil.getMsgHead(result,"UTF-8"))+"";
        int length = "8"==null?8:Integer.parseInt("8");
        pkgLength = SocketUtil.buzu(pkgLength,"0",length,0);
        System.out.println(pkgLength);
        List<String> list = new ArrayList<>();
        List<String> collect = list.stream().filter(item ->
                StringUtil.equals("1", "1") || StringUtil.equals("2", "2"))
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
