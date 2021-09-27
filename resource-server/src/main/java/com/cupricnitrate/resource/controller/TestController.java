package com.cupricnitrate.resource.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 硝酸铜
 * @date 2021/9/27
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {


    @PreAuthorize("hasAnyAuthority('api:hello')")
    @GetMapping(value = "/hello")
    public String hello(){
        return "hello world!";
    }
}
