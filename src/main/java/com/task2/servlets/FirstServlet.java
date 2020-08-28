package com.task2.servlets;

import com.task2.service.JmsConsumer;
import com.task2.service.RestClient;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "hello", urlPatterns = {"/go"})
public class FirstServlet extends HttpServlet  {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START SERVLET");

//        response.setContentType("text/event-stream");
//
//        //encoding must be set to UTF-8
//        response.setCharacterEncoding("UTF-8");
//
//        PrintWriter writer = response.getWriter();
//
//        for (int i = 0; i < 10; i++) {
//
//            writer.write("data: " + System.currentTimeMillis() + "\n\n");
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        writer.close();

    }
}
