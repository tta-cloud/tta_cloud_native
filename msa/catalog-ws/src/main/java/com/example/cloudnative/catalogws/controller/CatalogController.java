package com.example.cloudnative.catalogws.controller;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.model.CatalogResponseModel;
import com.example.cloudnative.catalogws.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalogs-ms")
public class CatalogController {
    @Autowired
    private Environment env;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/")
    public String health() {
        return "Hi, there. I'm a Catalog microservice";
    }

    @GetMapping(value="/catalogs")
    public ResponseEntity<List<CatalogResponseModel>> getCatalogs() {
        Iterable<CatalogEntity> orderList = catalogService.getAllCatalogs();

        List<CatalogResponseModel> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, CatalogResponseModel.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
