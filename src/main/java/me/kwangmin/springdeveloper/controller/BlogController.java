package me.kwangmin.springdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.kwangmin.springdeveloper.dao.Article;
import me.kwangmin.springdeveloper.dto.AddArticleRequest;
import me.kwangmin.springdeveloper.dto.ArticleResponse;
import me.kwangmin.springdeveloper.dto.UpdateArticleRequest;
import me.kwangmin.springdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<Article> articles = blogService.findAll();
        List<ArticleResponse> result = articles.stream().map(ArticleResponse::new).toList();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/api/articles/{id}") // /api/articles/3
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void>
    deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }
}
