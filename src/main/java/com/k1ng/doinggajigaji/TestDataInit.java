package com.k1ng.doinggajigaji;

import com.k1ng.doinggajigaji.constant.Role;
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

//    @PostConstruct
//    public void init() throws Exception {
//        MemberFormDto adminMemberDto = new MemberFormDto("안주홍", "12345678",
//                "lock5028@naver.com", "scarlet");
//        Member adminMember = Member.createMember(adminMemberDto, passwordEncoder);
//        adminMember.setRole(Role.ADMIN);
//        memberService.join(adminMember);
//
//
//        String fileName = "testCustomerUpload";
//        String contentType = "xls";
//        String filePath = "src/test/resources/excel/testCustomerUpload.xls";
//
//        for (int i = 0; i < 8; i++) {
//            PostFormDto postFormDto = new PostFormDto();
//            postFormDto.setOnlyMe(true);
//            postFormDto.setId((long) i);
//            postFormDto.setPostImgDtoList(new ArrayList<>());
//            postFormDto.setDescription("test" + i);
//            postFormDto.setPostImgIds(new ArrayList<>());
//
//            List<MultipartFile> multipartFileList = new ArrayList<>();
//
//            postService.savePost(postFormDto, "lock5028@naver.com", multipartFileList);
//        }
//    }


}
