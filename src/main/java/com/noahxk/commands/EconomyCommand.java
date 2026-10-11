package com.noahxk.commands;

import com.noahxk.database.BalancesDataGateway;
import com.noahxk.database.SecurityLevelDataGateway;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class EconomyCommand extends Command {
    public EconomyCommand() {
        super("eco");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if(!SecurityLevelDataGateway.isSecurityLevelMet(event, 3)) return;

        if(event.getOption("user").getAsUser().isBot()) {
            event.reply("Unable to comply: Bot account").queue();
            return;
        }

        switch(event.getSubcommandName()) {
            case "add":
                add(event);
                break;
            case "take":
                take(event);
                break;
            case "set":
                set(event);
                break;
        }

        MessageEmbed embed = new EmbedBuilder()
                .setColor(16777215)
                .setTitle("Modified User Balance :white_check_mark:")
                .build();

        event.replyEmbeds(embed).queue();
    }

    private void add(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        long amount = event.getOption("amount").getAsLong();

        BalancesDataGateway.increaseBalance(user.getId(), amount);
    }

    private void take(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        long amount = event.getOption("amount").getAsLong();

        BalancesDataGateway.decreaseBalance(user.getId(), amount);
    }

    private void set(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        long amount = event.getOption("amount").getAsLong();

        BalancesDataGateway.setBalance(user.getId(), amount);
    }
}
