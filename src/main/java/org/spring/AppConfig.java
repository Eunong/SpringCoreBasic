package org.spring;

import org.spring.discount.*;
import org.spring.member.MemberRepository;
import org.spring.member.MemberService;
import org.spring.member.MemberServiceImpl;
import org.spring.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 애플리케이션의 실제 동작에 필요한 "구현객체를 생성"
 * 객체의 생성과 연결을 담당
 * 의존관계주입 or 의존성주입 : 생성한 객체 인스턴스의 참조(레퍼런스)를 통해 "생성자 주입(연결)" Dependency injection(DI)
 */

/**
 * Spring에서는 구성정보를 담당하는 class에 @Configuration annotation을 붙여서 사용한다.
 */
@Configuration
public class AppConfig {

    //MemberService 역할
    @Bean
    public MemberService memberService() {
        //구현
        return new MemberServiceImpl(memberRepository());
    }

    //MemberRepository 역할
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    //OrderService 역할
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    //구현
    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
//        return new RateDiscountPolicy();
    }
}
