package com.example.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME_1 = "articles";
	private static final String TABLE_NAME_2 = "comments";

	private static final ResultSetExtractor<Article> ARTICLE_RS_EXTRACTOR = (rs) -> {
		Article article = null;
		Comment comment = null;
		List<Comment> commentList = null;
		int num = 0;
		while(rs.next()) {
			if(num != rs.getInt("id")) {
				article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				
			}
			comment = new Comment();
			comment.setId(rs.getInt("com_id"));
			comment.setName(rs.getString("com_name"));
			comment.setContent(rs.getString("com_content"));
			comment.setArticleId(rs.getInt("article_id"));
			commentList.add(comment);
		}
		return article;
	};
	
	public List<Article> findAll() {
		String sql = "SELECT a.id as id, a.name as name, a.content as content, "
					+ "c.id as com_id, c.name as com_name, c.content as com_content, c.article_id as article_id "
					+ "FROM " + TABLE_NAME_1 + " as a, " + TABLE_NAME_2 + " as c "
					+ "WHERE a.id = c.article_id ORDER BY a.id DESC, c.id";
		
		
	}

}
