package org.spring.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spring.AppConfig;
import org.spring.member.MemberService;
import org.spring.member.MemberServiceImpl;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class); // ac.getBean(빈 이름, 타입)

//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass() = " + memberService.getClass());

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class); // ac.getBean(빈 이름, 타입)
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        // Spring Bean 구성정보 파일(AppConfig)에 명시되어있는 인터페이스 타입으로 찾아오지 않고 아래와 같이 실제 구체 클래스 정보로도 찾아올 수 있음
        // but, 다형성을 고려했을 때 지양해야 하는 방식 (유연성 낮음)
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class); // ac.getBean(빈 이름, 타입)
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회 실패")
    void findBeanByNameNone() {
        // 아래와 같이 코드 작성 시 NoSuchBeanDefinitionException 익셉션 발생
        // org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'xxxxx' available
        // MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);

        // 코드를 실행했을 때 expected되는 throw 타입이 일치할 경우 테스트가 성공한다.
        // 따라서 하기 소스는 성공 케이스
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxxx", MemberService.class));
    }
}
