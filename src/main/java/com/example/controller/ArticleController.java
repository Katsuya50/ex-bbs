package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

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
	private ArticleRepository repository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	/**
	 * 既に投稿された内容を含んだ掲示板画面にフォワードするメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @return 掲示板画面
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = repository.findAll();
		model.addAttribute("articleList", articleList);
		return "bbs";
	}
	
	/**
	 * 記事の投稿をするメソッド.
	 * 
	 * @param articleForm ブラウザから受け取ったフォームクラス
	 * @param model リクエストスコープ
	 * @return indexメソッド
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm articleForm, Model model) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		repository.insert(article);
		model.addAttribute("insertedMessage", "記事の投稿が完了しました。");
		return index(model);
	}

}
