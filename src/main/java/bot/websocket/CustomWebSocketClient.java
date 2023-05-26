package bot.websocket;

import bot.entity.Message;
import bot.entity.Opcode;
import bot.entity.Payload;
import bot.service.QQChannelService;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.URI;
import java.util.TreeMap;

/**
 * WebSocket客户端
 *
 * @author 梁振辉
 * @since 2023-05-26 11:06:08
 */
public class CustomWebSocketClient extends WebSocketClient {

    private final Logger log = LoggerFactory.getLogger(CustomWebSocketClient.class);
    /**
     * WebSocket会话ID
     */
    private String sessionId;
    /**
     * 事件序号
     */
    private Integer seq;
    /**
     * QQ机器人认证Token
     */
    @Value("Bot ${bot.id}.${bot.token}")
    private String botToken;
    @Resource
    private QQChannelService qqChannelService;

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("open " + sessionId);
    }

    @Override
    public void onMessage(String msg) {
        log.info(msg);
        Payload payload = JSONUtil.toBean(msg, Payload.class);
        switch (payload.getOp()) {
            case Opcode.HELLO -> {
                if (sessionId == null) {
                    identify();
                } else {
                    resume();
                }
            }
            case Opcode.DISPATCH -> {
                seq = payload.getS();
                switch (payload.getT()) {
                    case "READY" -> sessionId = payload.getD().getStr("session_id");
                    case "MESSAGE_CREATE" -> {
                        Message message = qqChannelService.message(payload);
                        log.info(JSONUtil.toJsonStr(message));
                    }
                }
            }
        }
    }

    @Override
    public void onClose(int i, String msg, boolean b) {
        log.warn("close");
        new Thread(this::reconnect).start();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    /**
     * 鉴权连接
     */
    private void identify() {
        JSONObject data = JSONUtil.createObj()
                .set("op", Opcode.IDENTIFY)
                .set("d", JSONUtil.createObj()
                        .set("token", botToken)
                        .set("intents", 512)
                        .set("shard", new int[]{0, 1}));
        send(data.toString());
    }

    /**
     * 恢复连接
     */
    private void resume() {
        JSONObject data = JSONUtil.createObj()
                .set("op", Opcode.RESUME)
                .set("d", JSONUtil.createObj()
                        .set("token", botToken)
                        .set("session_id", sessionId)
                        .set("seq", seq));
        send(data.toString());
    }

    /**
     * 发送心跳
     */
    @Scheduled(fixedRate = 30 * 1000)
    private void heart() {
        if (sessionId != null) {
            JSONObject data = JSONUtil.createObj()
                    .set("op", Opcode.HEARTBEAT)
                    .set("s", seq);
            send(data.toString());
        }
    }

    private void hello() {

    }
}
