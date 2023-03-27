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
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
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

    @Value("${openai.key}")
    String openaiKey;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI uri = request.getURI();
        String host = uri.getHost();
        if ("sandbox.api.sgroup.qq.com".equals(host)) {
            //QQ机器人
            String token = "Bot " + botId + "." + botToken;
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", token);
        } else if ("api.openai.com".equals(host)) {
            //OpenAI
            String token = "Bearer " + openaiKey;
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", token);
        }
        return execution.execute(request, body);
    }

}
