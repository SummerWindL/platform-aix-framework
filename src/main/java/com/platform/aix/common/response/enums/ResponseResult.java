package com.platform.aix.common.response.enums;


public enum ResponseResult {

    // +++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++基础级别 错误+++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++
    COMMON_ERROR(-100, "基础错误"),
    COMMON_ERROR_EXCEPTION(-101, "异常错误"),
    COMMON_ERROR_UNKNOWN(-102, "未知错误"),
    COMMON_ERROR_INVALID_PARAM(-103, "无效参数"),
    COMMON_ERROR_NULL_POINTER(-104, "空指针"),
    COMMON_ERROR_ALLOC_FAILED(-105, "分配内存错误"),
    COMMON_ERROR_CONVERT_PARAM(-106, "参数类型转换错误"),
    COMMON_ERROR_PARAM_NOT_NULL(-107, "参数不能为空"),

    // ++++++++++++++++++数据库 db+++++++++++++++++++++++
    DB_ERROR(-1000, "数据库错误"),
    DB_ERROR_CONNECT(-1001, "数据库连接错误"),
    DB_ERROR_INSERT(-1002, "数据库插入错误"),
    DB_ERROR_UPDATE(-1003, "数据库更新错误"),
    DB_ERROR_DELETE(-1004, "数据库删除错误"),
    DB_ERROR_QUERY(-1005, "数据库查询错误"),
    DB_ERROR_RECORD_EXIST(-1006, "记录已经存在"),
    DB_ERROR_RECORD_NOTEXIST(-1007, "记录不存在"),
    DB_ERROR_FIELD_IS_NULL(-1008, "数据库存储过程入参为空"),



    // ++++++++++++++++++通讯 错误+++++++++++++++++++++++
    COMM_ERROR(-2000, "通讯错误"),
    COMM_ERROR_LISTEN(-2001, "Listen错误"),
    COMM_ERROR_ACCEPT(-2002, "Accept错误"),
    COMM_ERROR_CONNECT(-2003, "Connect错误"),
    COMM_ERROR_RECV(-2004, "RECV错误"),
    COMM_ERROR_SEND(-2005, "SEND错误"),
    COMM_ERROR_PORT_USED(-2006, "端口占用错误"),

    // ++++++++++++++++++IO 错误+++++++++++++++++++++++
    IO_ERROR(-3000, "IO错误"),
    IO_ERROR_FILE_NOT_EXIST(-3001, "文件不存在"),
    IO_ERROR_CREATE(-3002, "创建失败"),
    IO_ERROR_OPEN(-3003, "打开失败"),
    IO_ERROR_WRITE(-3004, "写失败"),
    IO_ERROR_READ(-3005, "读失败"),
    IO_ERROR_CLOSE(-3005, "关闭失败"),

    // ++++++++++++++++++HTTP 通讯协议错误+++++++++++++++++++++++
    HTTP_ERROR(-4000, "HTTP错误"),
    HTTP_ERROR_INVALID_REQUEST(-4001, "请求命令无效"),
    HTTP_ERROR_INVALID_PARAM(-4002, "参数不正确"),
    HTTP_ERROR_INVALID_SIGN(-4003, "签名无效"),
    HTTP_ERROR_POST_FAILURE(-4004, "POST请求失败"),
    HTTP_ERROR_PARSE_JSON(-4005, "JSON格式解析失败"),
    HTTP_ERROR_REQUEST_INTERCEPTOR(-4006, "请求被拦截"),
    HTTP_ERROR_UNSURPORT_PROTO_VER(-4007, "不支持的协议版本"),
    HTTP_ERROR_URL_ENCODE_ERROR(-4008, "URL编码错误"),
    HTTP_ERROR_WRITE_JSON(-4009, "生成JSON失败"),
    HTTP_ERROR_INVALID_AUTH(-4003, "非法的权限"),

    // ++++++++++++++++++HTTP 通讯协议上传文件错误+++++++++++++++++++++++
    HTTP_ERROR_UPLOAD_FILE_EMPTY(-5000, "上传文件为空"),
    HTTP_ERROR_UPLOAD_FILE_FAILURE(-5001, "上传文件失败"),

