package com.platform.aix.common.constants;

import java.io.File;

/**
 * 公共常量
 *
 * @author Michael
 */
public class Constants {


    public static final String TRACE_ID ="";

    /**
     * HTTP contentType
     */
    public static final String HTTP_CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";

    /**
     * 国际化文件相对路径
     */
    public static final String I18N_PROPERTIES_FILE_LOCATION = "config" + File.separator + "i18n" + File.separator + "messages";

    /**
     * 版本信息
     */
    public static final String SERVER_VERSION = "v1";

    /**
     * 公共接口URL
     */
    public static final String COMMON_REQ_URL = "/aixapiplatform/api/" + SERVER_VERSION;

    public static final String MANAGE_CLIENT_REQ_URL = COMMON_REQ_URL + "/manageclient";

    /**
     * aix平台访问路径
     */
    public static final String CLIENT_REQ_URL = COMMON_REQ_URL + "/client";

    /**
     * 验证码过期时间 ，默认10分钟
     */
    public static final long USER_CHECK_CODE_EXPIRED = 10 * 60 * 1000;
    public static final String DEFAULT_LANG = "zh";

    /**
     * 是否，1=是，0=否
     */
    public static int YES = 1;
    public static int NO = 0;

    /**
     * 医生问诊消息通知
     */
    public static final String CMD_DOCTOR_DXMSG_UPDATE = "cmd_40103001";

    public static final String CMDTYPE = "update";
    /**
     * 解散房间通知
     */
    public static final String CMD_DIS_MISS_ROOM = "cmd_40105001";

    /**
     * 居民检测数据上传通知
     */
    public static final String CMD_SS_INSPECTION_DATA_UPDATE = "cmd_40106001";

    /**
     * Mq Destination
     */
    public static final String DESTINATION_DOCTOR_DXMSG_UPDATE = "doctor_dxmsg_update";
    public static final String DESTINATION_DIS_MISS_ROOM = "dis_miss_room";
    public static final String DESTINATION_SS_INSPECTION_DATA_UPDATE = "ss_inspection_data_update";


    /**
     * PostgreSQL Notify
     */
    public static final int POSTGRES_NOTIFY_SUCCESS = 1;
    public static final int POSTGRES_NOTIFY_FAILURE = 0;
    public static final String POSTGRES_DESTINATION = "mymessage";
    public static final String POSTGRES_DESTINATION_ALERT_INSPECTION = "alert_notification";
    public static final String POSTGRES_DESTINATION_SSINFO_INSPECTION = "ssinfo_notification";
    public static final String POSTGRES_DESTINATION_INSPECTION_INSPECTION = "inspection_notification";
    public static final String POSTGRES_DESTINATION_SF_INSPECTION = "sf_notification";
    public static final String POSTGRES_DESTINATION_GY_INSPECTION = "gy_notification";

}
