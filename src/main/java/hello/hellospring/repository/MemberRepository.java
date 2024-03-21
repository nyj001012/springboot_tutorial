package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(AtomicLong id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}