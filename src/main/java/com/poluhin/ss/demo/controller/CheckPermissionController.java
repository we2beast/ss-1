package com.poluhin.ss.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CheckPermissionController {

    @GetMapping("user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> user() {
        return ok("User permission");
    }

    @GetMapping("admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> admin() {
        return ok("Admin permission");
    }

}
