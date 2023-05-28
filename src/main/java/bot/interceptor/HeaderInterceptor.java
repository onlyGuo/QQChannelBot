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
 * HTTP Header拦截器
 *
 * @author 梁振辉
 * @since 2023-05-26 11:19:18
 */
@Component
public class HeaderInterceptor implements ClientHttpRequestInterceptor {

    /**
     * QQ机器人认证Token
     */
    @Value("Bot ${bot.id}.${bot.token}")
    private String botToken;
    @Value("Bearer ${openai.token}")
    private String openAiToken;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String host = request.getURI().getHost();
        HttpHeaders headers = request.getHeaders();
        if (host.equals("sandbox.api.sgroup.qq.com")) {
            //QQ频道接口
            headers.set("Authorization", botToken);
        } else if (host.equals("api.openai.com")) {
            //OpenAI接口
            headers.set("Authorization", openAiToken);
        }
        return execution.execute(request, body);
    }
}
