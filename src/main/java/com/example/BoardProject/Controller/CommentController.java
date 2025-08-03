package com.example.BoardProject.Controller;

import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class CommentController { // View 반환 목적으로 작성했으나 현재 Rest API로 비동기 출력으로 사용하게 되어 불필요. 추후 사용 가능성 위해 미삭제.
    @Autowired
    CommentRepository commentRepository;
}
