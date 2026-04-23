package me.kwangmin.springdeveloper;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kwangmin.springdeveloper.dao.Article;
import me.kwangmin.springdeveloper.dto.AddArticleRequest;
import me.kwangmin.springdeveloper.dto.UpdateArticleRequest;
import me.kwangmin.springdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 객체를 -> JSON 문자열로 변환

    @Autowired
    protected BlogRepository blogRepository;

    @BeforeEach
    public void deleteAll() {
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "테스트";
        final String content = "블로그 글 첫 번째 입니다.";
        final AddArticleRequest article = new AddArticleRequest(title, content);
        final String requestBody = objectMapper.writeValueAsString(article);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody)); // Http Request 보내는 것을 흉내낸다.

        // then
        result.andExpect(status().isCreated());
        List<Article> articles = blogRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }

    @DisplayName("findAllarticles: 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllarticles() throws Exception {
        // given : 데이터를 하나 삽입
        final String url = "/api/articles";

        blogRepository.save(Article.builder()
                .title("title")
                .content("content")
                .build());

        // when : get 방식으로 /api/articles
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON_VALUE));

        // then : status OK이고 읽어온 데이터의 내용이 내가 삽입한 내용과 동일하다.
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].content").value("content"));

        /*
        {
            "title":"안녕하세요",
            "content":"반갑습니다."
        }
        // -> jsonPath("$.title"), jsonPath("$.content");
         */
    }

    @DisplayName("findArticle: 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given (데이터 준비: 블로그글 하나 생성)
        final String url = "/api/articles/{id}";
        final String title = "블로그 제목";
        final String content = "블로그 내용";

        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when  (실행: 위에서 생성된 블로그글을 조회)
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then  (검증: status가 200이고 조회한 블로그 제목과 내용이 위에서 삽입한 그것과 동일한지 확인)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content)); // 반환된 JSON 객체의 content 값이 변수 content와 동일하고
        // 반환된 JSON 객체의 title 값이 변수 title과 동일한지 확인

    }

    @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "4월 16일";
        final String content = "백엔드프로그래밍(II) 수업";
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId())).andExpect(status().isOk());

        // then
        List<Article> articles = blogRepository.findAll();
        assertThat(articles).isEmpty();
    }

    @DisplayName("updateArticle: 블로그 글 수정에 성공한다")
    @Test
    public void updateArticle() throws Exception {
        // given : 레코드 생성, 변경내용 작성
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";
        Article savedArticle = blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());

        final String newTitle = "Junit에서 제목 변경";
        final String newContent = "Junit에서 내용 변경";
        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when : /api/articles/생성된 레코드 id -> put 방식 요청
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then : status code가 200, repository에서 변경된 내용 검증
        result.andExpect(status().isOk());
        // [수정된 부분] findById의 결과인 Optional에 orElseThrow()를 사용해야 합니다.
        Article article = blogRepository.findById(savedArticle.getId()).orElseThrow();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }
}