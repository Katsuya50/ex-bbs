package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Like;

/**
 * 掲示板アプリのいいね機能の非同期処理用コントローラークラス.
 * 
 * @author katsuya.fujishima
 *
 */
@RestController
@RequestMapping("/like")
public class ArticleLikeController {

	@Autowired
	private ServletContext application;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String, Integer> index(Integer articleId) {
		List<Like> likeCountList = (List<Like>) application.getAttribute("likeCountList");
		if(likeCountList == null) {
			likeCountList = new ArrayList<>();
		}
		int likeCount = 0;
		for (Like like: likeCountList) {
			if (like.getArticleId() == articleId) {
				likeCount = like.getLikeCount() + 1;
				System.out.println(likeCount);
				like.setLikeCount(likeCount);
				likeCountList.add(likeCountList.indexOf(like), like);
				application.setAttribute("likeCountList", likeCountList);
				break;
			}
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("likeCount", likeCount);
		return map;
	}

}
