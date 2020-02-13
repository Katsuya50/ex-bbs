package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * コメントの情報を受け取るフォームクラス.
 * 
 * @author katsuya.fujishima
 *
 */
public class CommentForm {
	
	/** 記事ID */
	private String articleId;
	/** 名前 */
	@NotBlank(message="名前は必須です")
	@Size(min=1, max=30, message="1字以上30字以内で入力してください")
	private String name;
	/** 内容 */
	@NotBlank(message="コメントは必須です")
	@Size(min=1, max=140, message="1字以上140字以内で入力してください")
	private String content;

	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
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
	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}
}
