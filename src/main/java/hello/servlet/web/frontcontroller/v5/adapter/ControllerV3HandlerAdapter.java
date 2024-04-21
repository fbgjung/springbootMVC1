package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// ControllerV3를 지원하는 어댑터
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        // ControllerV3로 구현한 무언가가 넘어오면, true 반환
        // MemberFormControllerV3는 ControllerV3인스턴스이기 때문에 반환할 수 있따.
        return (handler instanceof ControllerV3); // V3 인스턴스냐 물어보기
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler; // MemberFormControllerV3

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap); // 5. 핸들러 호출하고 modelView 반환

        return mv; // handler 가 오면, 반환 타입을 modelView로 맞춰서 반환해줘야 한다.
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        // 컨트롤러 꺼내왔어. paramMap 을 넘겨줘야한다.
        Map<String, String> paramMap = new HashMap<>();
        // 모든 파라미터의 이름을 다 가져온다. request.getParameter(paramName): value
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}