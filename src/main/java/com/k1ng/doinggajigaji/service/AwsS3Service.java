package com.k1ng.doinggajigaji.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    private static final String bucketName = "dancingwhalebucket";
    private final AmazonS3 amazonS3;

}
