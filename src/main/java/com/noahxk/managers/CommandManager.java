package com.noahxk.managers;

import com.noahxk.commands.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CommandManager extends ListenerAdapter {
    private final Map<String, Command> commands = new HashMap<>(); // Stores instances of every command class with keys of their names

    // Use registerCommand(command) to make the command functional
    public CommandManager() {
        registerCommand(new DebugCommand());
        registerCommand(new BalanceCommand());
        registerCommand(new EconomyCommand());
        registerCommand(new SecurityLevelCommand());
        registerCommand(new WhoIsThisCommand());
        registerCommand(new CoinflipCommand());
    }

    private void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    // Uses the used slash command's name to find the correct command class and call its execute()
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        Command command = commands.get(commandName);

        if(command != null) {
            command.execute(event);
        } else {
            event.reply("Unable to comply: Command class not found").queue();
        }
    }
}
