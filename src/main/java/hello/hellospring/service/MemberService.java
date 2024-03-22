package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     *
     * @param member 가입할 멤버 객체
     * @return 가입한 멤버 객체의 id
     */
    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검사
     *
     * @param member 검사할 회원
     */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     *
     * @return 전체 회원 리스트
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * id로 한 회원 조회
     *
     * @param memberId 조회할 회원의 Id
     * @return Member의 Optional 객체
     */
    public Optional<Member> findOneById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
