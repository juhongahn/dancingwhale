package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.dto.MemberFormDto;
import com.k1ng.doinggajigaji.dto.PostFormDto;
import com.k1ng.doinggajigaji.entity.Member;
import com.k1ng.doinggajigaji.service.MemberService;
import com.k1ng.doinggajigaji.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws Exception {
        memberService.join(Member.createMember(new MemberFormDto("안주홍", "12345678",
                "lock5028@naver.com", "scarlet"), passwordEncoder));


        String fileName = "testCustomerUpload";
        String contentType = "xls";
        String filePath = "src/test/resources/excel/testCustomerUpload.xls";

        for (int i = 0; i < 8; i++) {
            PostFormDto postFormDto = new PostFormDto();
            postFormDto.setOnlyMe(true);
            postFormDto.setId((long) i);
            postFormDto.setEmail("lock5028@naver.com");
            postFormDto.setPostImgDtoList(new ArrayList<>());
            postFormDto.setDescription("test" + i);
            postFormDto.setPostImgIds(new ArrayList<>());

            List<MultipartFile> multipartFileList = new ArrayList<>();

            postService.savePost(postFormDto, "lock5028@naver.com", multipartFileList);
        }
    }


}
