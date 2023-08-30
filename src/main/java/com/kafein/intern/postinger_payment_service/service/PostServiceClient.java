package com.kafein.intern.postinger_payment_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "postinger-post-service", url = "http://localhost:8080")
public interface PostServiceClient {
    @GetMapping(value = "/posts/get-userId-by-postId")
    public Long getUserIdByPostId(@RequestParam(name = "postId") String postId);
}
