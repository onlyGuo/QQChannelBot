package bot.config;

import bot.interceptor.HeaderInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * RestTemplate配置类
 *
 * @author 梁振辉
 * @since 2023-05-26 11:12:40
 */
@Configuration
public class RestTemplateConfig {

    /**
     * HTTP Header拦截器
     */
    @Resource
    HeaderInterceptor headerInterceptor;

    @Bean
    RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(headerInterceptor);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
