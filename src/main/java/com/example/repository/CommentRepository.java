package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**	テーブル名 */
	private static final String TABLE_NAME = "comments";
	
	/**
	 * コメントの情報を挿入するメソッド.
	 * 
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		String sql = "INSERT INTO " + TABLE_NAME + " (name, content, article_id) "
					+ "VALUES (:name, :content, :articleId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		template.update(sql, param);
	}
	
	/**
	 * コメントを削除するメソッド.
	 * 
	 * @param id id
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
