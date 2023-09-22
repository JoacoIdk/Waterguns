package me.zephi.waterguns.features.mod;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.zephi.waterguns.register.Handler;
import me.zephi.waterguns.register.RegisterHandler;

import java.util.List;
import java.util.UUID;

@Getter
public class ModHandler extends Handler {
    private List<UUID> mods;

    @Override
    public void onRegister(RegisterHandler handler) {
        mods = Lists.newArrayList();

        handler.registerCommand(new ModCommand());
    }
}
