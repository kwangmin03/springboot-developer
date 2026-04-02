package me.kwangmin.springdeveloper;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}