    // ++++++++++++++++++Excel文件操作错误+++++++++++++++++++++++
    EXCEL_ERROR_CREATE(-6000, "创建失败"),
    EXCEL_ERROR_OPEN(-6001, "打开失败"),
    EXCEL_ERROR_WRITE(-6002, "写失败"),
    EXCEL_ERROR_READ(-6003, "读失败"),
    EXCEL_ERROR_CLOSE(-6004, "关闭失败"),
    EXCEL_ERROR_COLUMN_HEADER_ISEMPTY(-6005, "表头字段为空"),

    // +++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++业务级别 错误+++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++

    // ++++++++++++++++++错误+++++++++++++++++++++++
    BIZ_ERROR_INVALID_PARAM(-10001, "参数不正确"),
    BIZ_ERROR_TYPE_NOT_SUPPORT(-10002, "不支持的分类类型"),
    BIZ_ERROR_CHECKCODE(-10003, "验证码错误"),
    BIZ_ERROR_SEND_CHECKCODE(-10004, "发送验证码错误"),
    BIZ_ERROR_TOKEN_INVALID(-10005, "token无效"),
    BIZ_ERROR_DBJSON2CLZ(-10006, "数据库结果JSON转换实体类错误"),
    BIZ_ERROR_IMCFG_ERROR(-10007, "开通医生IM账号错误：IM参数配置不可为空！"),
    BIZ_ERROR_THIRD_CLOUD_PLAT_CONFIG_NOT_EXIST(-10008, "第三方平台参数未配置！"),
    BIZ_ERROR_THIRD_CLOUD_PLAT_IS_EMPTY(-10009, "参数为空！"),

    // --------------------------------------------------
    // ++++++++++++++++++用户错误++++++++++++++++++++++++
    // --------------------------------------------------

    // ++++++++++++++++++注册错误+++++++++++++++++++++++
    BIZ_ERROR_REGISTER(-11000, "注册错误"),
    BIZ_ERROR_MANUID_PWD_WRONG(-11001, "厂商ID或密码错误"),
    BIZ_ERROR_MANUID_NOT_EXIST(-11002, "厂商信息不存在"),
    BIZ_ERROR_USERID_ISEMPTY(-11003, "userid为空"),


    // ++++++++++++++++++帐号错误+++++++++++++++++++++++
    BIZ_ERROR_USER(-12000, "帐号错误"),
    BIZ_ERROR_USER_EXIST(-12001, "帐号已存在"),
    BIZ_ERROR_USER_NOT_EXIST(-12002, "帐号不存在"),
    BIZ_ERROR_USER_PWD_WRONG(-12003, "帐号或密码错误"),
    BIZ_ERROR_RESET_PWD_WRONG(-12004, "重置密码错误"),
    BIZ_ERROR_RSA_DECODE(-12005, "RSA解码错误"),
    BIZ_ERROR_RSA_ENCODE(-12006, "RSA编码错误"),
    BIZ_ERROR_MD5_ENCODE(-12007, "MD5编码错误"),
    BIZ_ERROR_OLD_PWD_WRONG(-12008, "旧密码错误"),
    BIZ_ERROR_USER_NAME_EMPTY(-12100, "用户名称不能为空"),
    BIZ_ERROR_USER_REGION_WRONG(-12101, "请选择帐号所属区域。"),


    // ++++++++++++++++++设备绑定错误+++++++++++++++++++++++
    BIZ_ERROR_DEVICE_IS_BOUND(-13000, "设备已被绑定"),
    BIZ_ERROR_USER_BIND_DEVICE_NOT_EXISTS(-13001, "设备绑定关系不存在"),
    BIZ_ERROR_THIS_TYPE_DEVICE_IS_BOUND_BY_USER(-13002, "用户已绑定该类型的设备，不允许编辑"),
    BIZ_ERROR_THIS_TYPE_DEVICE_IS_BOUND_BY_USER_REGIST(-13003, "用户已绑定该设备,请先解绑再删除"),
    BIZ_ERROR_THIS_IS_NOT_USE(-13004, "设备无法正常运行"),

    // ++++++++++++++++++第三方登录错误+++++++++++++++++++++++
    BIZ_ERROR_OAUTH_VALID_FAILD(-14000, "第三方验证不通过"),
    BIZ_ERROR_USER_OAUTH_ACCOUNT_NOT_EXSIT(-14001, "第三方用户绑定关系不存在"),
    BIZ_ERROR_OAUTH_ACCESS_TOKEN_EXPIRED(-14002, "acces token 超时"),

