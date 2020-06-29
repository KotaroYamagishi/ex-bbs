package com.exbbs.Form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleForm {
    
    @NotBlank(message = "投稿者名を入力してください")
    @Size(min =1, max =50,message="1文字以上50文字以下で入力してください")
    private String name;

    @NotBlank(message = "投稿内容を入力してください")
    @Size(min =1, max =140,message="1文字以上140字以内で入力してください")
    private String content;

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


    
}