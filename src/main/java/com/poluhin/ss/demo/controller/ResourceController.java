package com.poluhin.ss.demo.controller;

import com.poluhin.ss.demo.domain.model.ResourceObject;
import com.poluhin.ss.demo.service.ResourceObjectService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {

    private final ResourceObjectService service;
    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();

    @PostMapping
    public ResponseEntity<Integer> createResourceObject(@RequestBody ResourceObject object) {
        val result = service.save(object);
        return ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceObject> getResourceObject(@PathVariable Integer id) {
        return ok(service.get(id));
    }

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ok(SecurityContextHolder.getContext().getAuthentication().toString());
    }

}
