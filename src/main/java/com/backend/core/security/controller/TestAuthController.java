package com.backend.core.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@PreAuthorize("denyAll()")
public class TestAuthController {

    @GetMapping("/get")
    //@PreAuthorize("hasAuthority('READ')")
    public String hello(){
        return "Hello world get";
    }

    @PostMapping("/post")
    //@PreAuthorize("hasAuthority('CREATE') or hasAuthority('READ')")
    public String helloPost(){
        return "Hello world post";
    }
    @PutMapping("/put")
    //@PreAuthorize("hasAuthority('READ')")
    public String helloPut(){
        return "Hello world put";
    }
    @DeleteMapping("/delete")
    //@PreAuthorize("hasAuthority('READ')")
    public String helloDelete(){
        return "Hello world delete";
    }

    @PatchMapping("/patch")
    //@PreAuthorize("hasAuthority('REFACTOR')")
    public String helloPatch(){
        return "Hello world patch";
    }
}
