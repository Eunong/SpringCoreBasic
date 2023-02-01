package org.spring.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.spring.AppConfig;
import org.spring.member.MemberRepository;
import org.spring.member.MemberServiceImpl;
import org.spring.order.OrderServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 같은 객체가 출력된다. java 코드 상으로 객체 생성을 세번 하게 되어있는데 어떻게 같은 객체가 출력되었을까???
        /**
         * 실제 함수 호출은 아래와 같이 memberRepository 함수가 3회가 아니라 1회만 호출되는 것을 확인할 수 있다.
         * call AppConfig.memberService
         * call AppConfig.memberRepository
         * call AppConfig.orderService
         */
        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberRepository)
                .isSameAs(memberRepository1)
                .isSameAs(memberRepository2);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        /**
         * AppConfig.class에 @Configuration이 붙어있을 경우
         *  -> bean = class org.spring.AppConfig$$EnhancerBySpringCGLIB$$af748d6a
         *  -> call AppConfig.memberRepository 가 1회 호출된다.
         *
         * AppConfig.class에 @Configuration이 붙어있지 않을 경우
         *  -> bean = class org.spring.AppConfig
         *  -> call AppConfig.memberRepository 가 3회 호출된다.
         */
        System.out.println("bean = " + bean.getClass()); // bean = class org.spring.AppConfig$$EnhancerBySpringCGLIB$$af748d6a
    }

}
