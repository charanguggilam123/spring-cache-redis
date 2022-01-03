package com.example.demo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MyEntity;
import com.example.demo.entity.RedisRepo;

@RestController
public class MyController {
	
	@Autowired
	private RedisRepo repo;
	
	private final Logger log=LoggerFactory.getLogger(MyController.class);
	@GetMapping("/get/{id}")
	@Cacheable(cacheNames = "user",key = "#id")
	public MyEntity testGet(HttpServletRequest req,@PathVariable String id) {
		log.info("in get call");
		Optional<MyEntity> obj=repo.findById(Integer.parseInt(id));
		System.out.println(obj.get().getName());
		return obj.get();
	}
	
	@PostMapping("/post")
	public MyEntity testPost(HttpServletRequest req,@RequestBody MyEntity entity) {
		repo.save(entity);
		return entity;
	}
	
	@PatchMapping("/update")
	@CachePut(cacheNames = "user",key = "#entity.id")
	public MyEntity testPatch(HttpServletRequest req,@RequestBody MyEntity entity) {
		Optional<MyEntity> obj=repo.findById(entity.getId());
		if(obj.isPresent())
			repo.save(entity);
		else
			return null;
		return entity;
	}
	
	@DeleteMapping("/delete/{id}")
	@CacheEvict(cacheNames = "user",key = "#id")
	public String testDelet(HttpServletRequest req,@PathVariable String id) {
		log.info("in delete call");
		int idInt=Integer.parseInt(id);
		Optional<MyEntity> obj=repo.findById(idInt);
		if(obj.isPresent())
			repo.deleteById(idInt);
		else
			return "Objec not found!!!!!!";
		return "successfully deleted obj with id: "+id;
	}

}
