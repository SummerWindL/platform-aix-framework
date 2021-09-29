package com.platform.aix.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.platform.aix.cmd.bean.request.BaseRequest;
import com.platform.aix.common.annotation.DisableAccess;
import com.platform.aix.common.annotation.DisableCheckAuthHeader;
import com.platform.aix.common.annotation.FileUploadFunction;
import com.platform.aix.common.constants.Constants;
import com.platform.aix.common.handler.base.IBaseHandler;
import com.platform.aix.common.interceptor.AccessLimitInterceptors;
import com.platform.aix.common.request.CheckSign;
import com.platform.aix.common.request.RequestAuthHeaderCheckor;
import com.platform.aix.common.response.APIResponse;
import com.platform.aix.common.response.ZxApiResponse;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.response.enums.ResponseResult;
import com.platform.aix.common.spring.AppService;
import com.platform.aix.common.util.JsonAdaptor;
import com.platform.aix.config.ApisPorperties;
import org.eclipse.jetty.continuation.Continuation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HandlerRequestRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HandlerRequestRunnable.class);

    private final HttpServletRequest servlet;

    private final Continuation continuation;

    private final RequestAuthHeaderCheckor authCheckor = AppService.getBean("requestAuthHeaderCheckor");
    private JsonAdaptor mapper = AppService.getBean("jsonAdaptor");
    private AccessLimitInterceptors accessInterceptor = AppService.getBean("accessLimitInterceptors");
    private ApisPorperties apisPorperties = AppService.getBean("apisPorperties");

    public HandlerRequestRunnable(Continuation continuation, HttpServletRequest servlet) {
        this.servlet = servlet;
        this.continuation = continuation;
    }

    @Override
    public void run() {
        if (continuation.isSuspended()) {
            long start = System.currentTimeMillis();

            // 默认返回未知错误
            APIResponse response = new APIResponse(ResponseResult.COMMON_ERROR_UNKNOWN);
            ZxHttpRequest zxHttpRequest = null;
            // 获取基础参数
            String contentType = servlet.getContentType();
            String uri = servlet.getRequestURI();
            String factoryid = null;
            String factorysecretkey = null;
            String auth = null;
            String params = null;
            String resp = null;
            String sign = null;
            String ts = null;

            try {

                // 1.验证请求协议
                if (!uri.startsWith(Constants.COMMON_REQ_URL)) {
                    response = new APIResponse(ResponseResult.HTTP_ERROR_UNSURPORT_PROTO_VER);
                    return;
                }
                // 2.解析参数ts、 sign、auth、params
                // 判断是否文件上传接口 upload_file，文件上传ts、 sign、auth、params填充在HTTP Header中
                // if (uri.equals(AppService.getBeanName(UploadFileHandler.class)))
                if (uri.contains("cmd_11011")) {
                    // Continuation timeout默认30,setTimeout代码实现有锁，会造成代码执行阻塞
                    // continuation.setTimeout(600000);
                    sign = servlet.getHeader("sign");
                    ts = servlet.getHeader("ts");
                    auth = servlet.getHeader("auth");
                    params = servlet.getHeader("params");
                } else {
                    // Continuation timeout默认30
                    //continuation.setTimeout(100000);
                    sign = servlet.getParameter("sign");
                    ts = servlet.getParameter("ts");
                    auth = servlet.getParameter("auth");
                    params = servlet.getParameter("params");
                }


                // 3.根据http请求URI获取处理方法
                CommandHandler handler = AppService.getBean(uri);

                if (handler == null) {
                    response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_REQUEST);
                    return;
                }
                /**
                 * 限流处理
                 * eg. 某个接口限制 10秒访问一次
                 * 思路，应该对所有接口都限流，不限流的应该做一个DisAccess注解 标记为不限流
                 */
                //4.限流判断
                if(AopUtils.getTargetClass(handler).getAnnotation(DisableAccess.class) == null){
                    if(!accessInterceptor.accessLimitInterceptor(servlet,handler)){
                        response = new APIResponse(ResponseResult.HTTP_ACCESS_FREQUENCY);
                        return;
                    }
                }

                // 5.解析参数ts、 sign、auth、params
                //判断请求参数是否需要从头部获取
                /*if(AopUtils.getTargetClass(handler).getAnnotation(FileUploadFunction.class) != null ){
                    // sign = servlet.getHeader("sign");
                    // ts = servlet.getHeader("ts");
                    auth = servlet.getHeader("auth");
                    params = servlet.getHeader("params");
                }else{
                    // Continuation timeout默认30
                    //continuation.setTimeout(100000);

                    //通用获取 http请求参数信息
                    if(Constants.HTTP_CONTENT_TYPE_FORM.equals(contentType)){
                        //获取form的处理
                        //1.入参都是servlet
                        //2.出来的参数都是 factoryid factorysecretkey params
                        zxHttpRequest = HttpRequestConverter.formConverter2Obj(servlet);
                    }else if(Constants.HTTP_CONTENT_TYPE_JSON.equals(contentType)){
                        //获取JSON处理
                        zxHttpRequest = HttpRequestConverter.jsonConverter2Obj(servlet);
                    }else{
                        zxHttpRequest = HttpRequestConverter.formConverter2Obj(servlet);
                    }

                }*/

                //赋值
                //params = zxHttpRequest.getParams();
                //factoryid = zxHttpRequest.getFactoryid();
                //factorysecretkey = zxHttpRequest.getFactorysecretkey();
                // 4、没有DisableCheckAuthHeader注解的handler，需要验证签名和auth头
                if (AopUtils.getTargetClass(handler).getAnnotation(DisableCheckAuthHeader.class) == null) {
                    // 1.验证请求参数
                    if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(ts) || StringUtils.isEmpty(params)) {
                        response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_PARAM);
                        return;
                    }
                    // 2.校验签名信息
                    if (!CheckSign.checkSignValid(sign, ts, params)) {
                        response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_SIGN);
                        return;
                    }

                    // 3.校验auth
                    boolean isInvalid = authCheckor.checkRequestAuthHeaderValid(auth);
                    if (!isInvalid) {
                        response = new APIResponse(ResponseResult.BIZ_ERROR_TOKEN_INVALID);
                        return;
                    }

                    // 4.校验第三方调用信息
                    /*if (!CheckSign.checkFactoryAuth(zxHttpRequest.getFactoryid(), zxHttpRequest.getFactorysecretkey())) {
                        response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_AUTH);
                        return;
                    }*/

                }

                /*if(!params.contains("factoryid")){
                    //为没有factoryid的请求附上factoryid

                    JSONObject jsonObject = (JSONObject)JSONObject.parse(params);
                    jsonObject.put("factoryid", factoryid);
                    params = jsonObject.toJSONString();
                }*/
                log.info("\n获取到请求：{} ,\n客户端IP：{}，\n请求参数：{}",uri,servlet.getRemoteAddr(),params);
                // 5.把http请求params参数转换成对应的Request Bean
                BaseRequest request = mapper.readValue(params, handler.getRequestClass());
                // 请求参数params不能为空
                if (request == null) {
                    response = new APIResponse(ResponseResult.HTTP_ERROR_INVALID_PARAM);
                    return;
                }

                // 把servlet带上
                request.setServlet(servlet);

                // 5.处理具体请求
                response = handler.handle(request);

            } catch (JsonMappingException e) {
                log.error(e.getMessage(), e);
                response = new APIResponse(ResponseResult.HTTP_ERROR_PARSE_JSON);
            } catch (JsonParseException e) {
                log.error(e.getMessage(), e);
                response = new APIResponse(ResponseResult.HTTP_ERROR_PARSE_JSON);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                response = new APIResponse(ResponseResult.COMMON_ERROR_EXCEPTION);
            } finally {

                // APIResponse转成json格式字符串
                try {
                    resp = mapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    response = new APIResponse(ResponseResult.HTTP_ERROR_PARSE_JSON);
                    log.error(e.getMessage(), e);
                }

//				if (apisPorperties.isLogToMongo()) {
//					long costime = System.currentTimeMillis() - start;
//					// 处理超过10s或响应码不成功的请求记录日志
////					if (costime > 10000 ||
////							response.getResultcode() != ResponseResult.ZX_SUCCESS.getResultcode()) {
//						// 写请求日志记录
////						String executorService = String.valueOf(servlet.getAttribute("executorService"));
////						trace.append(" es:" + executorService + " ");
//						String ip = IPUtils.getIp(servlet);
//						TLogSysReqResp tLogSysReqResp = new TLogSysReqResp();
//						tLogSysReqResp.setIp(ip);
//						tLogSysReqResp.setUri(uri);
//						tLogSysReqResp.setTs(ts);
//						tLogSysReqResp.setSign(sign);
//						tLogSysReqResp.setAuth(auth);
//						tLogSysReqResp.setRequest(params);
//						// 目前mongo的document有长度限制（16M），如果响应内容过多则会导致写入错误。暂时以截取部分响应内容的方式来处理这个问题。
//						// todo:如果有更好的处理方案可以替换目前的方案，或想更精确一点可以使用bytes的长度来计算而不是String的长度。
//						tLogSysReqResp.setResponse(resp.length() < 1024 * 1024 ? resp : "响应体过长，mongo只记录部分内容，详情可查看log文件:" + resp.substring(0, 1024 * 1024));
//						tLogSysReqResp.setTrace(trace.toString());
//						tLogSysReqResp.setCostime(costime);
//						tLogSysReqResp.setCreatedtime(new Date());
//
////					}
//					try {
//						logSysService.insert(tLogSysReqResp);
//					} catch (Exception e) {
//						e.printStackTrace();
//						log.error("log to mongo error,reponse size is : {} ! ", resp.length() , e);
//					}
//				}

                if (apisPorperties.isLogResp()) {
                    String logres = resp.length() < 1024 * 1024 ? resp : resp.substring(0, 1024 * 1024);
                    log.info("uri=" + uri + " resp=" + logres + " ts=" + ts + " sign=" + sign + " auth=" + auth + " params=" + params + " costime=" + (System.currentTimeMillis() - start) + "ms");
                }
                // 响应结果返回给通过continuation Attribute返回给httphandler
                continuation.setAttribute("ret", resp);

                if (continuation.isSuspended()) {
                    continuation.resume();
                }
            }
        }
    }
}
