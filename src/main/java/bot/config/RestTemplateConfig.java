package bot.config;

import bot.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP请求客户端配置类
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    TokenInterceptor tokenInterceptor;

    @Resource
    ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        //HTTP请求拦截器
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(tokenInterceptor);
        //报文信息转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        messageConverters.add(messageConverter);
        //RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
