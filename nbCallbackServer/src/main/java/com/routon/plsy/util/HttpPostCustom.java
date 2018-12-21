package com.routon.plsy.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * HTTP Post工具
 */
public class HttpPostCustom {

    private static Logger logger = LoggerFactory.getLogger(HttpPostCustom.class);

    public static String sendHttpPost(String url, String body){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String responseContent = null;
        int httpCode = 0;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(body));
            response = httpClient.execute(httpPost);
            httpCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return "error";

        } catch (IOException e) {
            logger.error(e.getMessage());
            return "error";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "error";
        } finally {
            try {
                //response.close();
                httpClient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info(responseContent);
        return String.valueOf(httpCode);
    }
}
