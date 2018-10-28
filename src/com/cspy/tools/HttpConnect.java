package com.cspy.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpConnect {


    public static String postConnect(String urlStr, Map<String, String> params) {
        StringBuilder param = new StringBuilder();

        String result = "invalid";

        Set<String> keys = params.keySet();
        for (String key:keys) {
            param.append("&");
            param.append(key);
            param.append("=");
            param.append(params.get(key));
        }
        if (keys.size() != 0) {
            param.replace(0,1,"");
        }
        System.out.println("参数为：" + param.toString());

        byte[] postData = param.toString().getBytes(StandardCharsets.UTF_8);

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("charset","utf-8");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.length));
            connection.setUseCaches(false);

//            PrintWriter pw = new PrintWriter(connection.getOutputStream());
            DataOutputStream dw = new DataOutputStream(connection.getOutputStream());
            dw.write(postData);
            dw.flush();


            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = reader.readLine();
            System.out.println(result);

            dw.close();
            reader.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
