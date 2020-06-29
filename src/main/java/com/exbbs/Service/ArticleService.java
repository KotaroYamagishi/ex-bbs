package com.exbbs.Service;

import com.exbbs.Domain.Article;
import com.exbbs.Repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    
    @Autowired
    private ArticleRepository repository;

    // 投稿内容ををDBに追加する
    public void insertArticle(Article article){
        repository.insert(article);
    }

    // deleteByidでDBから削除
    public void delete(Integer id){
        repository.delete(id);
    }
}