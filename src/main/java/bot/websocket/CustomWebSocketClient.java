package bot.websocket;

import bot.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
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

    @Resource
    MessageService messageService;

    ObjectMapper mapper = new ObjectMapper();

    String sessionId;

    int seq = 0;

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open");
    }

    @SneakyThrows
    @Override
    public void onMessage(String message) {
        log.info(message);
        JsonNode msg = mapper.readTree(message);
        //opcode
        int op = msg.get("op").asInt();
        if (op == 10) {
            //鉴权连接
            identify();
        } else if (op == 7) {
            //重连
            resume();
        } else if (op == 0) {
            //事件类型
            String t = msg.get("t").asText();
            seq = msg.get("s").asInt();
            if ("MESSAGE_CREATE".equals(t)) {
                String content = msg.get("d").get("content").asText();
                if ("服务器状态".equals(content)) {
                    messageService.systemInfo(msg);
                } else if ("原神卡池".equals(content)) {
                    messageService.genshinPool(msg);
                } else {
                    messageService.reply(msg);
                }
            } else if ("READY".equals(t)) {
                sessionId = msg.get("d").get("session_id").asText();
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
     * 重连
     */
    public void resume() {
        //机器人Token
        String token = "Bot " + botId + "." + botToken;
        //事件内容
        ObjectNode d = mapper.createObjectNode()
                .put("token", token)
                .put("session_id", sessionId)
                .put("seq", seq);
        ObjectNode data = mapper.createObjectNode()
                .put("op", 6)
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
                .put("d", seq);
        send(data.toString());
    }
}
