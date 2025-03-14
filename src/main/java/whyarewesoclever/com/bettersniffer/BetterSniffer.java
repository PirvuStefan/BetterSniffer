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
                ReadWriteNBT nbt = NBT.parseNBT("{Health:20.0f,Motion:[0.0d,10.0d,0.0d],Silent:1b}");
                NBTItem nbtItem = new NBTItem(item);
                nbtItem.mergeCompound(nbt);
                if(Math.random() < 0.9) event.getItemDrop().setItemStack(nbtItem.getItem());

            }
        }

}
