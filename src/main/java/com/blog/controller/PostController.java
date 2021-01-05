package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.model.Post;
import com.blog.service.PostService;

@Controller
public class PostController {

	private PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@GetMapping({"/","/posts"})
	public String viewHome(Model model) {
		model.addAttribute("listPosts", postService.getAllPosts());
		return "home";
	}
	
	@GetMapping("/post/{id}")
	public String viewPost(@PathVariable (value = "id") int id, Model model) {
		Boolean hasImage;
		model.addAttribute("post", postService.getPost(id));
		if(postService.getPost(id).getImage().isEmpty()) {
			hasImage = false;
		}else {
			hasImage = true;
		}
		model.addAttribute("has_image", hasImage);
		return "post_description";
	}
	
	@GetMapping("/post/new")
	public String showNewPostForm(Model model) {
		Post post = new Post();
		model.addAttribute("post", post);
		return "new_post";
	}
	
	@PostMapping("/savePost")
	public String savePost(@ModelAttribute("post") Post post, @RequestParam("fileImage") MultipartFile multipartFile,Model model) throws IOException {
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		post.setImage(fileName);
		
		Post savedPost = postService.savePost(post);
		
		String uploadDir = "./post-images/" + savedPost.getId();
		
		Path uploadPath = Paths.get(uploadDir);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			throw new IOException("Could not save uploaded file: " + fileName);
		}
		
		
		model.addAttribute("msgRegister", "Post \""+ post.getTitle() + "\" has been registered succesfully!");
		model.addAttribute("listPosts", postService.getAllPosts());
		return "home";
	}
	
	@GetMapping("/post/update/{id}")
	public String showPostUpdateForm(@PathVariable(value = "id") int id, Model model) {
		
		Post post = postService.getPost(id);
		
		model.addAttribute("post", post);
		
		return "update_post";
	}

	
	@GetMapping("/post/delete/{id}")
	public String deletePost(@PathVariable(value = "id") int id, Model model) {
		Post post = postService.getPost(id);
		postService.deletePost(id);
		model.addAttribute("msgDeleted", "Post \""+ post.getTitle() + "\" has been deleted succesfully!");
		model.addAttribute("listPosts", postService.getAllPosts());
		return "home";
	}
	
	
}
