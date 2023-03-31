package bot.websocket;

import bot.constant.Opcode;
import bot.service.WebSocketService;
import bot.util.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

/**
 * WebSocket客户端
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
public class CustomWebSocketClient extends WebSocketClient {

    public static final Logger logger = LoggerFactory.getLogger(CustomWebSocketClient.class);

    @Value("Bot ${bot.id}.${bot.token}")
    String token;

    @Resource
    WebSocketService webSocketService;

    String sessionId;

    int seq = 0;

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info("WebSocket连接打开");
    }

    @Override
    public void onMessage(String message) {
        logger.info(message);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode msg;
        try {
            msg = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        int opcode = msg.get("op").asInt();
        if (opcode == Opcode.DISPATCH) {
            //事件类型
            String type = msg.get("t").asText();
            seq = msg.get("s").asInt();
            if ("MESSAGE_CREATE".equals(type)) {
                webSocketService.reply(msg);
            } else if ("READY".equals(type)) {
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
        logger.info("WebSocket连接关闭");
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    /**
     * 鉴权连接
     */
    public void identify() {
        //水平分片
        Map<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.IDENTIFY);
            put("d", new TreeMap<>() {{
                put("token", token);
                put("intents", 512);
                put("shard", new int[]{0, 1});
            }});
        }};
        send(JSONUtil.bean2json(data));
    }

    /**
     * 重连
     */
    public void resume() {
        Map<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.RESUME);
            put("d", new TreeMap<>() {{
                put("token", token);
                put("session_id", sessionId);
                put("seq", seq);
            }});
        }};
        send(JSONUtil.bean2json(data));
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    public void heart() {
        Map<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.HEARTBEAT);
            put("d", seq);
        }};
        send(JSONUtil.bean2json(data));
    }
}
