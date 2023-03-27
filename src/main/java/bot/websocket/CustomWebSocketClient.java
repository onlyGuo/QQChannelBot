package bot.websocket;

import bot.constant.Command;
import bot.constant.Intents;
import bot.constant.Opcode;
import bot.service.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;

/**
 * WebSocket客户端
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
public class CustomWebSocketClient extends WebSocketClient {

    public static final Logger logger = LoggerFactory.getLogger(CustomWebSocketClient.class);

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
    WebSocketService webSocketService;

    ObjectMapper mapper = new ObjectMapper();

    String sessionId;

    int seq = 0;

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("open");
    }

    @Override
    public void onMessage(String message) {
        logger.info(message);
        JsonNode msg;
        try {
            msg = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int opcode = msg.get("op").asInt();
        if (opcode == Opcode.DISPATCH) {
            //事件类型
            String t = msg.get("t").asText();
            seq = msg.get("s").asInt();
            if (Intents.MESSAGE_CREATE.equals(t)) {
                String content = msg.get("d").get("content").asText();
                switch (content) {
                    case Command.SERVER_STATUS -> webSocketService.systemInfo(msg);
                    case Command.GENSHIN_POOL -> webSocketService.genshinPool(msg);
                    default -> webSocketService.reply(msg);
                }
            } else if (Intents.READY.equals(t)) {
                sessionId = msg.get("d").get("session_id").asText();
            }
        } else if (opcode == Opcode.HELLO) {
            //鉴权连接
            identify();
        } else if (opcode == Opcode.RECONNECT) {
            //重连
            resume();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.info("close");
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
                .put("intents", Intents.GUILD_MESSAGES)
                .set("shard", shard);
        ObjectNode data = mapper.createObjectNode()
                .put("op", Opcode.IDENTIFY)
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
                .put("op", Opcode.RESUME)
                .set("d", d);
        send(data.toString());
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    public void heart() {
        ObjectNode data = mapper.createObjectNode()
                .put("op", Opcode.HEARTBEAT)
                .put("d", seq);
        send(data.toString());
    }
}
