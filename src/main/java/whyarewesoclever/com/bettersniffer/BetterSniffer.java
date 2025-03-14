package whyarewesoclever.com.bettersniffer;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class BetterSniffer extends JavaPlugin implements Listener {

    public static BetterSniffer getInstance() {
        return getPlugin(BetterSniffer.class);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("BetterSniffer has been enabled!");
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            ReloadCommand reloadCommand = new ReloadCommand("bettersniffer");
            commandMap.register("bettersniffer", reloadCommand);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


        getServer().getPluginManager().registerEvents(this, getInstance());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("BetterSniffer has been disabled!");
    }


    //@EventHandler
    //        public void onSnifferDrop(EntityDropItemEvent event) {
    //            if (event.getEntityType() == EntityType.SNIFFER) {
    //                getLogger().info("A Sniffer has died!");
    //                event.getItemDrop().setItemStack(new ItemStack(Material.DIAMOND));
    //            }
    //        } -- this work to spawn diamond when sniffer digs the ground



        @EventHandler
        public void onSnifferDrop(EntityDropItemEvent event) {
            if (event.getEntityType() == EntityType.SNIFFER) {
                getLogger().info("A Sniffer has dug the ground!");

                ItemStack item = new ItemStack(Material.DIAMOND);
                ReadWriteNBT nbt = NBT.parseNBT("{\u007FbCustomModelData\u007Fr: \u007F61000\u007Fr, \u007FbPublicBukkitValues\u007Fr: {\"\u007Fboraxen:original_name\u007Fr\": \"\u007Fa§x§F§6§9§D§8§4M§x§F§6§A§1§8§5i§x§F§7§A§5§8§5n§x§F§7§A§8§8§6e§x§F§7§A§C§8§6r§x§F§7§B§0§8§7'§x§F§8§B§4§8§7s§x§F§8§B§7§8§8 §x§F§8§B§B§8§9s§x§F§8§B§F§8§9a§x§F§9§C§3§8§An§x§F§9§C§6§8§Ad§x§F§9§C§A§8§Bw§x§F§9§C§E§8§Bi§x§F§A§D§2§8§Ct§x§F§A§D§5§8§Cc§x§F§A§D§9§8§Dh\u007Fr\", \"\u007Fboraxen:id\u007Fr\": \"\u007Faminer_sandwitch\u007Fr\"}, \u007Fbdisplay\u007Fr: {\u007FbName\u007Fr: '\u007Fa{\"extra\":[{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F69D84\",\"text\":\"M\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F6A185\",\"text\":\"i\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F7A585\",\"text\":\"n\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F7A886\",\"text\":\"e\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F7AC86\",\"text\":\"r\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F7B087\",\"text\":\"\\'\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F8B487\",\"text\":\"s\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F8B788\",\"text\":\" \"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F8BB89\",\"text\":\"s\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F8BF89\",\"text\":\"a\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F9C38A\",\"text\":\"n\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F9C68A\",\"text\":\"d\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F9CA8B\",\"text\":\"w\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#F9CE8B\",\"text\":\"i\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#FAD28C\",\"text\":\"t\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#FAD58C\",\"text\":\"c\"},{\"bold\":false,\"italic\":false,\"underlined\":false,\"strikethrough\":false,\"obfuscated\":false,\"color\":\"#FAD98D\",\"text\":\"h\"}],\"text\":\"\"}\u007Fr'}}, \u007FbCount\u007Fr: \u007F61\u007Fcb\u007Fr}\n");
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.mergeCompound(nbt);
                if(Math.random() < 0.9) event.getItemDrop().setItemStack(nbtItem.getItem());

            }
        }

}
