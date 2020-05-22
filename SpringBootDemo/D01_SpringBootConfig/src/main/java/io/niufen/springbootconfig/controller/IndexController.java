package io.niufen.springbootconfig.controller;

import io.niufen.springbootconfig.property.UserDefinedProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author niufen
 */
@RestController
public class IndexController {

	@Autowired
	private UserDefinedProperties userDefinedProperties;

	@Value("${spring.profiles.active}")
	private String springProfilesActive;

	@Value("${server.port}")
	private Integer serverPort;

	@RequestMapping("/")
	String index() {
		return "HelloWorld";
	}

	@RequestMapping("/value")
	String value() {
		return "springProfilesActive:"+springProfilesActive +" ;serverPort:"+serverPort;
	}

	@RequestMapping("/spring")
	String spring() {
		return userDefinedProperties.toString();
	}
}
