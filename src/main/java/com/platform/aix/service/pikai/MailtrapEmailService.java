package com.platform.aix.service.pikai;

import cn.hutool.core.map.MapUtil;
import com.platform.aix.config.ApisPorperties;
//import io.mailtrap.client.MailtrapClient;
//import io.mailtrap.config.MailtrapConfig;
//import io.mailtrap.factory.MailtrapClientFactory;
//import io.mailtrap.model.request.emails.Address;
//import io.mailtrap.model.request.emails.MailtrapMail;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月21日 16:23
 * 这个Mailtrap 使用的jdk17编写，在1.8版本中无法使用
 */
@Slf4j
@Service
public class MailtrapEmailService {

    @Autowired
    private ApisPorperties apisPorperties;
    @Resource(name = "OkRestTemplate")
    private RestTemplate okRestTemplate;

    public boolean sendMailTrapTemplate(String email,String code) {
//        final MailtrapConfig config = new MailtrapConfig.Builder()
//                .token(apisPorperties.getMail().getTemplateToken())
//                .build();
//
//        final MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);
//
//        final MailtrapMail mail = MailtrapMail.builder()
//                .from(new Address("humans@pikai.dev", "Pikai Group"))
//                .to(List.of(new Address(email)))
//                .templateUuid(apisPorperties.getMail().getTemplateUUID())
//                .templateVariables(MapUtil.of(
//                        "code",code
//                ))
//                .build();
//
//        try {
//            log.info("send mail {}",client.send(mail));
//            return true;
//        } catch (Exception e) {
//            log.error("Caught exception : {}",e);
//            return false;
//        }
        return true;
    }

    /**
     * 发送邮件验证码
     * @param email 收件人邮箱
     * @param code 验证码
     * @return 是否发送成功
     */
    public boolean sendEmailCode(String email, String code) {
        // 创建RestTemplate，使用OkHttp客户端

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        headers.set("Authorization", "Bearer "+apisPorperties.getMail().getTemplateToken());
//        headers.set("Authorization", "Bearer 3a49845f4d61f5cbf4ffe7f358195ef7");
//        headers.setBearerAuth("3a49845f4d61f5cbf4ffe7f358195ef7");
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();

        Map<String, String> from = new HashMap<>();
        // 这里暂时只能使用demomailtrap.co演示域名
        from.put("email", "humans@demomailtrap.co");
        from.put("name", "Pikai Group");
        requestBody.put("from", from);

        Map<String, String> recipient = new HashMap<>();
        recipient.put("email", email);
        requestBody.put("to", Collections.singletonList(recipient));

        requestBody.put("template_uuid", apisPorperties.getMail().getTemplateUUID());

        Map<String, String> templateVariables = new HashMap<>();
        templateVariables.put("code", code);
        requestBody.put("template_variables", templateVariables);

        // 创建HTTP实体
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 发送请求
            ResponseEntity<String> response = okRestTemplate.exchange(
                    "https://send.api.mailtrap.io/api/send",
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            log.info("Response: {}" , response.getBody());
            // 检查请求是否成功
            log.info("邮件发送成功 to {} ，code {}",email,code);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            // 明确捕获 401 错误
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                System.err.println("认证失败，请检查 API Token");
            }
            log.error("HTTP 错误: {}" , e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            log.error("发送邮件失败: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 发送邮件到指定邮箱，使用模板变量
     * @param email 目标邮箱地址
     * @param code  动态验证码（或其他模板变量值）
     */
    public boolean sendEmailWithCode(String email, String code) {
        // 参数校验
        if (email == null || email.trim().isEmpty() || code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Email and code cannot be null or empty");
        }


        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apisPorperties.getMail().getTemplateToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构建请求体 JSON
        Map<String, Object> requestBody = new LinkedHashMap<>();

        // 发件人信息
        Map<String, String> from = new LinkedHashMap<>();
        from.put("email", "humans@pikai.dev");
        from.put("name", "Pikai Group");
        requestBody.put("from", from);

        // 收件人列表（支持多个收件人）
        List<Map<String, String>> toList = new ArrayList<>();
        Map<String, String> to = new LinkedHashMap<>();
        to.put("email", email);
        toList.add(to);
        requestBody.put("to", toList);

        // 模板 UUID 和变量
        requestBody.put("template_uuid", apisPorperties.getMail().getTemplateUUID());
        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("code", code);
        requestBody.put("template_variables", variables);

        // 封装请求实体
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送请求并处理响应
        try {
            ResponseEntity<String> response = okRestTemplate.exchange(
                    "https://send.api.mailtrap.io/api/send",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("邮件发送成功: " + response.getBody());
            } else {
                System.out.println("邮件发送失败，状态码: " + response.getStatusCode());
            }
            return true;
        } catch (Exception e) {
            System.err.println("请求异常: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    private static final String API_TOKEN = "3a49845f4d61f5cbf4ffe7f358195ef7";
    private static final String API_URL = "https://send.api.mailtrap.io/api/send";

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        String json = "{\"from\": {\"email\": \"humans@pikai.dev\", \"name\": \"Pikai Group\"}, \"to\": [{\"email\": \"854757115@qq.com\"}],\"template_uuid\": \"96829dfe-5014-4687-af73-90d4794c34f6\",\"template_variables\": {\"code\": \"920387ef2b4d491fab1dc76ddf324d88\"}}";

        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(okhttp3.MediaType.parse("application/json"),json))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response Code: " + response.code());
            System.out.println("Response Body: " + response.body().string());
        }
    }


}
