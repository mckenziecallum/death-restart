package com.cjmckenzie.deathrestart.utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class WorldUtils {

  public static void copy(String original, String newWorld) {
    World originalWorld = Bukkit.getWorld(original);
    if (originalWorld == null) {
      throw new IllegalArgumentException("Invalid world provided");
    }

    File source = originalWorld.getWorldFolder();
    File target = new File(Bukkit.getWorldContainer(), newWorld);

    copyFileStructure(source, target);

    new WorldCreator(newWorld).createWorld();
  }

  public static void unload(String world) {
    World worldObj = Bukkit.getWorld(world);

    if (worldObj != null) {
      Bukkit.unloadWorld(worldObj, true);
    }
  }

  public static void delete(String world) {
    unload(world);
    File target = new File(Bukkit.getWorldContainer(), world);

    try {
      FileUtils.deleteDirectory(target);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static final List<String> ignoredFiles = List.of("uid.dat", "session.lock");

  private static void copyFileStructure(File source, File target) {
    try {
      if (ignoredFiles.contains(source.getName())) {
        return;
      }

      if (!source.isDirectory()) {
        Files.copy(source.toPath(), target.toPath());
        return;
      }

      if (!target.exists() && !target.mkdirs()) {
        throw new RuntimeException("Couldn't create world directory!");
      }

      String[] files = source.list();

      if (files == null) {
        return;
      }

      for (String file : files) {
        File srcFile = new File(source, file);
        File destFile = new File(target, file);
        copyFileStructure(srcFile, destFile);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public static void teleportToWorldSpawn(Player player, String worldName) {
    World world = Bukkit.getWorld(worldName);

    if (world == null) {
      throw new IllegalArgumentException("");
    }

    player.teleport(world.getSpawnLocation());
  }

}
