package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {
    /**
     *
     * @param paramMap
     * @param model
     * @return viewName
     */
    // Object는 아무거나 넣을 수 있다.
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
