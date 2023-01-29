package org.spring.discount;

import org.spring.member.Member;
import org.spring.member.MemberRepository;
import org.spring.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    // interface만 의존하도록 코드 변경 -> DIP 지키는 형태로 변경!
    private final MemberRepository memberRepository;
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    private DiscountPolicy discountPolicy;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //고정할인정책
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); //비율할인정책

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
