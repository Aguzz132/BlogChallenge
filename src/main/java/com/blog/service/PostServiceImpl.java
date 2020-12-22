package com.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.model.Post;
import com.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository repository;
	
	@Override
	public List<Post> getAllPosts() {
		return repository.findAll();
	}

	@Override
	public Post savePost(Post post) {
		return this.repository.save(post);
		
	}

	@Override
	public Post getPost(int id) {
		Optional<Post> optional = repository.findById(id);
		Post post = null;
		
		if(optional.isPresent()) {
			post = optional.get();
		}else {
			throw new RuntimeException("Post not found id");
		}
		
		return post;
	}

	@Override
	public void deletePost(int id) {
		this.repository.deleteById(id);
		
	}

	
	
}
