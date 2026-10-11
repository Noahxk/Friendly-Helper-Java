package com.noahxk.commands;

import com.noahxk.database.NameDataGateway;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WhoIsThisCommand extends Command{
    public WhoIsThisCommand() {
        super("whoisthis");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        User user = event.getOption("user").getAsUser();
        if(user.isBot()) {
            event.reply("Unable to comply: Bot account").queue();
            return;
        }

        String name = NameDataGateway.getName(user.getId());

        if(name == null) {
            event.reply("Unable to comply: User name is undocumented").queue();
        } else event.reply("That's " + name).queue();
    }
}
