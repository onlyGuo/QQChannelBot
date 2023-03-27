package bot.entity;

import java.util.List;

/**
 * 消息对象(Message)
 *
 * @author 梁振辉
 * @since 2023-03-28 00:12:02
 */
public class Message {
    /**
     * 消息 id
     */
    private String id;
    /**
     * 子频道 id
     */
    private String channelId;
    /**
     * 频道 id
     */
    private String guildId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息创建时间
     */
    private String timestamp;
    /**
     * 消息编辑时间
     */
    private String editedTimestamp;
    /**
     * 是否是@全员消息
     */
    private Boolean mentionEveryone;
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
     * ark消息
     */
    private MessageArk ark;
    /**
     * 用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序。(目前只在消息事件中有值，2022年8月1日 后续废弃)
     */
    private Integer seq;
    /**
     * 子频道消息 seq，用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序
     */
    private String seqInChannel;
    /**
     * 引用消息对象
     */
    private MessageReference messageReference;
    /**
     * 用于私信场景下识别真实的来源频道id
     */
    private String srcGuildId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
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

    public String getEditedTimestamp() {
        return editedTimestamp;
    }

    public void setEditedTimestamp(String editedTimestamp) {
        this.editedTimestamp = editedTimestamp;
    }

    public Boolean getMentionEveryone() {
        return mentionEveryone;
    }

    public void setMentionEveryone(Boolean mentionEveryone) {
        this.mentionEveryone = mentionEveryone;
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

    public String getSeqInChannel() {
        return seqInChannel;
    }

    public void setSeqInChannel(String seqInChannel) {
        this.seqInChannel = seqInChannel;
    }

    public MessageReference getMessageReference() {
        return messageReference;
    }

    public void setMessageReference(MessageReference messageReference) {
        this.messageReference = messageReference;
    }

    public String getSrcGuildId() {
        return srcGuildId;
    }

    public void setSrcGuildId(String srcGuildId) {
        this.srcGuildId = srcGuildId;
    }
}
