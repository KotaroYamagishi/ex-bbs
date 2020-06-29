package com.exbbs.Domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Comment {

    
    private Integer id;
    @NotBlank(message = "コメント者名を入力してください")
    @Size(min =1, max =50,message = "1文字以上50文字以下で入力してください")
    private String name;
    @NotBlank(message = "コメント内容を入力してください")
    @Size(min =1, max =140,message = "1文字以上140文字以内で入力してください")
    private String content;
    private Integer articleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    
}
