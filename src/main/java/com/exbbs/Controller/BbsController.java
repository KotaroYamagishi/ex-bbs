package com.exbbs.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletContext;

import com.exbbs.Domain.Article;
import com.exbbs.Domain.Comment;
import com.exbbs.Form.ArticleForm;
import com.exbbs.Form.CommentForm;
import com.exbbs.Repository.ArticleRepository;
import com.exbbs.Repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/article")
public class BbsController {
    
    @ModelAttribute
    public ArticleForm setUpForm1() {
        return new ArticleForm();
    }
    
    @ModelAttribute
    public CommentForm setUpForm2() {
        return new CommentForm();
    }

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ServletContext applicaiton;


    @RequestMapping("")
    public String index(){
        List<Article> articleList = (List<Article>) applicaiton.getAttribute("articleList");
        articleList=articleRepository.findAll();
        if(Objects.isNull(articleList)){
            articleList=new LinkedList<Article>();
            applicaiton.setAttribute("articleList",articleList);
        }
        // for(Article article : articleList){
        //     List<Comment> commentList =article.getCommentList();
        //     commentList.stream().distinct().collect(Collectors.toList());
        // }
        
        
        // List<Comment> commentList=new LinkedList<Comment>();
        // for(Article article:articleList){
        //     commentList=commentRepository.findByArticleId(article.getId());
        //     article.setCommentList(commentList);
        // }
        
        applicaiton.setAttribute("articleList",articleList);

        return "article";
    }

    // 記事投稿ボタン押下時
    @RequestMapping("/article-input")
    public String insertArticle(@Validated ArticleForm articleForm,BindingResult result){
        if(result.hasErrors()){
            return index();
        }

        List<Article> articleList= (List<Article>) applicaiton.getAttribute("articleList");
        Article article=new Article();
        article.setName(articleForm.getName());
        article.setContent(articleForm.getContent());
        articleRepository.insert(article);
        articleList.add(0,article);
        applicaiton.setAttribute("articleList",articleList);

        return "redirect:/article";
    }

    // コメント投稿ボタン押下時
    @RequestMapping("/article-comment")
    public String insertComment(@Validated CommentForm form,BindingResult result ,String articleId) {
        if(result.hasErrors()){
            return index();
        }

        Comment comment = new Comment();
        comment.setName(form.getName());
        comment.setContent(form.getContent());
        comment.setArticleId(Integer.parseInt(articleId));
        commentRepository.insert(comment);

        return "redirect:/article";
    }


    // 記事削除ボタン押下時
    @RequestMapping("/article-delete")
    public String deleteArticle(String articleId){
        articleRepository.delete(Integer.parseInt(articleId));
        return "redirect:/article";
    }
}