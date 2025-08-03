package com.example.BoardProject;

import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Entity.Member;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Repository.CommentRepository;
import com.example.BoardProject.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.LocalTime.now;

@Component
@RequiredArgsConstructor
public class DBMacro implements CommandLineRunner { // Project 실행 시 DB 데이터 자동 입력
    @Autowired
    private final ArticleRepository articleRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args){
        List<Member> members = new ArrayList<>();
        for (int i=0; i<50; i++){                   // 회원 50명 추가
            Member member = Member.builder()
                    .username("Member "+String.valueOf(i+1))
                    .password(String.valueOf(i))
                    .joineddate(now().toString())
                    .build();
            members.add(member);
        }
        memberRepository.saveAll(members);

        List<Article> articles = new ArrayList<>();
        for (int i=0; i<50; i++){                   // 게시물 50개 작성
            Article article = Article.builder()
                    .username(members.get(i).getUsername())
                    .title("Title "+String.valueOf(i+1))
                    .content("Content "+String.valueOf(i+1))
                    .postdate(now().toString())
                    .build();
            articles.add(article);
        }
        articleRepository.saveAll(articles);

        List<Comment> comments = new ArrayList<>();
        for (int i=0; i<500; i++){                   // 댓글 500개 작성
            Comment comment = Comment.builder()
                    .username(members.get(i%50).getUsername())
                    .comment("Comment "+String.valueOf(i+1))
                    .article(articles.get(i%50))
                    .commentdate(now().toString())
                    .build();
            comments.add(comment);
        }
        commentRepository.saveAll(comments);
    }
}
