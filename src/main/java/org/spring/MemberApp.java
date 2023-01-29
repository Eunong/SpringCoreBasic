package org.spring;

import org.spring.member.Grade;
import org.spring.member.Member;
import org.spring.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService(); //memberServiceImpl
//        MemberService memberService = new MemberServiceImpl();

        // spring f/w ApplicationContext 사용하기
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 기본적으로 메서드 이름으로 bean의 이름이 지정된다. (ex, memberService)
        // spring container에 등록된 객체를 이름, 타입 으로 꺼내서 사용하면 된다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());

    }
}
