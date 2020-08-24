/**
 * 文件名:RequestAuthHeader.java
 * 版权:ikinloop
 * 描述:
 * 修改人:rivers
 * 修改时间:2017年9月18日
 * 修改内容:
 * 跟踪单号
 * 修改单号
 */

package com.platform.aix.common.request;

import lombok.Getter;
import lombok.Setter;

/**
 *鉴权头
 *登录成功后系统返回，后续请求都需要带上auth进行权限验证。
 *@author rivers
 *@version 2.0
 *@see [相关类/方法]
 *@since [产品/模块版本]
 */
@Setter
@Getter
public class RequestAuthHeader {

    private String id;

    private String token;

}
