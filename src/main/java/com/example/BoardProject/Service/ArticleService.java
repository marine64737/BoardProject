package com.example.BoardProject.Service;

import com.example.BoardProject.DTO.ArticleForm;
import com.example.BoardProject.DTO.CommentForm;
import com.example.BoardProject.Entity.Article;
import com.example.BoardProject.Entity.Comment;
import com.example.BoardProject.Repository.ArticleRepository;
import com.example.BoardProject.Repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    //-----------------------------View 반환용 함수--------------------------------------------

    public void index(Model model, @RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Article> pageResult = articleRepository.findAll(pageable);
        model.addAttribute("articles", pageResult.getContent());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("currentPage", page+1);
        if (pageResult.hasNext()) {
            model.addAttribute("nextPage", page+1);
        }
        if (pageResult.hasPrevious()) {
            model.addAttribute("prevPage", page-1);
        }
    }

    public void create(UserDetails userDetails, ArticleForm form, Model model,
                         MultipartFile file, int page) throws IOException {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        String filename = null;
        if (file != null && !file.isEmpty()){
            log.info(file.toString());
            filename = file.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/upload/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String path = uploadDir+filename;
            file.transferTo(new File(path));
            Article article = Article.toEntity(form, userDetails.getUsername(), filename);
            article.setFilename(filename);
        }
        log.info(file.toString());
        Article article = Article.toEntity(form, userDetails.getUsername(), filename);
        articleRepository.save(article);
        Page<Article> pageResult = articleRepository.findAll(pageable);
        if (pageResult.hasNext()) {
            model.addAttribute("nextPage", page+1);
        }
        if (pageResult.hasPrevious()) {
            model.addAttribute("prevPage", page-1);
        }
        model.addAttribute("articles", pageResult.getContent());
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("currentPage", page+1);
    }

    public void view(UserDetails userDetails, Long id, Model model){
        Article board = articleRepository.findById(id).orElse(null);
        ArticleForm form = ArticleForm.createArticleForm(board);
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        model.addAttribute("post", form);
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        model.addAttribute("commentForms", commentForms);
    }

    public void modify(@PathVariable Long id, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        ArticleForm form = ArticleForm.createArticleForm(article);
        log.info(article.toString());
        Model saved = model.addAttribute("post", form);
        log.info(saved.toString());
    }

    public void modified(UserDetails userDetails, Long id, ArticleForm form, Model model, MultipartFile file) throws IOException {
        Article article = articleRepository.findById(id).orElse(null);
        ArticleForm articleForm = ArticleForm.createArticleForm(article);
        String filename = article.getFilename();
        if (file != null && !file.isEmpty()){
            log.info("File: "+file.toString());
            filename = file.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/upload/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String path = uploadDir+filename;
            file.transferTo(new File(path));
        }
        ArticleForm target = articleForm.patch(form);
        Article article1 = Article.createArticle(target);
        List<CommentForm> commentForms = commentService.viewByArticleId(id);
        log.info(article.toString());
        log.info(target.toString());
        Article saved = articleRepository.save(article1);
        log.info(saved.toString());
        Model savedModel = model.addAttribute("post", target);
        model.addAttribute("commentForms", commentForms);
        model.addAttribute("new-comment-nickname", userDetails.getUsername());
        log.info(savedModel.toString());
    }

    public void uploadFile(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException{
        if (file != null){
            String filename = file.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/upload/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();
            String path = uploadDir+filename;
            file.transferTo(new File(path));
            redirectAttributes.addFlashAttribute("file", filename);
        }
    }

    //-----------------------------REST API JSON 반환용 함수--------------------------------------------

    public List<Article> index(){
        List<Article> articleList = articleRepository.findAll();
        return articleList;
    }
    public Article create(ArticleForm form, String username, String filename){
        Article article = Article.toEntity(form, username, filename);
        if (article.getId() != null){
            return null;
        }
        Article saved = articleRepository.save(article);
        return saved;
    }
    public Article show(Long id){
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }
    public Article update(Long id, ArticleForm form, String username, String filename){
        Article article = articleRepository.findById(id).orElse(null);
        Article target = Article.toEntity(form, username, filename);
        if (article == null || id != target.getId()){
            return null;
        }
        article.patch(target);
        Article updated = articleRepository.save(article);
        return updated;
    }
    public Article delete(Long id){
        List<Comment> c = commentRepository.findByArticleId(id);
        if (!c.isEmpty()) {
            List<Comment> comments = commentRepository.findByArticleId(id);
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
        }
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null){
            return null;
        }
        articleRepository.delete(article);
        return article;
    }

    //Transaction 연습용 함수

//    @Transactional
//    public List<Article> transaction(List<ArticleForm> forms, String username){
//        List<Article> articleList = forms.stream()
//                .map(form -> Article.toEntity(form, username))
//                .collect(Collectors.toList());
//        log.info(articleList.toString());
//        articleList.stream()
//                .forEach(article -> articleRepository.save(article));
//        log.info(articleList.toString());
//        articleRepository.findById(-1L)
//                .orElseThrow(() -> new IllegalArgumentException("Failed to pay!"));
//        return articleList;
//    }
}
