package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
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
	
	/**
	 * 既に投稿された内容を含んだ掲示板画面をフォワードするメソッド.
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

}
