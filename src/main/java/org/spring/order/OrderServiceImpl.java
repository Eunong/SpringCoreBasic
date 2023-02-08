package org.spring.order;

import lombok.RequiredArgsConstructor;
import org.spring.annotation.MainDiscountPolicy;
import org.spring.discount.DiscountPolicy;
import org.spring.member.Member;
import org.spring.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // final이 붙은 변수를 가진 생성자를 자동으로 만들어줌
public class OrderServiceImpl implements OrderService {

    // interface만 의존하도록 코드 변경 -> DIP 지키는 형태로 변경!
    private final MemberRepository memberRepository; // final 키워드 추가 시 생성자에서만 값을 셋팅하고 변경할 수 없음
    // @Autowired private MemberRepository memberRepository; // @Autowired로 필드 생성자 주입은 거의 사용하지 않음
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final DiscountPolicy discountPolicy;
    // @Autowired private DiscountPolicy discountPolicy;
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //고정할인정책
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); //비율할인정책

    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }
     */

    // @Autowired // 생성자가 딱 1개인 경우 스프링 빈인 경우에는 @Autowired를 생략해도 자동으로 주입이 된다.
    // @RequiredArgsConstructor 와 동일
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    // public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.OrderServiceImpl");
        System.out.println("memberRepository = " + memberRepository + " discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    /*public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository()  {
        return memberRepository;
    }
}
