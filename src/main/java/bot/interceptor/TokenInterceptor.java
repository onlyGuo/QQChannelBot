package bot.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * HTTP请求拦截器
 */
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {

    /**
     * 接口域名
     */
    @Value("${bot.api}")
    String botApi;
    /**
     * BotAppID
     */
    @Value("${bot.id}")
    String botId;
    /**
     * 机器人令牌
     */
    @Value("${bot.token}")
    String botToken;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI uri = request.getURI();
        //接口host
        String apiHost = URI.create(botApi).getHost();
        //当前请求host
        String host = uri.getHost();
        if (host.equals(apiHost)) {
            //机器人Token
            String token = "Bot " + botId + "." + botToken;
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", token);
        }
        return execution.execute(request, body);
    }

}
