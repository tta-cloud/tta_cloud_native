package com.example.cloudnative.catalogws.service;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    CatalogRepository repository;

    Environment env;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository,
                              Environment env) {
        this.repository = repository;
        this.env = env;
    }

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return repository.findAll();
    }
}
