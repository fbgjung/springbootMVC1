package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>(); // 아무 Controller든 지원하기 위해 Object로
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    // 값 넣어주기
    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        // V3 지원
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4 지원
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 핸들러(컨트롤러) 조회 MemberFormControllerV3 반환
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // V3냐 V4냐 등에 따라 핸들러 어댑터 목록을 뒤져서 핸들러 어댑터를 찾아와야한다.
        // 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회 ControllerV3HandlerAdapter 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // 3. 핸들러 어댑터 호출
        // MemberFormControllerV3가 handle로 넘어온다.
        ModelView mv = adapter.handle(request, response, handler); // modelView 반환

        // 6. viewName 가져와서 viewResolver 호출
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // 7. render(model) 호출
        view.render(mv.getModel(), request, response);
    }


    private Object getHandler(HttpServletRequest request) {
        // 매핑 정보를 가지고 handlerMappingMap에서 핸들러 찾기
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 핸들러 어댑터를 다 뒤진다. 지금은 V3밖에 없고 initHandlerAdapters에
        // MemberFromControllerV3
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.support(handler)) { // 있으면 support를 호출한다. V3 어댑터가 V3 핸들러를 처리할 수 있다.
                return adapter; // 어댑터 반환
            } // V3 없으면 V4 찾고.. 이런식으로 쭉 돈다.
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