    // --------------------------------------------------
    // ++++++++++++++++++患者档案错误+++++++++++++++++++++++
    // --------------------------------------------------
    BIZ_ERROR_SS(-20000, "居民错误"),
    BIZ_ERROR_SS_EXIST(-20001, "居民已存在"),
    BIZ_ERROR_SS_NOT_EXIST(-20003, "居民档案信息不存在"),
    BIZ_ERROR_SS_NO_NEWEST_INSPECTION(-20004,"居民无最新检测记录"),
    BIZ_ERROR_SS_IDCARDNO_EXIST(-20005,"该身份证已建档"),

    //++++++++++++++++++心电数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_ECG_DATA(-20100, "心电数据错误"),
    BIZ_ERROR_ECG_DATA_EXIST(-20101, "心电数据已存在"),
    BIZ_ERROR_ECG_DATA_NOT_EXIST(-20102, "心电数据不存在"),
    BIZ_ERROR_ECG_DATA_CHECK_NOT_EXIST(-20103, "心电复核数据不存在"),
    BIZ_ERROR_ECG_DATA_PROCESSING_NO_MORE(-20104, "没有待处理的心电数据"),
    BIZ_ERROR_ECG_DATA_ID_NOT_NULL(-20105, "心电数据记录ID不能为空"),
    BIZ_ERROR_ECG_DATA_ID_LIST_NOT_NULL(-20106, "心电数据记录ID列表不能为空"),

    //++++++++++++++++++疾病史数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_DISES_DATA(-20200, "疾病史数据错误"),
    BIZ_ERROR_DISES_DATA_EXIST(-20201, "疾病史数据已存在"),
    BIZ_ERROR_DISES_DATA_NOT_EXIST(-20202, "疾病史数据不存在"),

    //++++++++++++++++++胆固醇数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_CHOLESTERIN_DATA(-20300, "胆固醇数据错误"),
    BIZ_ERROR_CHOLESTERIN_DATA_EXIST(-20301, "胆固醇数据已存在"),
    BIZ_ERROR_CHOLESTERIN_DATA_NOT_EXIST(-20302, "胆固醇数据不存在"),

    //++++++++++++++++++运动数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_SPORT_DATA(-20400, "运动数据错误"),
    BIZ_ERROR_SPORT_DATA_EXIST(-20401, "运动数据已存在"),
    BIZ_ERROR_SPORT_DATA_NOT_EXIST(-20402, "运动数据不存在"),

    //++++++++++++++++++体重数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_WEIGHT_DATA(-20500, "体重数据错误"),
    BIZ_ERROR_WEIGHT_DATA_EXIST(-20501, "体重数据已存在"),
    BIZ_ERROR_WEIGHT_DATA_NOT_EXIST(-20502, "体重数据不存在"),

    //++++++++++++++++++习惯数据记录错误+++++++++++++++++++++++
    BIZ_ERROR_HABIT_DATA(-20600, "生活习惯数据错误"),
    BIZ_ERROR_HABIT_DATA_EXIST(-20601, "生活习惯数据已存在"),
    BIZ_ERROR_HABIT_DATA_NOT_EXIST(-20602, "生活习惯数据不存在"),

    //++++++++++++++++++预警规则错误+++++++++++++++++++++++
    BIZ_ERROR_ALERT_RULE_NOT_EXIST(-20700, "预警规则不存在"),

    //++++++++++++++++++确诊信息错误+++++++++++++++++++++++
    BIZ_ERROR_DX_DISE_EXIST(-22000, "已确诊该疾病，请勿重复确诊"),

    //++++++++++++++++++干预信息错误+++++++++++++++++++++++
    BIZ_ERROR_GY_PLAN_EXIST(-23000, "同一时间段内不能有多个干预计划"),

    // --------------------------------------------------
    // ++++++++++++++++++医生错误++++++++++++++++++++++++
    // --------------------------------------------------
    BIZ_ERROR_DOCTOR(-30000, "医生错误"),
    BIZ_ERROR_DOCTOR_INFO_EXIST(-30001, "医生信息已存在"),
    BIZ_ERROR_DOCTOR_INFO_NOT_EXIST(-30002, "医生信息不存在"),
    BIZ_ERROR_HOSPITAL_CENTER_DOCTOR_INFO_NOT_EXIST(-30003, "中心医院医生信息不存在"),
    BIZ_ERROR_DOCTOR_ACC_DISABLED(-30004, "医生帐号禁用"),


