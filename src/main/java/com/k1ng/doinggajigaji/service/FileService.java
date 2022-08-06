package com.k1ng.doinggajigaji.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {


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

    public void deleteFile(String filePath) {

        //삭제할 파일을 불러온다.
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
