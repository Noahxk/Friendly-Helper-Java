package com.noahxk;

import com.noahxk.managers.CommandManager;
import com.noahxk.managers.DatabaseManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        final String botToken = dotenv.get("FRIENDLY_HELPER_DEV_TOKEN");

        //Creating the bot object and connecting to the discord api
        JDA api = JDABuilder.createDefault(botToken)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(new CommandManager())
                .build();

        DatabaseManager.initialiseDatabase();
    }
}