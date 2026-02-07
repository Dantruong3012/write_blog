package com.dantruong.writeblog.service;

import com.dantruong.writeblog.Repository.BlogRepo;
import com.dantruong.writeblog.Repository.UserRepo;
import com.dantruong.writeblog.entity.Blog;
import com.dantruong.writeblog.entity.User;
import com.dantruong.writeblog.entity.dto.BlogDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppService {
    private final UserRepo userRepo;
    private final BlogRepo blogRepo;

    public AppService(UserRepo userRepo, BlogRepo blogRepo) {
        this.userRepo = userRepo;
        this.blogRepo = blogRepo;
    }

    @Transactional
    public  String  createBlog(BlogDto dto){
        User user;
        Optional<User> existingUser = userRepo.findByUserName(dto.getUserName());
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            user = new User();
            user.setUserName(dto.getUserName());
            user = userRepo.save(user);
        }

        Blog blog = new Blog();

        blog.setContent(dto.getContent());
        blog.setUser(user);
        blog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        blog.setImageFile(dto.getFile());
        blogRepo.save(blog);
        dto.setUserID(user.getId());
        return "Đã lưu thành công";
    }

    public List<BlogDto> showList(Integer userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng này"));

        List<Blog> blogList = blogRepo.findBlogsByUserId(userId);
            user.setBlogs(blogList);
        List<BlogDto> dtoList = new ArrayList<>();

        for (Blog blogs : blogList){
            BlogDto dto = new BlogDto();
            dto.setUserID(user.getId());
            dto.setUserName(user.getUserName());
            dto.setBlogId(blogs.getId());
            dto.setContent(blogs.getContent());
            dto.setCreatedTime(blogs.getCreatedTime());
            dto.setFile(blogs.getImageFile());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public void  updateBlog(Integer blogId, String content){
        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new RuntimeException("Không thể update blog do không tìm thấy blog trong database"));
        if (content != null && !content.trim().isEmpty()){
            blog.setContent(content);
            blog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        }
        blogRepo.save(blog);
    }

    public  void deleteBlog(Integer blogId){
        blogRepo.deleteById(blogId);
    }
}
