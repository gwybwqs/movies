package com.liu.movices.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.movices.entity.Relation;
import com.liu.movices.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/relation")
public class RelationController {
    @Autowired
    RelationService relationService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add (@RequestBody Relation relation) {
        return relationService.add(relation);
    }
}
