package com.blog.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.blog.repository.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PostTest {
	
	@Autowired
	PostRepository postRepository;
	
	@Test
	public void savePostTest() throws Exception {
		
		Post post = new Post();
		
		post.setId(1);
		post.setTitle("Hola");
		post.setImage("hola.jpg");
		post.setCategory("Politics");
		post.setCreator("User");
		post.setContent("Contenido");
		
		postRepository.save(post);
		
		Post testPost = postRepository.getOne(1);
		
		assertEquals(post.getId(), testPost.getId());
	}
	
	
}
