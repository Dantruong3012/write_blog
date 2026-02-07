package com.dantruong.writeblog.entity.dto;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class BlogDto {
    private  Integer userID;
    private String userName;
    private Integer blogId;
    private String content;
    private Timestamp createdTime;
    private String file;
}
