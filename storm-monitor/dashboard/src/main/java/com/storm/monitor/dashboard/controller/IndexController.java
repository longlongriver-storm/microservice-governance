/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by lixin
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String showIndexPage(Map<String, Object> model) {
        return "index";
    }


}
