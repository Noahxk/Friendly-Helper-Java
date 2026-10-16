package com.noahxk.commands;

import com.noahxk.database.BalancesDataGateway;
import com.noahxk.managers.ProbabilityManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CoinflipCommand extends Command{
    public CoinflipCommand() {
        super("coinflip");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        int bet = event.getOption("bet").getAsInt();

        // Check if the user has enough money to place their bet
        if(bet > BalancesDataGateway.getBalance(event.getMember().getId())) {
            event.reply("Unable to comply: Insufficient balance").queue();
            return;
        }

        int generatedNumber = ProbabilityManager.randomInteger(5);
        if(generatedNumber < 2) { // If the user wins
            BalancesDataGateway.increaseBalance(event.getMember().getId(), bet);

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Coinflip Results")
                    .setDescription("**Doubled!**")
                    .addField("Amount Won", ":coin: **" + bet * 2 + "**", false)
                    .build();

            event.replyEmbeds(embed).queue();
        } else { // If the user loses
            BalancesDataGateway.decreaseBalance(event.getMember().getId(), bet);

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Coinflip Results")
                    .setDescription("**Lost!**")
                    .addField("Amount Lost", ":coin: **" + bet + "**", false)
                    .build();

            event.replyEmbeds(embed).queue();
        }


    }
}
