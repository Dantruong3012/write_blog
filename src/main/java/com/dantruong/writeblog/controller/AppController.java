package com.dantruong.writeblog.controller;

import com.dantruong.writeblog.entity.dto.BlogDto;
import com.dantruong.writeblog.service.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AppController {
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/blog-home")
    public String showForm(Model model){
        model.addAttribute("blogDto", new BlogDto());
        return "index";
    }

    @PostMapping("/create-blog")
    public String createBlog(@ModelAttribute("blogDto") BlogDto dto, @RequestParam("imageFile") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get("imgFile", fileName);
        Files.write(fileNameAndPath, file.getBytes());
        dto.setFile(fileName);
        appService.createBlog(dto);
        return "redirect:/view?userId=" + dto.getUserID();
    }

    @GetMapping("/view")
    public String showBlog(Model model, @RequestParam("userId") Integer userId){
        model.addAttribute("blogList", appService.showList(userId));
        model.addAttribute("currentUserId", userId);
        return "view";
    }

    @PostMapping("/update")
    public String updateBlog(@RequestParam("blogId") Integer blogId,
                             @RequestParam("userId") Integer userId,
                             @RequestParam("content") String content,
    RedirectAttributes redirectAttributes){

        appService.updateBlog(blogId, content);
        redirectAttributes.addFlashAttribute("successMessage", "Đã cập nhật bài viết thành công!");
        return "redirect:/view?userId=" + userId;
    }

    @GetMapping("/delete")
    public String deleteBlog(@RequestParam("blogId") Integer blogId,
                             @RequestParam("userId") Integer userId,RedirectAttributes redirectAttributes) {
        appService.deleteBlog(blogId);
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa bài viết thành công!");
        return "redirect:/view?userId=" + userId;
    }
}