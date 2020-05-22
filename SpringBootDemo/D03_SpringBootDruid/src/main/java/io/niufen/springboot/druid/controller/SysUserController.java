package io.niufen.springboot.druid.controller;

import io.niufen.springboot.druid.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/batch/save")
    public void batchSave() {
        for (int i = 0; i < 200; i++) {
            sysUserService.batchSave();
        }

    }

    @RequestMapping("/slow/like")
    public void slowSql(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        session.setAttribute("sessionId", "sessionId");

        sysUserService.slowSql();
    }

    @RequestMapping("/multi")
    public void multi(){
        sysUserService.multi();
    }
}
