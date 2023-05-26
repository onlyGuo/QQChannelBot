package bot.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 梁振辉
 * @since 2023-05-26 15:58:13
 */
@Data
public class Member {
    /**
     * 用户的频道基础信息，只有成员相关接口中会填充此信息
     */
    private User user;
    /**
     * 用户的昵称
     */
    private String nick;
    /**
     * 用户在频道内的身份组ID, 默认值可参考DefaultRoles
     */
    private List<String> roles;
    /**
     * 用户加入频道的时间
     */
    private String joined_at;
}
