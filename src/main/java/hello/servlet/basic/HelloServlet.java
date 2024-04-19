package hello.servlet.basic;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // control o
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // http://localhost:8080/hello?username=kim query parameter 조회
        System.out.println("username = " + username);

        // ContentType header 정보에 들어간다.
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        response.getWriter().write("hello " + username);
    }
}