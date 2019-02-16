/**
 * 
 */
package com.cc.image.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cc.common.file.config.FileConfig;
import com.cc.common.file.strategy.FileStrategy;
import com.cc.common.file.strategy.impl.NativeFileStrategy;

/**
 * @author Administrator
 *
 */
@Configuration
public class ImageConfig {

	@Bean
	public FileStrategy fileStrategy(FileConfig fileConfig){
		fileConfig.setAllowedExt("jpg,png,jpeg,gif,bmp");
		FileStrategy fileStrategy = new NativeFileStrategy();
		fileStrategy.setFileConfig(fileConfig);
		return fileStrategy;
	}
}
