package de.fanta.tedisync.discord.commands.giveaway;

import de.fanta.tedisync.discord.DiscordBot;
import de.fanta.tedisync.discord.Giveaway;
import de.fanta.tedisync.utils.ChatUtil;
import de.iani.cubesideutils.bungee.commands.SubCommand;
import de.iani.cubesideutils.commands.ArgsParser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeDiSyncDiscordSetColor extends SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String commandString, ArgsParser args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return false;
        }

        if (!DiscordBot.getUserEditGiveaway().containsKey(player.getUniqueId())) {
            ChatUtil.sendErrorMessage(player, "Du bearbeitest momentan kein Gewinnspiel.");
            return true;
        }
        Giveaway giveaway = DiscordBot.getGiveaways().get(DiscordBot.getUserEditGiveaway().get(player.getUniqueId()));
        if (giveaway == null) {
            ChatUtil.sendErrorMessage(player, "Das Gewinnspiel, welches du aktuell bearbeitest existiert nicht.");
            return true;
        }

        if (!args.hasNext()) {
            ChatUtil.sendErrorMessage(player, "Du musst eine Farbe für das Gewinnspiel angeben.");
            return true;
        }

        String colorText = args.getNext();
        if (!colorText.startsWith("#")) {
            ChatUtil.sendErrorMessage(player, "Die Farbe muss als Hex angegeben werden. (#FFFFFF)");
            return true;
        }
        try {
            ChatColor.of(colorText);
        } catch (IllegalArgumentException ex) {
            ChatUtil.sendErrorMessage(player, ex.getMessage());
            return true;
        }


        giveaway.setChatColor(colorText);
        ChatUtil.sendNormalMessage(player, "Du hast eine Farbe für das Gewinnspiel hinzugefügt.");
        return true;
    }

    @Override
    public String getRequiredPermission() {
        return "tedisync.discord.giveaway.setcolor";
    }

}
