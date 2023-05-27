package bot.entity;

/**
 * @author 梁振辉
 * @since 2023-05-26 16:05:58
 */
public class MessageReference {
    /**
     * 需要引用回复的消息 id
     */
    private String message_id;
    /**
     * 是否忽略获取引用消息详情错误，默认否
     */
    private Boolean ignore_get_message_error;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public Boolean getIgnore_get_message_error() {
        return ignore_get_message_error;
    }

    public void setIgnore_get_message_error(Boolean ignore_get_message_error) {
        this.ignore_get_message_error = ignore_get_message_error;
    }
}
