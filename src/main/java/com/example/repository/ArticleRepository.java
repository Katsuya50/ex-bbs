package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルとcommentsテーブルを操作するリポジトリ.
 * commentsテーブルを操作するのはテーブル結合するときのみ
 * 
 * @author katsuya.fujishima
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**	記事テーブル */
	private static final String TABLE_NAME_1 = "articles";
	/**	コメントテーブル */
	private static final String TABLE_NAME_2 = "comments";

	/**	コメントをリスト化、記事ドメインに全てのプロパティを格納してリストとして返すエクストラクター */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RS_EXTRACTOR = (rs) -> {
//		Article article = null;
//		Comment comment = null;
		List<Comment> commentList = null;
		List<Article> articleList = new ArrayList<>();
		int num = 0;
		while(rs.next()) {
			int id = rs.getInt("id");
			if(num != id) {
				Article article = new Article();
				article.setId(id);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			}
			Comment comment = new Comment();
			comment.setId(rs.getInt("com_id"));
			comment.setName(rs.getString("com_name"));
			comment.setContent(rs.getString("com_content"));
			comment.setArticleId(rs.getInt("article_id"));
			if(comment.getArticleId() == 0) {
				commentList = null;
			}else {
				commentList.add(comment);
			}
			num = id;
		}
		return articleList;
	};
	
	/**
	 * 記事の全件検索メソッド.
	 * 記事テーブルとコメントテーブルを結合して記事をリスト化して返す
	 * 記事とコメントは新しい順に上から並べる
	 * 
	 * @return こめんとを紐づけた記事リスト
	 */
	public List<Article> findAll() {
		String sql = "SELECT a.id as id, a.name as name, a.content as content, "
					+ "c.id as com_id, c.name as com_name, c.content as com_content, c.article_id as article_id "
					+ "FROM " + TABLE_NAME_1 + " as a "
					+ "LEFT OUTER JOIN " + TABLE_NAME_2 + " as c "
					+ "ON a.id = c.article_id ORDER BY a.id DESC, c.id DESC";
		List<Article> articleList = template.query(sql, ARTICLE_RS_EXTRACTOR);
		return articleList;
	}
	
	/**
	 * 記事を挿入するメソッド.
	 * 
	 * @param article 記事
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO " + TABLE_NAME_1 + " (name, content) VALUES (:name, :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		template.update(sql, param);
	}

}
