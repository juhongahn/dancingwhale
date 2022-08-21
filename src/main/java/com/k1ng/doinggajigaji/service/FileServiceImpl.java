package com.k1ng.doinggajigaji.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileServiceImpl {



    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();

        // 확장자 추출
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 파일을쓴다.
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }
}
