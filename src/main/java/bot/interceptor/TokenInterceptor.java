package bot.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * HTTP请求拦截器
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    @Value("Bot ${bot.id}.${bot.token}")
    String botToken;

    @Value("Bearer ${openai.key}")
    String openaiKey;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String host = request.getURI().getHost();
        if ("sandbox.api.sgroup.qq.com".equals(host)) {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", botToken);
        } else if ("api.openai.com".equals(host)) {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", openaiKey);
        }
        return execution.execute(request, body);
    }

}
