package bot.entity;

/**
 * 用户对象(User)
 * 用户对象中所涉及的 ID 类数据，都仅在机器人场景流通，与真实的 ID 无关。请不要理解为真实的 ID
 *
 * @author 梁振辉
 * @since 2023-05-26 15:42:27
 */
public class User {
    /**
     * 用户 id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 是否是机器人
     */
    private Boolean bot;
    /**
     * 特殊关联应用的 openid，需要特殊申请并配置后才会返回。如需申请，请联系平台运营人员。
     */
    private String union_openid;
    /**
     * 机器人关联的互联应用的用户信息，与union_openid关联的应用是同一个。如需申请，请联系平台运营人员。
     */
    private String union_user_account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public String getUnion_openid() {
        return union_openid;
    }

    public void setUnion_openid(String union_openid) {
        this.union_openid = union_openid;
    }

    public String getUnion_user_account() {
        return union_user_account;
    }

    public void setUnion_user_account(String union_user_account) {
        this.union_user_account = union_user_account;
    }
}
