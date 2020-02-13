package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 記事の情報を受け取るフォームクラス.
 * 
 * @author katsuya.fujishima
 *
 */
public class ArticleForm {
	/** 名前 */
	@NotBlank(message="投稿者名は必須です")
	@Size(min=1, max=30, message="1字以上30字以内で入力してください")
	private String name;
	/** 内容 */
	@NotBlank(message="投稿内容は必須です")
	@Size(min=1, max=140, message="1字以上140字以内で入力してください")
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
	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}
}
