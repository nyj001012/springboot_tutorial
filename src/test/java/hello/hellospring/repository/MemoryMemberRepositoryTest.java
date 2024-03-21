package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

    @Nested
    class TestSave {
        @Test
        void save() {
            Member yejin = new Member();
            yejin.setName("예진");
            Member saveResult = repository.save(yejin);
            assertThat(saveResult).isEqualTo(yejin);
            assertThat(saveResult.getId().longValue()).isEqualTo(1);
        }
    }

    @Nested
    class TestExcludeSave {
        @BeforeEach
        void beforeEach() {
            Member yejin = new Member();
            Member yena = new Member();
            yejin.setName("예진");
            yena.setName("예나");
            repository.save(yejin);
            repository.save(yena);
        }

        @Test
        void findById() {
            Optional<Member> findResult = repository.findById(1L);
            Member member = findResult.orElse(null);
            assertThat(member).isNotNull();
            assertThat(member.getName()).isEqualTo("예진");
        }

        @Test
        void findByIdFail() {
            Optional<Member> findResult = repository.findById(-2L);
            assertThat(findResult.isEmpty()).isTrue();
        }

        @Test
        void findByName() {
            Optional<Member> findResult = repository.findByName("예진");
            Member member = findResult.orElse(null);
            assertThat(member).isNotNull();
            assertThat(member.getId()).isEqualTo(1);
        }

        @Test
        void findByNameFail() {
            Optional<Member> findResult = repository.findByName("고구마");
            assertThat(findResult.isEmpty()).isTrue();
        }

        @Test
        void findAll() {
            List<Member> memberList = repository.findAll();
            assertThat(memberList.size()).isEqualTo(2);
        }
    }
}
