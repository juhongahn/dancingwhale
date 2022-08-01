package com.k1ng.doinggajigaji.likes;

import com.k1ng.doinggajigaji.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/likes/new")
    public void saveLikes(@Login String userEmail, Long postId, BindingResult br) {
        likesService.newLikes(userEmail, postId);
    }
}
