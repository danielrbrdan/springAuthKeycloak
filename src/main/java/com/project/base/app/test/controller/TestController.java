package com.project.base.app.test.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.base.app.test.entity.Test;
import com.project.base.app.test.service.TestService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Test> findAll() {
        return testService.findAll();
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Test save(@RequestBody Test test) {
        return testService.save(test);
    }
}
