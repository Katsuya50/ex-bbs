package com.example.domain;

public class Like {
	private Integer articleId;
	private Integer likeCount;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	@Override
	public String toString() {
		return "Like [articleId=" + articleId + ", likeCount=" + likeCount + "]";
	}
}
