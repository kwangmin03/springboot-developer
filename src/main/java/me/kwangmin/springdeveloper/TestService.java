package me.kwangmin.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    TestRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // select * from member;
    }

    public Member saveMember(Member member){
        return memberRepository.save(member); // insert into member(id, name) value
    }
}
