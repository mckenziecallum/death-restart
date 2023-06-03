package com.cjmckenzie.deathrestart;

import com.cjmckenzie.deathrestart.listeners.PlayerListener;
import com.cjmckenzie.deathrestart.utils.WorldUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class Main extends JavaPlugin {

  private static Main instance;

  private boolean deathShowing = false;

  public Main() {
    instance = this;
  }

  @Override
  public void onLoad() {
    boolean doDelete = getConfig().getBoolean("deleteOnLoad", false);

    if (doDelete) {
      getLogger().info("Deleting worlds...");
      WorldUtils.delete("world");
      WorldUtils.delete("world_nether");
      WorldUtils.delete("world_the_end");

      getLogger().info("Deleted worlds.");

      getConfig().set("deleteOnLoad", false);
      saveConfig();
    }
  }

  @Override
  public void onDisable() {
    getLogger().info("Disabling DeathRestart");

    if (deathShowing) {
      getConfig().set("deleteOnLoad", true);
      saveConfig();
    }
  }

  @Override
  public void onEnable() {
    getLogger().info("Enabling DeathRestart");
    saveDefaultConfig();

    getServer().getPluginManager().registerEvents(new PlayerListener(), this);
  }

  public static Main getInstance() {
    return instance;
  }

}
