package com.noahxk.commands;

import com.noahxk.database.BalancesDataGateway;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class BalanceCommand extends Command {
    public BalanceCommand() {
        super("bal");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if(event.getOption("user") != null) {
            Member user = event.getOption("user").getAsMember();
            if(user.getUser().isBot()) {
                event.reply("Unable to comply: Bot account").queue();
                return;
            }

            long balance = BalancesDataGateway.getBalance(user.getId());

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Balance of " + user.getEffectiveName())
                    .setDescription(":coin: **" + balance + "**")
                    .build();

            event.replyEmbeds(embed).queue();
        } else {
            long balance = BalancesDataGateway.getBalance(event.getMember().getId());

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Your Balance")
                    .setDescription(":coin: **" + balance + "**")
                    .build();

            event.replyEmbeds(embed).queue();
        }
    }
}
