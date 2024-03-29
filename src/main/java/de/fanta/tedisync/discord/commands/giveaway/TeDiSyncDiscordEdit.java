package de.fanta.tedisync.discord.commands.giveaway;

import de.fanta.tedisync.discord.DiscordBot;
import de.fanta.tedisync.discord.Giveaway;
import de.fanta.tedisync.utils.ChatUtil;
import de.iani.cubesideutils.bungee.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Collection;

public class TeDiSyncDiscordEdit extends SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String commandString, ArgsParser args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return true;
        }

        if (!args.hasNext()) {
            ChatUtil.sendErrorMessage(player, "Du musst einen Namen für das Gewinnspiel angeben.");
            return true;
        }

        String giveawayName = args.getNext();
        Giveaway giveaway = DiscordBot.getGiveaways().get(giveawayName.toLowerCase());
        if (giveaway == null) {
            ChatUtil.sendErrorMessage(player, "Ein Gewinnspiel mit dem Namen " + ChatUtil.BLUE + giveawayName + ChatUtil.RED + " existiert nicht.");
            return true;
        }
        DiscordBot.getUserEditGiveaway().put(player.getUniqueId(), giveawayName.toLowerCase());
        ChatUtil.sendNormalMessage(player, "Du bearbeitest jetzt das Gewinnspiel " + ChatUtil.BLUE + giveawayName + ChatUtil.GREEN + ".");
        return true;
    }

    @Override
    public Collection<String> onTabComplete(CommandSender sender, Command command, String alias, ArgsParser args) {
        return DiscordBot.getGiveaways().keySet();
    }

    @Override
    public String getRequiredPermission() {
        return "tedisync.discord.giveaway.edit";
    }
}
