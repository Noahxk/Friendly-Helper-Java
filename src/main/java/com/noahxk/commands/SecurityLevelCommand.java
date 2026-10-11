package com.noahxk.commands;

import com.noahxk.database.SecurityLevelDataGateway;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SecurityLevelCommand extends Command{
    public SecurityLevelCommand() {
        super("security-level");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        switch(event.getSubcommandName()) {
            case "get":
                get(event);
                break;
            case "set":
                if(!SecurityLevelDataGateway.isSecurityLevelMet(event, 3)) return;
                set(event);
                break;
        }
    }

    private void get(SlashCommandInteractionEvent event) {
        if(event.getOption("user") != null) {
            Member user = event.getOption("user").getAsMember();
            if(user.getUser().isBot()) {
                event.reply("Unable to comply: Bot account");
                return;
            }

            int securityLevel = SecurityLevelDataGateway.getSecurityLevel(user.getId());

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Security Level of " + user.getEffectiveName())
                    .setDescription(":shield: **" + securityLevel + "**")
                    .build();

            event.replyEmbeds(embed).queue();
        } else {
            int securityLevel = SecurityLevelDataGateway.getSecurityLevel(event.getMember().getId());

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(16777215)
                    .setTitle("Your Security Level")
                    .setDescription(":shield: **" + securityLevel + "**")
                    .build();

            event.replyEmbeds(embed).queue();
        }
    }

    private void set(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        int securityLevel = event.getOption("level").getAsInt();

        SecurityLevelDataGateway.setSecurityLevel(user.getId(), securityLevel);

        MessageEmbed embed = new EmbedBuilder()
                .setColor(16777215)
                .setTitle("Security Level of " + user.getEffectiveName() + " Modified :white_check_mark:")
                .setDescription("New Level: :shield: **" + securityLevel + "**")
                .build();

        event.replyEmbeds(embed).queue();
    }
}
