package com.k1ng.doinggajigaji.controller;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import com.k1ng.doinggajigaji.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
@Slf4j
public class LikesController {

    private final LikesService likesService;

    @ResponseBody
    @PostMapping("/new")
    public void saveLikes(@RequestParam Long postId, @Login String userEmail) {
        likesService.newLikes(userEmail, postId);
    }
}
