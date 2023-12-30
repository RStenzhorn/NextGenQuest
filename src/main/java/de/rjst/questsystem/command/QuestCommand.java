package de.rjst.questsystem.command;

import de.rjst.questsystem.config.bean.PluginCommand;
import de.rjst.questsystem.util.PaperUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@PluginCommand("quest")
@Component
public class QuestCommand implements CommandExecutor {

    @Qualifier("questCommandConsumer")
    private final Consumer<Player> questCommandConsumer;

    @Override
    public final boolean onCommand(@NotNull final CommandSender sender, final @NotNull Command command, @NotNull final String label, @NotNull final String[] args) {
        final Optional<Player> player = PaperUtil.getPlayer(sender);
        player.ifPresent(questCommandConsumer);
        return true;
    }
}
