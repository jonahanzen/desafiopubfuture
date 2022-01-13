package br.com.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuracao para o ModelMapper
 * A classe torna disponivel uma nova instancia de ModelMapper
 * Possibilitando que ele seja injetado,
 * 
 * ex: {@code @Autowired ModelMapper mapper;}
 *
 */
@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
