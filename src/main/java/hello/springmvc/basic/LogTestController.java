package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass()); -> @Slf4j

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        // sout는 logging level 상관없이 무조건 출력 그러면 안된다.
        System.out.println("name = " + name);

        // logging level
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok"; // RestController로 하면 string이 그대로 반환된다.
    }
}
