package com.gdut.xdvideo.utils;


import com.google.gson.Gson;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装http的get,post请求
 */

public class HttpUtils {

    private static Gson gson = new Gson();


    /**
     * 封装get请求
     * @param url
     * @param timeout
     * @return
     */
    public static Map<String,Object> doGet(String url,int timeout){

        HashMap<String, Object> map = new HashMap<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)//设置连接时间
                .setConnectionRequestTimeout(timeout)//设置请求时间
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(true)//允许重定向
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                String jsonResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                map = gson.fromJson(jsonResult, map.getClass());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 封装post请求
     * @param url
     * @param data
     * @param timeout
     * @return
     */

     public String doPost(String url,String data,int timeout){

         CloseableHttpClient httpClient = HttpClients.createDefault();

         RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout) //设置连接时间
                 .setConnectionRequestTimeout(timeout)//设置请求时间
                 .setSocketTimeout(timeout)
                 .setRedirectsEnabled(true)//允许重定向
                 .build();

         HttpPost httpPost = new HttpPost(url);
         httpPost.setConfig(requestConfig);
         httpPost.addHeader("Content-Type","text/html; chartset=UTF-8");

         if(data !=null && data instanceof String){
             StringEntity stringEntity = new StringEntity(data, "UTF-8");//使用字符串传参
             httpPost.setEntity(stringEntity);
         }

         try {
             CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
             if(httpResponse.getStatusLine().getStatusCode() == 200){
                 String result = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
                 return result;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }finally {
             try {
                 httpClient.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

         return null;
     }
}