package org.spring.autowired;

import org.junit.jupiter.api.Test;
import org.spring.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // @Autowired // => 존재하지 않는 Bean이기 때문에 오류 발생
        @Autowired(required = false) // => 존재하지 않는 Bean이기 때문에 메서드 호출 자체가 되지 않음
        public void setNoBean1(Member noBean1) { // Member는 스프링 빈이 아님
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) { // => 존재하지 않는 Bean인 경우 null로 들어감
            System.out.println("noBean2 = " + noBean2); // noBean2 = null
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) { // => 존재하지 않는 Bean인 경우 Optional.empty 들어감
            System.out.println("noBean3 = " + noBean3); // noBean3 = Optional.empty
        }
    }
}
