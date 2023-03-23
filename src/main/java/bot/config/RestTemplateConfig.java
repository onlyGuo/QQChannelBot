package bot.config;

import bot.interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP请求客户端配置类
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    TokenInterceptor tokenInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(tokenInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
