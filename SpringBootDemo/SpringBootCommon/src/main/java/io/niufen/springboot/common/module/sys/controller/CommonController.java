package io.niufen.springboot.common.module.sys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haijun.zhang
 * @date 2020/5/14
 * @time 21:26
 */
@RestController
@RequestMapping("/")
public class CommonController {

    @RequestMapping("index")
    public String index(){
        return "HelloWorld";
    }
}
