package com.noahxk.commands;

import com.noahxk.database.BalancesDataGateway;
import com.noahxk.database.InventoryDataGateway;
import com.noahxk.database.SecurityLevelDataGateway;
import com.noahxk.managers.DatabaseManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class DebugCommand extends Command{
    public DebugCommand() {
        super("debug");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        int securityLevel = SecurityLevelDataGateway.getSecurityLevel(event.getMember().getId());
        if(securityLevel < 3 && !event.getSubcommandName().equals("ping")) {
            event.reply("Unable to comply: Insufficient security level").queue();
            return;
        }

        switch(event.getSubcommandName()) {
            case "log":
                log(event);
                break;
            case "update-commands":
                updateCommands(event);
                break;
            case "shutdown":
                shutdown(event);
                break;
            case "ping":
                ping(event);
                break;
            case "custom":
                custom(event);
                break;
        }
    }

    // Create the slash command and it's options and subcommands here, then use the /debug update-commands command to push to discord
    private void updateCommands(SlashCommandInteractionEvent event) {

        SlashCommandData command = Commands.slash("whoisthis", "Figure out someone's real name").addOption(OptionType.USER, "user", "The user you want to get the real name of", true);

        //        SubcommandData get = new SubcommandData("get", "Get your security level or someone else's")
//                .addOption(OptionType.USER, "user", "The person's security level you want to check, leave blank for your own", false);
//        SubcommandData set = new SubcommandData("set", "Set someone's security level")
//                .addOption(OptionType.USER, "user", "The person whose security level you want to change", true)
//                        .addOption(OptionType.INTEGER, "level", "The person's new security level", true);
//
//
//        command.addSubcommands(get, set);


        event.getGuild().upsertCommand(command).queue();
        event.reply("Updated the loaded command").queue();
    }

    // Logs a message to the console
    private void log(SlashCommandInteractionEvent event) {
        System.out.println(event.getOption("message").getAsString());

        event.reply("Message written to console").queue();
    }

    // Safely shuts down the bot, this is the ideal shutdown method
    private void shutdown(SlashCommandInteractionEvent event) {
        event.reply("Initiating shutdown procedures...").queue();

        DatabaseManager.shutdownDatabase();
        System.exit(0);
    }

    // Checks bot responsiveness
    private void ping(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }

    // Load any custom code to execute here when the /debug custom command is used
    private void custom(SlashCommandInteractionEvent event) {
        InventoryDataGateway.insertRow(event.getMember().getId(), "pass-change-nickname", 1);

        boolean result = InventoryDataGateway.rowExists(event.getMember().getId(), "pass-change-nickname");

        event.reply(String.valueOf(result)).queue();
    }
}
