package io.niufen.springboot.thymeleaf.controller;

import io.niufen.springboot.thymeleaf.model.UserBO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("basic")
public class ExampleController {

    @RequestMapping("/thText")
    public String string(ModelMap map) {
        String text = "变量表达式即 OGNL 表达式或 Spring EL 表达式(在 Spring 术语中也叫 model attributes)。如下所示：\n" +
                "${session.user.name}\n" +
                "它们将以HTML标签的一个属性来表示：\n" +
                "\n\n";
        map.addAttribute("modelAttrValue", text);
        map.addAttribute("strSplit", ":thText 可以在HTML标签进行字符串拼接");
        return "basic/thText";
    }

    @RequestMapping("/thIfUnless")
    public String thIfAndUnless(ModelMap map) {
        map.addAttribute("flag", "yes");
        return "basic/thIfAndUnless";
    }

    @RequestMapping("/thList")
    public String list(ModelMap map) {
        map.addAttribute("users", getUserList());
        return "basic/thList";
    }

    @RequestMapping("/url")
    public String url(ModelMap map) {
        map.addAttribute("type", "link");
        map.addAttribute("pageId", "springcloud/2017/09/11/");
        map.addAttribute("img", "http://www.ityouknow.com/assets/images/neo.jpg");
        return "basic/url";
    }

    @RequestMapping("/thEq")
    public String eq(ModelMap map) {
        map.addAttribute("name", "neo");
        map.addAttribute("age", 30);
        map.addAttribute("flag", "yes");
        return "basic/thEq";
    }

    @RequestMapping("/thSwitch")
    public String switchcase(ModelMap map) {
        map.addAttribute("sex", "woman");
        return "basic/thSwitch";
    }

    private List<UserBO> getUserList(){
        List<UserBO> list=new ArrayList<UserBO>();
        UserBO user1=new UserBO("大牛",12,"123456");
        UserBO user2=new UserBO("小牛",6,"123563");
        UserBO user3=new UserBO("纯洁的微笑",66,"666666");
        list.add(user1);
        list.add(user2);
        list.add(user3);
        return  list;
    }

}
