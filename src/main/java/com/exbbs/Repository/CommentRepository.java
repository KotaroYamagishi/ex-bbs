package com.exbbs.Repository;


import com.exbbs.Domain.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Comment> COMMENT_ROW_MAPPER
    =(rs,i)->{
        Comment comment=new Comment();
        comment.setId(rs.getInt("id"));
        comment.setName(rs.getString("name"));
        comment.setContent(rs.getString("content"));
        comment.setArticleId(rs.getInt("article_id"));
        return comment;
    };


    
    /** 
     * @param comment
     */
    // insertでDBに保存
    public void insert(Comment comment){
        String sql="INSERT INTO comments (name,content,article_id) VALUES(:name,:content,:articleId)";
        SqlParameterSource param=new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content",comment.getContent()).addValue("articleId",comment.getArticleId());
        template.update(sql, param);
    }

    
    /** 
     * @param articleId
     */
    // deleteByidでDBから削除
    public void delete(Integer articleId){
        String sql="DELETE FROM comments WHERE article_id=:articleId";
        SqlParameterSource param=new MapSqlParameterSource().addValue("articleId", articleId);
        template.update(sql, param);
    }
}