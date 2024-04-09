package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("에진");

        memberService.join(member);

        Optional<Member> findResult = memberRepository.findByName(member.getName());
        Member joinedMember = findResult.get();
        assertThat(member.getName()).isEqualTo(joinedMember.getName());
    }

    @Test
    void 회원가입_중복_유저() {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("예진");
        member2.setName("예진");

        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 모든_멤버_리스트_가져오기() {
        for (int i = 0; i < 10; i++) {
            Member member = new Member();
            member.setName(Integer.toString(i) + "번째 유저");
            memberService.join(member);
        }

        List<Member> members = memberService.findMembers();

        assertThat(members.size()).isGreaterThan(0);
    }

    @Test
    void id로_멤버_찾기() {
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("피카츄");
        member2.setName("리자몽");
        memberService.join(member1);
        memberService.join(member2);

        Optional<Member> findResult = memberService.findOneById(member1.getId());

        findResult.ifPresentOrElse(member -> {
            assertThat(member.getName()).isEqualTo(member1.getName());
        }, () -> {
            fail();
        });
    }

    @Test
    void 없는_id로_멤버_찾기() {
        Member member1 = new Member();
        member1.setName("피카츄");
        memberService.join(member1);

        Optional<Member> findResult = memberService.findOneById(-1L);

        assertThat(findResult.isEmpty()).isTrue();
    }
}