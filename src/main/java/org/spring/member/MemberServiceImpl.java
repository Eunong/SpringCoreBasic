package org.spring.member;

public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //DIP를 지키기 위한 생성자 선언
    //생성자 주입!
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
