package me.kwangmin.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        // given(준비)

        // when(실행)
        List<Member> members = memberRepository.findAll();

        // then(검증)
        assertThat(members.size()).isEqualTo(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        // given(준비)

        // when(실행)
        Member member = memberRepository.findById(2L).get();

        // then(검증)
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        // given(준비)

        // when(실행)
        Member member = memberRepository.findByName("C").get();

        // then(검증)
        assertThat(member.getName()).isEqualTo("C");
    }

    @DisplayName("레코드 삽입 테스트")
    @Test
    void saveMember() {
        // given
        Member m = new Member("Kwangmin");

        // when : 레코드 삽입 테스트
        Member savedMember = memberRepository.save(m);
        // 1. Member 객체 m에 primary key인 id가 없으면 :
        // insert into member(name) values("Kwangmin")
        // 2. Member 객체 m에 primary key가 이미 설정 되어 있으면 :
        // update member set name- "Kwangmin" where id = 1;
        // save 메서드가 성공하면 삽입된 또는 uqdate된 레코드를 Member 객체로 반환
        // 3. return new Member(부여된 id, "Kwangmin");

        // then
        // Optional<Member>
        assertThat(savedMember.getId()).isNotNull(); // 삽입에 성공했는지 체크.
        // MemberRepository의 findById() 메서드는
        // 1. select * from member where id = :id
        // 2. return new Optional<Member>(1L, "Kwangmin");

        // assertThat(memberRepository.findById(savedMember.getId()).get().getName()).isEqualTo("Kwangmin");
        Long id = savedMember.getId();
        Optional<Member> result = memberRepository.findById(id);
        // select * from member where id = :id
        Member member = result.get();
        String name = member.getName();
        assertThat(name).isEqualTo("Kwangmin");
    }

    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers() {
        // given
        List<Member> members = List.of(new Member("HongGilDong"), new Member("GoGilDong"));

        // when
        memberRepository.saveAll(members);

        // then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 테스트")
    @Test
    void deleteALL(){
    // given
    // when
    memberRepository.deleteAll();
    // then
    }

    @Sql("/insert-members.sql")
    @DisplayName("Update Test")
    @Test
    void update() {
        // given
        Member member = memberRepository.findById(2L).get();

        // when
        member.changeName("kwangmin");

        // then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("kwangmin");
    }
}