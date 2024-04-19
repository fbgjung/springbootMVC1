package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    // URL 호출이 되면 ControllerV1을 꺼내서 호출해
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // control enter
    public FrontControllerServletV1() {
        // 맵핑 정보를 생성할 때 그냥 미리 담아놓자.
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); // 매핑 URL, 호출된 컨트롤러
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        // ex)) /front-controller/v1/members
        String requestURI = request.getRequestURI(); // HTTP 요청 URL 들어오면

        ControllerV1 controller = controllerMap.get(requestURI); // 해당 컨트롤러 찾는다. 다형성!!
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
            return;
        }

        // 잘 조회가 되면 인터페이스 호출
        controller.process(request, response);
    }
}
