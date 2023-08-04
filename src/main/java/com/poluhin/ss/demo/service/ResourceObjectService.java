package com.poluhin.ss.demo.service;

import com.poluhin.ss.demo.domain.entity.ResourceObjectEntity;
import com.poluhin.ss.demo.domain.model.ResourceObject;
import com.poluhin.ss.demo.repository.ResourceObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceObjectService {

    private final ResourceObjectRepository repository;

    public Integer save(ResourceObject resourceObject) {
        return repository.save(new ResourceObjectEntity(
                resourceObject.getId(), resourceObject.getValue(),
                resourceObject.getPath())).getId();

    }

    public ResourceObject get(int id) {
        return repository.findById(id)
                .map(r -> new ResourceObject(r.getId(), r.getValue(), r.getPath()))
                .orElse(null);
    }

}
