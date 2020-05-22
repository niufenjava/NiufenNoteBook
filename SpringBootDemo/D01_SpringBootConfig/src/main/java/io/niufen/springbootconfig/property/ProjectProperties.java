package io.niufen.springbootconfig.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author niufen
 */
@Data
@Component
@PropertySource("classpath:project.properties")
@Configuration
@ConfigurationProperties(prefix="project")
public class ProjectProperties {

	private String name;

	private Integer level;

}
