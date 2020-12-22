package com.blog.service;

import java.util.List;

import com.blog.model.Post;

public interface PostService {

	List<Post> getAllPosts();
	Post savePost(Post post);
	Post getPost(int id);
	public void deletePost(int id);
}
