package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);// 5. 핸들러 호출하고 modelView 반환

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

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
