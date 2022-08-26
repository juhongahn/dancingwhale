package com.k1ng.doinggajigaji.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceS3 {

    private static final String bucketName = "dancingwhalebucket";
    private final AmazonS3 s3;

    public String uploadFile(MultipartFile multipartFile, String savedFileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());

        PutObjectRequest request = new PutObjectRequest(bucketName, savedFileName, multipartFile.getInputStream(), metadata);
        s3.putObject(request);
        return s3.getUrl(bucketName, savedFileName).toString();

    }

    public void deleteFile(String filename) {
        s3.deleteObject(bucketName, filename);
    }
}
