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

import java.util.Collection;

public class TeDiSyncDiscordInfo extends SubCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String commandString, ArgsParser args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            ChatUtil.sendErrorMessage(sender, "You are not a Player :>");
            return false;
        }

        if (!args.hasNext() && !DiscordBot.getUserEditGiveaway().containsKey(player.getUniqueId())) {
            ChatUtil.sendErrorMessage(player, "Du bearbeitest momentan kein Gewinnspiel.");
            return true;
        }

        Giveaway giveaway = args.hasNext() ? DiscordBot.getGiveaways().get(args.getNext().toLowerCase()) : DiscordBot.getGiveaways().get(DiscordBot.getUserEditGiveaway().get(player.getUniqueId()));
        if (giveaway == null) {
            ChatUtil.sendErrorMessage(player, "Das Gewinnspiel, welches du aktuell bearbeitest existiert nicht.");
            return true;
        }

        ChatUtil.sendNormalMessage(player, ChatUtil.GREEN.toString() + ChatColor.BOLD + ChatColor.UNDERLINE + "--- Giveaway-Info zu " + giveaway.getName() + " ---");
        ChatUtil.sendNormalMessage(player, "Name: " + ChatUtil.BLUE + giveaway.getName());
        ChatUtil.sendNormalMessage(player, "Nachricht: " + (giveaway.getMessage() != null ? ChatUtil.BLUE + giveaway.getMessage() : ChatUtil.ORANGE + "NULL"));
        ChatUtil.sendNormalMessage(player, "Titel: " + (giveaway.getTitle() != null ? ChatUtil.BLUE + giveaway.getTitle() : ChatUtil.ORANGE + "NULL"));
        ChatUtil.sendNormalMessage(player, "Knopf-Text: " + (giveaway.getButtonText() != null ? ChatUtil.BLUE + giveaway.getButtonText() : ChatUtil.ORANGE + "NULL"));
        ChatUtil.sendNormalMessage(player, "Farbe: " + (giveaway.getChatColor() != null ? ChatColor.of(giveaway.getChatColor()) + giveaway.getChatColor() : ChatUtil.ORANGE + "NULL"));
        ChatUtil.sendNormalMessage(player, "Mehrfach eintragen: " + (giveaway.isEnterMultiple() ? ChatUtil.GREEN : ChatUtil.RED) + giveaway.isEnterMultiple());
        ChatUtil.sendNormalMessage(player, "Geöffnet: " + (giveaway.isOpen() ? ChatUtil.GREEN : ChatUtil.RED) + giveaway.isOpen());
        ChatUtil.sendNormalMessage(player, "");
        ChatUtil.sendNormalMessage(player, "Erfüllt Mindestvoraussetzung: " + (giveaway.canOpen() ? ChatUtil.GREEN : ChatUtil.RED) + giveaway.canOpen());
        ChatUtil.sendNormalMessage(player, "");
        ChatUtil.sendNormalMessage(player, "Teilnehmer: " + ChatUtil.BLUE + giveaway.getEntryList().size());
        ChatUtil.sendNormalMessage(player, "Teilnahmen: " + ChatUtil.BLUE + giveaway.countEntries());
        return true;
    }

    @Override
    public Collection<String> onTabComplete(CommandSender sender, Command command, String alias, ArgsParser args) {
        return DiscordBot.getGiveaways().keySet();
    }

    @Override
    public String getRequiredPermission() {
        return "tedisync.discord.giveaway.info";
    }
}
