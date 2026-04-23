package me.kwangmin.springdeveloper.repository;

import me.kwangmin.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
