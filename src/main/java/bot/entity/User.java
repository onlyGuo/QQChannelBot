package bot.entity;

public class User {

    private String id;
    private String username;
    private String avatar;
    private Boolean bot;
    private String unionOpenid;
    private String unionUserAccount;

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

    public String getUnionOpenid() {
        return unionOpenid;
    }

    public void setUnionOpenid(String unionOpenid) {
        this.unionOpenid = unionOpenid;
    }

    public String getUnionUserAccount() {
        return unionUserAccount;
    }

    public void setUnionUserAccount(String unionUserAccount) {
        this.unionUserAccount = unionUserAccount;
    }
}
