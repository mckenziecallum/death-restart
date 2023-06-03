package com.cjmckenzie.deathrestart.listeners;

import com.cjmckenzie.deathrestart.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Collection;

public class PlayerListener implements Listener {

  private final int RESTART_TIME_SECONDS = 20;

  @EventHandler
  public void a(PlayerDeathEvent event) {
    Player whoDied = event.getPlayer();

    Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

    Component deathMessage = event.deathMessage();
    if (deathMessage == null) {
      deathMessage = Component.text("How about don't die next time Kappa");
    }

    if (onlinePlayers.size() == 1) {
      deathMessage = Component.text("You died and thought no one would notice?",
          Style.style().color(TextColor.color(255, 0, 0)).build());
    }

    event.setCancelled(true);

    Main.getInstance().setDeathShowing(true);

    for (Player player : onlinePlayers) {
      player.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));

      if (player.isOp()) {
        player.sendMessage(
            Component.text("Server restarting in " + RESTART_TIME_SECONDS + " seconds!")
        );
      }
      player.showTitle(
          Title.title(
              Component.text(whoDied.getName() + " Died"),
              deathMessage,
              Title.Times.times(
                  Duration.ofMillis(500),
                  Duration.ofSeconds(RESTART_TIME_SECONDS),
                  Duration.ofMillis(500)
              )
          )
      );
    }

    Bukkit.getScheduler()
        .runTaskLater(
            Main.getInstance(),
            () -> Bukkit.getServer()
                .dispatchCommand(
                    Bukkit.getServer().getConsoleSender(),
                    "restart"
                ),
            RESTART_TIME_SECONDS * 20L
        );
  }

  @EventHandler
  public void a(PlayerMoveEvent event) {
    if (!event.getFrom().getBlock().getLocation().equals(event.getTo().getBlock().getLocation())) {
      if (Main.getInstance().isDeathShowing()) {
        event.setCancelled(true);
      }
    }
  }

}
