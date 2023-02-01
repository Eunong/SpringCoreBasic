package org.spring.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // 오류 코드

        // ThreadA : A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        // ThreadB : B사용자 20000원 주문
        statefulService2.order("userB", 20000);
        
        // ThreadA : A사용자의 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("userAPrice(error) = " + price); // 당연히 20000이 출력된다. A사용자의 주문금액 조회 오류 발생!

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        // 정상 코드
        int userAPrice = statefulService1.orderStateless("userA", 10000);
        int userBPrice = statefulService1.orderStateless("userB", 20000);
        System.out.println("userAPrice = " + userAPrice);
        System.out.println("userBPrice = " + userBPrice);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}