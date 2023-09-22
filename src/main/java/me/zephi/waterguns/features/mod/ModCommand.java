package me.zephi.waterguns.features.mod;

import com.google.common.collect.Lists;
import me.zephi.waterguns.register.command.BasicCommand;
import me.zephi.waterguns.register.command.CommandReader;
import me.zephi.waterguns.register.command.CommandReturn;

public class ModCommand extends BasicCommand {
    public ModCommand() {
        super("mod", "Toggles mod mode", "/<command>", Lists.newArrayList("modmode", "staffmode", "h"), "waterguns.mod");
    }

    @Override
    public CommandReturn onCommand(CommandReader reader) {
        return null;
    }
}
