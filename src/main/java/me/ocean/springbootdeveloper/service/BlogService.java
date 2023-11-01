package me.ocean.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.ocean.springbootdeveloper.domain.Article;
import me.ocean.springbootdeveloper.dto.AddArticleRequest;
import me.ocean.springbootdeveloper.dto.UpdateArticleRequest;
import me.ocean.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor    // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service                    // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName) {

        return blogRepository.save(request.toEntity(userName));
    }

    // 블로그 글 목록 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 조회 API 구현
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

//    public void delete(long id) {
//        blogRepository.deleteById(id);
//    }

    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}
