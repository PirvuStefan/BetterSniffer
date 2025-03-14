package whyarewesoclever.com.bettersniffer;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class ReloadCommand extends BukkitCommand {

    public ReloadCommand(String name) {
        super(name);
        this.setDescription("Reloads the BetterSniffer config");
        this.setUsage("/bettersniffer reload");
        this.setPermission("bettersniffer.commands");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {

        if (sender instanceof Player) {
            boolean permission = sender.hasPermission("bettersniffer.commands");
           if(!permission){
               if( s.equals("reload") ){
                   if( !sender.hasPermission("bettersniffer.reload"))
                       permission = false ;
               }
               if( s.equals("create") ){
                   if( !sender.hasPermission("bettersniffer.create"))
                       permission = false ;
               }
           }
           if( !permission ){
                sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#A00D0D") + "You do not have permission to use this command!");
               return false;
           }
        } // we do have permission to use the command
        tabComplete(sender, s, strings);
        //sunt un geniu mancavas



        if (strings.length == 0) {
            if (!(sender instanceof Player)) {
                getLogger().info("No argument provided. Try /bettersniffer reload");
            }
            return false;
        }

        if ( (strings.length > 1 && strings[0].equalsIgnoreCase("reload") ) ||  ( strings.length == 1 && strings[0].startsWith("r") && !strings[0].equalsIgnoreCase("reload") ) ) {
            TryAgain(sender, "reload");
            return false;
        } else if ( strings.length == 1 && strings[0].startsWith("c")  && !strings[0].equalsIgnoreCase("create")) {
            TryAgain(sender, "create"); // we do not have the create command
            return false;
        }

        if (strings[0].equalsIgnoreCase("reload")) {    // arg[0] is "reload"
                    BetterSniffer.getInstance().reloadConfig();
                    BetterSniffer.getInstance().saveDefaultConfig();


                    if (sender instanceof Player) {
                        sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#A9DE18") + "Config file reloaded!");
                        ((Player) sender).playSound(((Player) sender).getLocation(), org.bukkit.Sound.BLOCK_AMETHYST_BLOCK_BREAK, 10, 1);
                    }
                    getLogger().info("BetterSniffer config reloaded!");

                    return true;
        }

                // bettersniffer create <name> <chance> <worlds>
                if( strings.length < 4){
                    sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#A9DE18") + "Too few arguments . Try /bettersniffer create <name> <chance> <worlds>");
                    return false;
                }


                getLogger().info("Acum am ajuns aici");
                String name_id = strings[1];
                String chance = strings[2];
                List <String> biomes = new java.util.ArrayList<>(Collections.singletonList(strings[3]));
                biomes.addAll(Arrays.asList(strings).subList(4, strings.length));
             try {
            java.nio.file.Files.createFile(new java.io.File(BetterSniffer.getInstance().getDataFolder(), "Drops/" + name_id + ".yml").toPath());
             } catch (IOException e) {
                 getLogger().warning("Could not create file " + name_id + ".yml");
                 getLogger().warning("This is possibly due to a file already existing with the same name");
                 if( sender instanceof Player) {
                     sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#b53636") + "Could not create file " + name_id + ".yml");
                     sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#b53636") + "This is possibly due to a file already existing with the same name");
                     ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, 10, 1);
                 }
            throw new RuntimeException(e);

            }


        return true;
    }
    @Override
    public @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1 && args[0].startsWith("r")) {
            return Collections.singletonList("reload");
        }
        if( args.length == 1 && args[0].startsWith("c") ){
            return Collections.singletonList("create");
        }

        //if (args.length == 1 && args[0].startsWith("r")) {
        //        return Collections.singletonList("reload");
        //    }
        // if we do have multiple commands, recommends "reload" if the first letter is "r", we do not need this since we only have one command
        // and we should always recommend "reload"
        return Collections.emptyList();
    }

    public void TryAgain(CommandSender sender, String sentance) {
        if (sender instanceof Player)
          if(sentance.equals("reload"))     sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#A9DE18") + "Wrong command. Try /bettersniffer reload");
            else
                sender.sendMessage(net.md_5.bungee.api.ChatColor.of("#00FF00") + "[BetterSniffer] : " + net.md_5.bungee.api.ChatColor.of("#A9DE18") + "Wrong command. Try /bettersniffer create" );
        else
            getLogger().info("Wrong command. Try /bettersniffer reload");
    }

}
