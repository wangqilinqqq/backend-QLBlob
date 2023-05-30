package org.qilin.controller;

import org.qilin.domain.ResponseResult;
import org.qilin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryList")
    public ResponseResult categoryList() {
        return categoryService.categoryList();
    }

}