    //++++++++++++++++++签约错误+++++++++++++++++++++++
    BIZ_ERROR_DOCTOR_PATIENT_SIGNED_EXIST(-50001, "医患签约关系已存在"),
    BIZ_ERROR_DOCTOR_PATIENT_SIGNED_NOT_EXIST(-50002, "医患签约关系不存在"),
    BIZ_ERROR_DOCTOR_PATIENT_SIGNED_WRONG_STATUS(-50003, "医患签约状态有误"),
    BIZ_ERROR_DOCTOR_PATIENT_ISNOT_SIGNED(-50004, "还没确立医患签约关系"),

    BIZ_ERROR_SIGN_RECORD_IS_EXIST(-50005,"此患者签约记录已经存在，请勿重复签约"),
    BIZ_ERROR_RELEASE_RECORD_IS_EXIST(-50005,"此患者解约记录已经存在，请勿重复解约"),
    BIZ_ERROR_RENEW_RECORD_IS_EXIST(-50005,"此患者续约记录已经存在，请勿重复续约"),

    //++++++++++++++++++心电数据错误+++++++++++++++++++++++
    BIZ_ERROR_ECGDATA_NOT_EXIST(-50200, "心电数据不存在"),
    BIZ_ERROR_ECGDATA_EXIST(-50201, "心电数据已存在"),
    BIZ_ERROR_GET_EGG_DATA_POINTS_ERROR(-50202, "心电采样点获取失败"),

    //++++++++++++++++++筛查错误++++++++++++++++++++++++
    BIZ_ERROR_APP_ACCOUNT_NOT_EXIST(-50300, "手机APP账号不存在"),
    BIZ_ERROR_SC_APP_ACCOUNT_EXIST(-50301, "筛查APP账号已存在"),

    //++++++++++++++++++会话错误++++++++++++++++++++++++
    BIZ_ERROR_DOCTOR_DIAGNOSIS_MSG_EXIST(-50400, "重复回复同一条消息"),


    //++++++++++++++++++数据错误+++++++++++++++++++++++
    BIZ_ERROR_NO_DATA_AUTHORITY(-51000, "没有数据权限"),
    BIZ_ERROR_ADMIN_NOT_DELETE(-510002,"不允许删除"),
    BIZ_ERROR_DATA_ALREADY_SYNC(-510003,"数据已经同步，不允许再次同步"),
    BIZ_ERROR_UPDATE_DATA_SYNC(-510004,"更新数据失败"),
    BIZ_ERROR_LINK_OTHER_DATA(-510005,"链接到其他数据"),


    //++++++++++++++++++医疗机构错误+++++++++++++++++++++++
    BIZ_ERROR_DX_HA_HOSPCODE_ALREADY_EXIST(-51101, "医疗机构已添加过，不能重复添加"),
    BIZ_ERROR_DX_HA_HOSPCODE_NO_EXIST(-51102, "医疗机构不存在"),
    BIZ_ERROR_DX_HA_DELETE_RELEVANCE_INFO(-51103, "请先删除该医疗机构的关联信息"),
    BIZ_ERROR_DX_HA_HOSPCODE_QUERAY_PARAM_ERROR(51104, "查询参数错误"),
    BIZ_ERROR_DX_HA_HOSPCODE_CODE_NOT_NULL(51105, "医疗机构编码不能为空"),
    BIZ_ERROR_DX_HA_HOSPCODE_CODES_NOT_NULL(51106, "请选择医疗机构"),
    BIZ_ERROR_DX_HA_HOSPCODE_ADMIN_NOT_NULL(51107, "请先添加管理员帐号"),

