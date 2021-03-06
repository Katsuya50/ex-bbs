package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.domain.Like;
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
	
	@Autowired
	private ServletContext application;
	
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert-article")
	public String insertArticle(@Validated ArticleForm articleForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return index(model);
		}
		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);
		model.addAttribute("insertedArticleMessage", "記事の投稿が完了しました。");
		List<Article> articleList = articleRepository.findAll();
		List<Like> likeCountList = (List<Like>) application.getAttribute("likeCountList");
		if(likeCountList == null) {
			likeCountList = new ArrayList<>();
		}
		Like like = new Like();
		like.setArticleId(articleList.get(articleList.size()-1).getId());
		like.setLikeCount(0);
		System.out.println(like);
		likeCountList.add(like);
		application.setAttribute("likeCountList", likeCountList);
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
	public String insertComment(@Validated CommentForm commentForm, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("articleId", Integer.parseInt(commentForm.getArticleId()));
			return index(model);
		}
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
	 * 記事とコメントを一括削除するメソッド.
	 * 
	 * @param articleId 記事id
	 * @return toIndexメソッドへリダイレクト
	 */
	@RequestMapping("/delete-article-and-comment")
	public String deleteArticleAndComment(String articleId) {
		articleRepository.deleteArticleAndCommentByArticleId(Integer.parseInt(articleId));
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
