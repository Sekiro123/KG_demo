package com.example.KG_demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet("/entitySearchServlet")
public class entity_Search_Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取需要查询的实体名称
        if (request.getParameter("name") != null){
            String entity = URLEncoder.encode(request.getParameter("name") ,"UTF-8");
            String URL = "http://192.168.31.65:8085/neo4j/findOne?" + "name=" + entity;
            System.out.println(URL);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(URL);
            CloseableHttpResponse response1 = httpClient.execute(httpGet);
            if (response1.getStatusLine().getStatusCode() == 200){
                HttpEntity httpEntity = response1.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(content);
                response.setContentType("application/json;charset=utf-8");
                response.setHeader("Access-Control-Allow-Origin", "*");
                PrintWriter pw = response.getWriter();
                pw.write(content);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
