package com.example.BoardProject.DTO;

import com.example.BoardProject.Entity.Article;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
    private String postdate;
}