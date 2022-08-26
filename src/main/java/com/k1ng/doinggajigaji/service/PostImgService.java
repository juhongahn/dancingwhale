package com.k1ng.doinggajigaji.service;

import com.k1ng.doinggajigaji.entity.PostImg;
import com.k1ng.doinggajigaji.repository.PostImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostImgService {

    private final PostImgRepository postImgRepository;

    private final FileServiceS3 fileServiceImpl;

    public void savePostImg(PostImg postImg, MultipartFile postImgFile) throws IOException {

        UUID uuid = UUID.randomUUID();

        // 원본 이름
        String oriImgName = postImgFile.getOriginalFilename();
        String imgUrl = "";

        // 확장자 추출 & 저장할 파일 이름 만들기.
        String extension = oriImgName.substring(oriImgName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgUrl = fileServiceImpl.uploadFile(postImgFile, savedFileName);
        }
        postImg.updatePostImg(oriImgName, savedFileName, imgUrl);
        postImgRepository.save(postImg);
    }


    public void updatePostImg(Long postImgId, MultipartFile postImgFile) throws IOException {

        if (!postImgFile.isEmpty()) {
            PostImg savedPostImg = postImgRepository.findById(postImgId)
                    .orElseThrow(EntityNotFoundException::new);

            // 기존 이미지를 삭제
            if (!StringUtils.isEmpty(savedPostImg.getImgName())) {
                fileServiceImpl.deleteFile(savedPostImg.getImgName());
            }
            String oriImgName = postImgFile.getOriginalFilename();
            String imgName = fileServiceImpl.uploadFile(postImgFile, oriImgName);
            savedPostImg.updatePostImg(oriImgName, imgName, imgName);
        }
    }
}
