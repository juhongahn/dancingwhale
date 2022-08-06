package com.k1ng.doinggajigaji.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostEditFormDto {

    private String description;

    private List<MultipartFile> itemImgFileList;

}
