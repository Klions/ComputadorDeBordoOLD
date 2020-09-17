/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.time.Instant;
import java.time.ZonedDateTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import police.InicializadorMain;

/**
 *
 * @author John
 */
public class DiscordWebhook2 {
    
    public static void EnviarMsg(String Canal, EmbedBuilder eb){
        TextChannel usuar = InicializadorMain.jda.getTextChannelById​(Long.parseLong(Canal));
        
        eb.setFooter("Computador de Bordo", "https://imgur.com/vBK8vRk.png");
        Instant instant = Instant.from(ZonedDateTime.now());
        eb.setTimestamp(instant);
        usuar.sendMessage(eb.build()).queue();
    }
}
