package io.niufen.springbootconfig.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ConfigurationProperties 指明配置文件前缀
 * @author niufen
 */
@Data
@Component
@ConfigurationProperties(prefix="oos.ali")
public class OOSAliConfig {

	private String accessKey;

	private String accessKeySecret;

	private String endPoint;

	private String bucketName;

}
