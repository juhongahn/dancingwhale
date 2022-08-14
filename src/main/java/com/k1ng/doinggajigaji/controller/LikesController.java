package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import com.k1ng.doinggajigaji.service.LikesService;
import com.k1ng.doinggajigaji.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikesController {

    private final LikesService likesService;

    
    // 좋아요 등록
    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<String> saveLikes(@RequestParam Long postId, Principal principal) {

        if (likesService.checkDuplicatedLikes(principal.getName(), postId)) {
            return new ResponseEntity<>("\"이미 참여하셨습니다.\"", HttpStatus.BAD_REQUEST);
        }
        likesService.newLikes(principal.getName(), postId);
        return new ResponseEntity<>("\"등록됐습니다.\"", HttpStatus.OK);
    }
}
