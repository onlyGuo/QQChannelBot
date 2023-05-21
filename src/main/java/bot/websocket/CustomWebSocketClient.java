package bot.websocket;

import bot.constant.Opcode;
import bot.entity.Payload;
import bot.service.WebSocketService;
import bot.util.JSONUtil;
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

    public static final Logger log = LoggerFactory.getLogger(CustomWebSocketClient.class);

    /**
     * 机器人令牌
     */
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
        log.info("open");
    }

    @Override
    public void onMessage(String message) {
        log.info(message);
        Payload payload = JSONUtil.toObject(message, Payload.class);
        int opcode = payload.getOp();
        switch (opcode) {
            case Opcode.DISPATCH -> dispatch(payload);
            case Opcode.HELLO -> identify();
            case Opcode.RECONNECT -> resume();
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

    public void dispatch(Payload payload) {
        //消息推送
        String t = payload.getT();
        seq = payload.getS();
        if ("MESSAGE_CREATE".equals(t)) {
            //发送消息事件
            webSocketService.reply(payload);
        } else if ("READY".equals(t)) {
            //连接就绪事件
            sessionId = payload.getD().get("session_id").asText();
        }
    }

    /**
     * 鉴权连接
     */
    public void identify() {
        Map<String, Object> data = new TreeMap<>() {{
            put("op", Opcode.IDENTIFY);
            put("d", new TreeMap<>() {{
                put("token", token);
                put("intents", 512);
                put("shard", new int[]{0, 1});
            }});
        }};
        send(JSONUtil.toJson(data));
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
        send(JSONUtil.toJson(data));
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
        send(JSONUtil.toJson(data));
    }
}
