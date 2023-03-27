package bot.constant;

/**
 * 事件订阅列表
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
public class Intents {
    public static final String READY = "READY";
    /**
     * 消息事件，仅 *私域* 机器人能够设置此 intents。
     */
    public static final int GUILD_MESSAGES = 512;
    /**
     * 发送消息事件，代表频道内的全部消息，而不只是 at 机器人的消息。内容与 AT_MESSAGE_CREATE 相同
     */
    public static final String MESSAGE_CREATE = "MESSAGE_CREATE";
}
