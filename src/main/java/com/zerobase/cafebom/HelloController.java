package com.zerobase.cafebom;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "hello-controller", description = "Test")
@RestController
@RequiredArgsConstructor
public class HelloController {

    // yesun-23.09.05
    @GetMapping("/hello")
    public String hello(
        @RequestHeader(AUTHORIZATION) String token
    ) {
        return "hello";
    }
}
