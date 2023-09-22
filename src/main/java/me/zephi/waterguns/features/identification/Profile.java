package me.zephi.waterguns.features.identification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.zephi.waterguns.util.EncryptUtil;

@Getter @Setter
public class Profile {
    private String username;
    @Setter(AccessLevel.NONE)
    private String password;

    private String skin;
    private String displayName;
    private int level;

    public Profile(String username, String password) {
        this.username = username;
        this.password = EncryptUtil.encryptSHA3_256(password);

        this.skin = "Steve";
        this.displayName = username;
        this.level = 0;
    }
}
