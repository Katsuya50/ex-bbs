package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 掲示板アプリのコントローラークラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
@Transactional
@RequestMapping("/")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * 既に投稿された内容を含んだ掲示板画面にフォワードするメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @return 掲示板画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);
		return "bbs";
	}
	
	/**
	 * 記事の投稿をするメソッド.
	 * 
	 * @param articleForm ブラウザから受け取ったフォームクラス
	 * @param model リクエストスコープ
	 * @return toIndexメソッドへリダイレクト
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm articleForm, Model model) {
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		model.addAttribute("insertedArticleMessage", "記事の投稿が完了しました。");
		return "redirect:/to-index";
	}
	
	/**
	 * コメントを投稿するメソッド.
	 * 
	 * @param commentForm コメントを受けとったフォームクラス
	 * @param model リクエストスコープ
	 * @return toIndexメソッドへリダイレクト
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm commentForm, Model model) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
		commentRepository.insert(comment);
		model.addAttribute("insertedCommentMessage", "コメントの投稿が完了しました。");
		return "redirect:/to-index";
	}
	
	/**
	 * 記事とコメントを削除するメソッド.
	 * 
	 * @param articleId 記事id
	 * @return toIndexメソッドへリダイレクト
	 */
	@RequestMapping("/delete-article")
	public String deleteArticle(String articleId) {
		commentRepository.deleteByArticleId(Integer.parseInt(articleId));
		articleRepository.deleteById(Integer.parseInt(articleId));
		return "redirect:/to-index";
	}
	
	/**
	 * リダイレクト用indexメソッドを呼ぶメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @return indexメソッド
	 */
	@RequestMapping("/to-index")
	public String toIndex(Model model) {
		return index(model);
	}

}
