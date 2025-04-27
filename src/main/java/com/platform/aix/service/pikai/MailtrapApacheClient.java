package com.platform.aix.service.pikai;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年04月21日 18:35
 */
public class MailtrapApacheClient {
    private static final String API_TOKEN = "3a49845f4d61f5cbf4ffe7f358195ef7";
    private static final String API_URL = "https://send.api.mailtrap.io/api/send";
//    private static final String API_URL = "https://stoplight.io/mocks/railsware/mailtrap-api-docs/93404133/api/send";

    public static void main(String[] args) {
        sendEmail("yanl.fu@outlook.com", "920387ef2b4d491fab1dc76ddf324d88");
    }

    public static void sendEmail(String email, String code) {
        // 1. 创建 HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // 2. 构建 JSON 请求体（与 CURL 完全一致）
            String jsonBody = String.format(
                    "{" +
                            "\"from\": {\"email\": \"humans@pikai.dev\", \"name\": \"Pikai Group\"}," +
                            "\"to\": [{\"email\": \"%s\"}]," +
                            "\"template_uuid\": \"96829dfe-5014-4687-af73-90d4794c34f6\"," +
                            "\"template_variables\": {\"code\": \"%s\"}" +
                            "}",
                    email, code
            );

            // 3. 创建 POST 请求
            HttpPost httpPost = new HttpPost(API_URL);

            // 设置请求头（关键点：Header 顺序和内容）
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_TOKEN);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

            // 设置请求体
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // 4. 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                System.out.println("响应内容: " + EntityUtils.toString(response.getEntity()));
            }

        } catch (Exception e) {
            System.err.println("请求异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
