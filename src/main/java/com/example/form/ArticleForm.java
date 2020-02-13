package com.example.form;

/**
 * 記事の情報を受け取るフォームクラス.
 * 
 * @author katsuya.fujishima
 *
 */
public class ArticleForm {
	
	
	/** 名前 */
	private String name;
	/** 内容 */
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
