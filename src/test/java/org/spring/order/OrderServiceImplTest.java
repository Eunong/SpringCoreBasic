package org.spring.order;

import org.junit.jupiter.api.Test;
import org.spring.discount.FixDiscountPolicy;
import org.spring.member.Grade;
import org.spring.member.Member;
import org.spring.member.MemberRepository;
import org.spring.member.MemoryMemberRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy()); // 생성자 주입 방식인 경우에는 별도로 선언한 객체를 주입할 수도 있다.

        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}