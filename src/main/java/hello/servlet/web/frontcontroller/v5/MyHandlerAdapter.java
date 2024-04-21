package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {
    // 컨트롤러가 넘어왔을 때 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단
    boolean support(Object handler);

    // 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}