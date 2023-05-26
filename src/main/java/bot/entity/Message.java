package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 15:38:11
 */
@Data
public class Message {
    /**
     * 消息ID
     */
    private String id;
    /**
     * 频道ID
     */
    private String channel_id;
    /**
     * 子频道ID
     */
    private String guild_id;
    /**
     * 频道内容
     */
    private String content;
    /**
     * 消息创建时间
     */
    private String timestamp;
    /**
     * 消息编辑时间
     */
    private String edited_timestamp;
    /**
     * 是否@全体成员
     */
    private Boolean mention_everyone;
    /**
     * 消息创建者
     */
    private User author;
    /**
     * 附件
     */
    private List<MessageAttachment> attachments;
    /**
     * embed
     */
    private List<MessageEmbed> embeds;
    /**
     * 消息中@的人
     */
    private List<User> mentions;
    /**
     * 消息创建者的member信息
     */
    private List<Member> members;
    /**
     * Ark消息
     */
    private MessageArk ark;
    /**
     * 用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序。(目前只在消息事件中有值，2022年8月1日 后续废弃)
     */
    private Integer seq;
    /**
     * 子频道消息 seq，用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序
     */
    private String seq_in_channel;
    /**
     * 引用消息对象
     */
    private MessageReference message_reference;
    /**
     * 用于私信场景下识别真实的来源频道id
     */
    private String src_guild_id;
}
