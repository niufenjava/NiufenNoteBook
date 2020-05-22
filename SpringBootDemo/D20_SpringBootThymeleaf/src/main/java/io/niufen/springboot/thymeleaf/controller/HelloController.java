package io.niufen.springboot.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haijun.zhang
 * @date 2020/5/21
 * @time 21:25
 */
@Controller
public class HelloController {

    @RequestMapping("/hi")
    public String hi(ModelMap map){
        map.addAttribute("hello","Hello Thymeleaf !!!");
        return "basic/hello";
    }


}
