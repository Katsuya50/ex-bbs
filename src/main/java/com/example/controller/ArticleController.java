package com.example.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.ArticleRepository;

@Controller
@Transactional
@RequestMapping("/")
public class ArticleController {
	
	@Autowired
	private ArticleRepository repository;
	
	@Autowired
	private ServletContext application;
	
	

}
