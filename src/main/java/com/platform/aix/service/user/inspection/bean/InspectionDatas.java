package com.platform.aix.service.user.inspection.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: platform-aimb
 * @description:公卫用户批量上传检测数据请求体
 * @author: fuyl
 * @create: 2019-09-29 16:07
 **/
@Data
public class InspectionDatas {
    private String hospcode ;  // 医院编码
    private String ssid ;  // 受检人ID
    private String inspectiontime       	;
    private String registcode ;  // 登记编号
    private Integer inspectionflag ;  // 0=未检 1=已检
    private String inspectionitemcode ;  // 参考t_dict_inspection_item
    private String inspectionitemname ;  // 检查项目名称
    private String inspectionsubitemcode ;  // 参考t_dict_inspection_item
    private String inspectionsubitemname ;  // 检查子项目名称
    private String inspectionindexcode ;  // 参考 t_dict_inspection_index
    private String inspectionindexname ;  // 检查指标名称
    private String inspectionid ;  // 检查ID
    @NotNull(message = "检测值1不能为空")
    private String inspectionindexvalue1 ;
    private String inspectionindexvalue2 ;
    private String inspectionindexvalue3 ;
    private Integer inspectionindextype;
    private String inspectionhospcode   	;
    private String inspectionhospname   	;
    private String inspectionusercode   	;
    private String inspectionusername   	;
    private String verfusercode         	;
    private String verfusername         	;
    private Integer datasourcetype         ;
    private String datasourcedevice       ;
    private String id;
    private String aidxresultcode ;  // AI诊断结果编码
    private String aidxresultname ;  // AI诊断结果名称
    private String aidxresultmemo ;  // AI诊断结果描述
    private List<DiagnosticResult> aidxresult ;  // 嵌套AI诊断结果数组
    private String doctordxresultcode ;  // 医生诊断结果编码
    private String doctordxresultname ;  // 医生诊断结果名称
    private String doctordxresultmemo ;  // 医生诊断结果描述
    private List<DiagnosticResult> doctordxresult ;  // 嵌套医生诊断结果数组
    private List<Inspectionfile> inspectionfile ;  // 嵌套检查文件数组

    public String getHospcode() {
        return StringUtils.isEmpty(hospcode)?"":hospcode;
    }

    public String getSsid() {
        return StringUtils.isEmpty(ssid)?"":ssid ;
    }

    public String getInspectiontime() {
        return StringUtils.isEmpty(inspectiontime)?"":inspectiontime ;
    }

    public String getRegistcode() {
        return StringUtils.isEmpty(registcode)?"":registcode ;
    }

    public Integer getInspectionflag() {
        return 1;
    }

    public String getInspectionitemcode() {
        return StringUtils.isEmpty(inspectionitemcode)?"":inspectionitemcode ;
    }

    public String getInspectionitemname() {
        return StringUtils.isEmpty(inspectionitemname)?"":inspectionitemname ;
    }

    public String getInspectionsubitemcode() {
        return StringUtils.isEmpty(inspectionsubitemcode)?"":inspectionsubitemcode ;
    }

    public String getInspectionsubitemname() {
        return StringUtils.isEmpty(inspectionsubitemname)?"":inspectionsubitemname ;
    }

    public String getInspectionindexcode() {
        return StringUtils.isEmpty(inspectionindexcode)?"":inspectionindexcode ;
    }

    public String getInspectionindexname() {
        return StringUtils.isEmpty(inspectionindexname)?"":inspectionindexname ;
    }

    public String getInspectionid() {
        return StringUtils.isEmpty(inspectionid)?"":inspectionid ;
    }

    public String getInspectionindexvalue1() {
        return StringUtils.isEmpty(inspectionindexvalue1)?"":inspectionindexvalue1 ;
    }

    public String getInspectionindexvalue2() {
        return StringUtils.isEmpty(inspectionindexvalue2)?"":inspectionindexvalue2 ;
    }

    public String getInspectionindexvalue3() {
        return StringUtils.isEmpty(inspectionindexvalue3)?"":inspectionindexvalue3 ;
    }

    public Integer getInspectionindextype() {
        return inspectionindextype == null?1:2;
    }

    public String getInspectionhospcode() {
        return StringUtils.isEmpty(inspectionhospcode)?"":inspectionhospcode ;
    }

    public String getInspectionhospname() {
        return StringUtils.isEmpty(inspectionhospname)?"":inspectionhospname ;
    }

    public String getInspectionusercode() {
        return StringUtils.isEmpty(inspectionusercode)?"":inspectionusercode ;
    }

    public String getInspectionusername() {
        return StringUtils.isEmpty(inspectionusername)?"":inspectionusername ;
    }

    public String getVerfusercode() {
        return StringUtils.isEmpty(verfusercode)?"":verfusercode ;
    }

    public String getVerfusername() {
        return StringUtils.isEmpty(verfusername)?"":verfusername ;
    }

    public Integer getDatasourcetype() {
        return datasourcetype == null ? 0: 1;
    }

    public String getDatasourcedevice() {
        return StringUtils.isEmpty(datasourcedevice)?"":datasourcedevice ;
    }

    public String getId() {
        return StringUtils.isEmpty(id)?"":id ;
    }

    public String getAidxresultcode() {
        return StringUtils.isEmpty(aidxresultcode)?"":aidxresultcode ;
    }

    public String getAidxresultname() {
        return StringUtils.isEmpty(aidxresultname)?"":aidxresultname ;
    }

    public String getAidxresultmemo() {
        return StringUtils.isEmpty(aidxresultmemo)?"":aidxresultmemo ;
    }

    public List<DiagnosticResult> getAidxresult() {
        return aidxresult;
    }

    public String getDoctordxresultcode() {
        return StringUtils.isEmpty(doctordxresultcode)?"":doctordxresultcode ;
    }

    public String getDoctordxresultname() {
        return StringUtils.isEmpty(doctordxresultname)?"":doctordxresultname ;
    }

    public String getDoctordxresultmemo() {
        return StringUtils.isEmpty(doctordxresultmemo)?"":doctordxresultmemo ;
    }

    public List<DiagnosticResult> getDoctordxresult() {
        return doctordxresult;
    }

    public List<Inspectionfile> getInspectionfile() {
        return inspectionfile;
    }
}
