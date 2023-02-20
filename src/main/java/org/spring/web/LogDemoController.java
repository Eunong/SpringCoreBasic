package org.spring.web;

import lombok.RequiredArgsConstructor;
import org.spring.common.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    // private final ObjectProvider<MyLogger> myLoggerProvider;

    /**
     * CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입해준다.
     * 스프링 컨테이너에 가짜 프록시 객체를 등록한다.
     * ac.getBean("myLogger", MyLogger.class)로 조회해도 프록시 객체가 조회되는 것을 확인할 수 있다.
     * 의존관계 주입도 가짜 프록시 객체가 주입된다.
     * 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직이 수행된다.
     * 객체를 사용하는 클라이언트 입장에서는 진짜 객체인지 아닌지 모르게, 동일하게 사용할 수 있다.(다형성)
     */
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass()); // myLogger = class org.spring.common.MyLogger$$EnhancerBySpringCGLIB$$2980c591

        // MyLogger myLogger = myLoggerProvider.getObject(); // http 요청이 들어올 때 마다 이 시점에 MyLogger가 최초로 생성된다. 스프링 컨테이너가 빈을 생성하는 시점을 지연시킨다.
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);

        logDemoService.logic("testId");

        return "OK";
    }
}
