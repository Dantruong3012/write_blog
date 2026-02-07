package com.dantruong.writeblog.Repository;

import com.dantruong.writeblog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepo extends JpaRepository<Blog, Integer> {
    List<Blog> findBlogsByUserId(Integer userId);
}