    //++++++++++++++++++配置错误+++++++++++++++++++++++
    BIZ_ERROR_DX_HA_DEVICE_ALREADY_EXIST(-51201, "设备已添加过，不能重复添加"),
    BIZ_ERROR_DX_HA_DEVICE_NO_EXIST(-51202, "设备不存在"),
    BIZ_ERROR_DX_HA_GATEWAY_IS_NO_NULL(-51203, "医疗机构编码或网关编码不能为空"),
    BIZ_ERROR_DX_HA_GATEWAY_EXIST(-51204, "网关已添加过，不能重复添加"),
    BIZ_ERROR_DX_HA_GATEWAY_NO_EXIST(-51205, "网关不存在"),
    BIZ_ERROR_DX_HA_INSPECTION_IS_NO_NULL(-51206, "医疗机构编码或检查项目或检查单类型不能为空"),
    BIZ_ERROR_DX_HA_INSPECTION_NO_EXIST(-51207, "检查单类型不存在"),
    BIZ_ERROR_DX_HA_INSPECTION_EXIST(-51208, "检查单类型已添加过，不能重复添加"),
    BIZ_ERROR_DX_HA_DEVICE_PARAM_NO_EXIST(-51209, "设备不存在"),
    BIZ_ERROR_DX_HA_DEPARTMENT_NAME_EXIST(-51210, "科室已添加过，不能重复"),


    //++++++++++++++++++帐号配置错误+++++++++++++++++++++++
    BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_IS_NO_NULL(-51401, "用户ID或用户名不能为空！"),
    BIZ_ERROR_DX_USER_ACCOUNT_AND_HA_PK(-51402, "请在配置医疗机构中解除该用户与医院的权限关联，再进行删除！"),
    BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_PHONE_EXIST(-51403, "手机号码已注册过，请填写其他手机号码！"),
    BIZ_ERROR_DX_USER_ACCOUNT_USERID_OR_ACC_EXIST(-51404, "用户账号已存在，不能重复添加！"),
    BIZ_ERROR_MB_USER_DOCTOR_APP_EXIST(-51405, "该用户已开通医生APP！"),
    BIZ_ERROR_MB_USER_DOCTOR_APP_IS_REGISTERED(-51406, "该医生APP账号已被注册！"),
    BIZ_ERROR_MB_USER_DOCTOR_APP_NOT_EXIST(-51407, "该用户未开通医生APP！"),

    //++++++++++++++++++角色配置错误+++++++++++++++++++++++
    BIZ_ERROR_DX_ROLE_GROUP_ID_IS_NO_NULL(-51501, "角色ID不能为空！"),
    BIZ_ERROR_DX_ROLE_GROUP_EXIST(-51502, "角色组已经添加，不能重复添加！"),

    //++++++++++++++++++项目错误+++++++++++++++++++++++
    BIZ_ERROR_DX_PRJ_NAME_IS_REPEAT(-51401, "项目名称重复"),
    BIZ_ERROR_DX_PRJ_TITLE_NAME_IS_REPEAT(-51402, "项目标题名称重复"),
    BIZ_ERROR_DX_PRJ_DELETE_RELEVANCE_INFO(-51403, "请先删除关联的项目标题"),

    //++++++++++++++++++功能菜单错误+++++++++++++++++++++++
    BIZ_ERROR_DX_DICT_FUNCTION_PARAM_PAGE_IS_NOT_NULL(-51501, "参数不能为空"),

    //++++++++++++++++++生成报告错误+++++++++++++++++++++++
    BIZ_ERROR_DX_REPORT_UNSURPORT_FILE_FORMAT(-51501, "不支持的文件格式"),
    BIZ_ERROR_DX_REPORT_WKHTML2FILE_FAILURE(-51502, "HTML转换目标格式文件失败"),

    //++++++++++++++++++用户权限配置错误+++++++++++++++++++++++
    BIZ_ERROR_DX_USER_AUTH_HOSP_DEPT_CANNOT_SAVE(-51601, "用户院科权限配置失败"),

    //++++++++++++++++++字典错误+++++++++++++++++++++++
    BIZ_ERROR_DICT_DEV_MANU_EXIST(-51701, "设备厂商已存在"),

    //问卷调查
    BIZ_ERROR_ASK_SS_IDCARDNO_EXIST(-60001,"存在同一身份证信息记录"),
    BIZ_ERROR_ASK_SS_GENDER_ERROR(-60002,"第1页 1.1 请选择性别"),
    BIZ_ERROR_ASK_SS_BIRTH_ERROR(-60004,"第1页 1.2 请填写出生日期"),
    BIZ_ERROR_ASK_SS_BIRTH_FORMAT(-60003,"第1页 1.2 出生日期格式错误"),

