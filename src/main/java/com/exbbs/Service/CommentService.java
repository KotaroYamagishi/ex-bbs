package com.exbbs.Service;

import com.exbbs.Domain.Comment;
import com.exbbs.Repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    
    @Autowired
    private CommentRepository repository;

    // insertでDBに保存
    public void insert(Comment comment){
        repository.insert(comment);
    }

    // deleteByidでDBから削除
    public void delete(Integer articleId){
        repository.delete(articleId);
    }
}