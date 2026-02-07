package com.dantruong.writeblog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude // ngan khong cho lombok goi tostring cua user
    private User user;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Column(name = "file_name")
    private String imageFile;
}
