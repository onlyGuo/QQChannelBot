package bot.entity;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 15:38:11
 */
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
    private Member member;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEdited_timestamp() {
        return edited_timestamp;
    }

    public void setEdited_timestamp(String edited_timestamp) {
        this.edited_timestamp = edited_timestamp;
    }

    public Boolean getMention_everyone() {
        return mention_everyone;
    }

    public void setMention_everyone(Boolean mention_everyone) {
        this.mention_everyone = mention_everyone;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MessageAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<MessageEmbed> getEmbeds() {
        return embeds;
    }

    public void setEmbeds(List<MessageEmbed> embeds) {
        this.embeds = embeds;
    }

    public List<User> getMentions() {
        return mentions;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MessageArk getArk() {
        return ark;
    }

    public void setArk(MessageArk ark) {
        this.ark = ark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getSeq_in_channel() {
        return seq_in_channel;
    }

    public void setSeq_in_channel(String seq_in_channel) {
        this.seq_in_channel = seq_in_channel;
    }

    public MessageReference getMessage_reference() {
        return message_reference;
    }

    public void setMessage_reference(MessageReference message_reference) {
        this.message_reference = message_reference;
    }

    public String getSrc_guild_id() {
        return src_guild_id;
    }

    public void setSrc_guild_id(String src_guild_id) {
        this.src_guild_id = src_guild_id;
    }
}
