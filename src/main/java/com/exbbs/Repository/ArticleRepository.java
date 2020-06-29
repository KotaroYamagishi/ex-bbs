package com.exbbs.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.exbbs.Domain.Article;
import com.exbbs.Domain.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER
    =(rs,i)->{
        Article article=new Article();
        Comment comment=new Comment();
        article.setId(rs.getInt(1));
        article.setName(rs.getString(2));
        article.setContent(rs.getString(3));
        List<Comment> commentList=new ArrayList<>();
        comment.setArticleId(rs.getInt(1));
        comment.setName(rs.getString(4));
        comment.setContent(rs.getString(5));
        commentList.add(comment);
        article.setCommentList(commentList);
        return article;
    };

    

    
    /** 
     * @return List<Article>
     */
    // findAll()記事を表示する
    public List<Article> findAll(){
        String sql = "SELECT a.id,a.name,a.content,c.name,c.content FROM articles as a LEFT JOIN comments as c on a.id=c.article_id ORDER BY a.id desc";
        List<Article> articleList=template.query(sql,ARTICLE_ROW_MAPPER);
        Map<Integer,Article> articleMap=new LinkedHashMap<>();
        List<Comment> commentList=new ArrayList<>();
        for(Article article:articleList){
            List<Comment> comments =article.getCommentList();
            for(Comment comment:comments){
                commentList.add(comment);
            }
        }
        for(Article article:articleList){
            List<Comment> comments =new ArrayList<>();
            for(Comment comment:commentList){
                if(article.getId()==comment.getArticleId()){
                    comments.add(comment);
                }
            }
            article.setCommentList(comments);
            articleMap.put(article.getId(),article);
        }
        List<Article> articles=new ArrayList<>(articleMap.values());

        return articles;
    }

    
    /** 
     * @param article
     */
    // 投稿内容ををDBに追加する
    public void insert(Article article){
        String sql = "INSERT INTO articles (name,content) VALUES (:name, :content)";
        SqlParameterSource param=new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
        template.update(sql, param);
    }

    
    /** 
     * @param id
     */
    // deleteByidでDBから削除
    public void delete(Integer id){
        String sql="BEGIN;"
        +"DELETE FROM comments WHERE article_id=:id;"
        +"DELETE FROM articles WHERE id = :id;"
        +"COMMIT;";
        SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }
}