package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.entity.PostImg;
import com.k1ng.doinggajigaji.repository.PostImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImgService {

    @Value("${upload.postImgLocation}")
    private String postImgLocation;

    private final PostImgRepository postImgRepository;
    
    private final FileService fileService;

    public void savePostImg(PostImg postImg, MultipartFile postImgFile) throws IOException {
        
        // 원본 이름
        String oriImgName = postImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(postImgLocation, oriImgName,
                    postImgFile.getBytes());

            imgUrl = "/images/post/" + imgName;
        }

        postImg.updatePostImg(oriImgName, imgName, imgUrl);
        postImgRepository.save(postImg);
    }

    public void updatePostImg(Long postImgId, MultipartFile postImgFile) throws IOException {
        if (!postImgFile.isEmpty()) {
            PostImg savedPostImg = postImgRepository.findById(postImgId)
                    .orElseThrow(EntityNotFoundException::new);

            if (!StringUtils.isEmpty(savedPostImg.getImgName())) {
                fileService.deleteFile(postImgLocation+"/"+
                        savedPostImg.getImgName());
            }

            String oriImgName = postImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(postImgLocation, oriImgName, postImgFile.getBytes());
            String imgUrl = "/images/post/" + imgName;
            savedPostImg.updatePostImg(oriImgName, imgName, imgUrl);
        }


    }
}
