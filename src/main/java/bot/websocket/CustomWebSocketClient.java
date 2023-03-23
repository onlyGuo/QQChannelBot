package bot.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;

@Slf4j
public class CustomWebSocketClient extends WebSocketClient {

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

    ObjectMapper mapper = new ObjectMapper();

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open");
    }

    @SneakyThrows
    @Override
    public void onMessage(String s) {
        log.info(s);
        JsonNode msg = mapper.readTree(s);
        //opcode
        int op = msg.get("op").asInt();
        if (op == 10) {
            //鉴权连接
            identify();
        } else if (op == 0) {
            //事件类型
            String t = msg.get("t").asText();
            if ("MESSAGE_CREATE".equals(t)) {

            }
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("close");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    /**
     * 鉴权连接
     */
    public void identify() {
        //机器人Token
        String token = "Bot " + botId + "." + botToken;
        //水平分片
        ArrayNode shard = mapper.createArrayNode()
                .add(0)
                .add(1);
        //事件内容
        ObjectNode d = mapper.createObjectNode()
                .put("token", token)
                .put("intents", 512)
                .set("shard", shard);
        ObjectNode data = mapper.createObjectNode()
                .put("op", 2)
                .set("d", d);
        send(data.toString());
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    public void heart() {
        ObjectNode data = mapper.createObjectNode()
                .put("op", 1)
                .set("d", null);
        send(data.toString());
    }
}