    //++++++++++++++++++公卫账号错误+++++++++++++++++++++++
    BIZ_ERROR_GW_ACCOUNT_PWD_WRONG(					-52001, "公卫帐号或密码错误，请重试！"),
    BIZ_ERROR_GW_ACCOUNT_PRODUCT_REGIST_WRONG(					-52002, "公卫帐号产品注册失败，请联系管理员！"),
    BIZ_ERROR_GW_API_WRONG(					-52003, "公卫网关通讯异常，请重试！"),
    BIZ_ERROR_GW_REGION_API_WRONG(					-52004, "公卫区划API配置错误"),
    BIZ_ERROR_GW_ACCOUNT_NOEXIST(					-52005, "划区不存在该公卫帐号"),
    BIZ_ERROR_GW_INIT_DOCTOR_LIST(					-52006, "初始化公卫机构医务人员列表失败"),

    //++++++++++++++++++公卫业务级别错误+++++++++++++++++++
    BIZ_ERROR_MATERNALSS_UPDATE_CLOSEDFLAG(-80001, "解案失败，一个孕妇只能有一个未结案名册"),
    BIZ_ERROR_MATERNALSS_INSERT(-80002, "建档失败，一个孕妇只能有一个未结案名册"),

    BIZ_ERROR_CHILDSS_UPDATE_CLOSEDFLAG(-80003, "解案失败，一个儿童只能有一个未结案名册"),
    BIZ_ERROR_CHILDSS_INSERT(-80004, "建档失败，一个儿童只能有一个未结案名册"),

    BIZ_ERROR_HBPSS_UPDATE_CLOSEDFLAG(-80005, "解案失败，一个高血压患者只能有一个未结案名册"),
    BIZ_ERROR_HBPSS_INSERT(-80006, "建档失败，一个高血压患者只能有一个未结案名册"),

    BIZ_ERROR_DIABETESS_UPDATE_CLOSEDFLAG(-80007, "解案失败，一个糖尿病患者只能有一个未结案名册"),
    BIZ_ERROR_DIABETESS_INSERT(-80008, "建档失败，一个糖尿病患者只能有一个未结案名册"),

    BIZ_ERROR_MENTALSS_UPDATE_CLOSEDFLAG(-80009, "解案失败，一个精神病患者只能有一个未结案名册"),
    BIZ_ERROR_MENTALSS_INSERT(-80010, "建档失败，一个精神病患者只能有一个未结案名册"),

    BIZ_ERROR_LUNGERSS_UPDATE_CLOSEDFLAG(-80011, "解案失败，一个肺结核患者只能有一个未结案名册"),
    BIZ_ERROR_LUNGERSS_INSERT(-80012, "建档失败，一个肺结核患者只能有一个未结案名册"),

    BIZ_ERROR_FAMILY_SSARCHIVE_INSERT(-80013, "建档失败，已经存在家庭户主"),

    BIZ_ERROR_FAMILY_SSARCHIVE_UPDATE(-80014, "更新失败，已经存在家庭户主"),

    // +++++++++++++++++++++++++++++++++++++ 检测指标数据级错误 (暂时错误代码定义为接口代码+自定义自增编号) +++++++++++++++++++++++

    BIZ_ERROR_INSPECTIONDATA_INSPECTIONINDEXVALUE1_ISEMPTY(-8031601,"存在定量指标项入参inspectionindexvalue1为空，不允许插入数据库！"),

    //++++++++++++++++++问卷调研++++++++++++++++++++++++
    BIZ_ERROR_ASK_SS_QADONEFLAG_0(-90042,"问卷调研未完成，不允许提交审核！"),

    //++++++++++++++++++互联网业务错误++++++++++++++++++++++++
    BIZ_ERROR_NETHOSP_DOCTORIMACC_EXISTS(-7001,"医生IM账号已存在！"),
    BIZ_ERROR_NETHOSP_APPHOSPCODE_ERR(-7001,"APPHOSPCODE非法！"),
    BIZ_ERROR_NETHOSP_IMPARAMS_ISRMPTY(-7001,"医院IMPARAMS配置不可为空！"),

    // ++++++++++++++++++成功+++++++++++++++++++++++
    ZX_SUCCESS(0, "成功"),
    COMMON_SUCCESS(0, "成功");


    private int resultcode;

    private String resultmsg;

    private ResponseResult(int resultcode, String resultmsg) {
        this.resultcode = resultcode;
        this.resultmsg = resultmsg;
    }

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }
}
