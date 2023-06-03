package com.cjmckenzie.deathrestart.utils;


import org.apache.commons.lang3.StringUtils;
import org.bukkit.map.MapFont;
import org.bukkit.map.MinecraftFont;

import java.lang.reflect.Field;

public class CharRepo {

  public static final String NEG1 = "\uF801";
  public static final String NEG2 = "\uF802";
  public static final String NEG3 = "\uF803";
  public static final String NEG4 = "\uF804";
  public static final String NEG5 = "\uF805";
  public static final String NEG6 = "\uF806";
  public static final String NEG7 = "\uF807";
  public static final String NEG8 = "\uF808";
  public static final String NEG16 = "\uF809";
  public static final String NEG32 = "\uF80A";
  public static final String NEG64 = "\uF80B";
  public static final String NEG128 = "\uF80C";
  public static final String NEG256 = "\uF80D";
  public static final String NEG512 = "\uF80E";
  public static final String NEG1024 = "\uF80F";

  public static final String POS1 = "\uF821";
  public static final String POS2 = "\uF822";
  public static final String POS3 = "\uF823";
  public static final String POS4 = "\uF824";
  public static final String POS5 = "\uF825";
  public static final String POS6 = "\uF826";
  public static final String POS7 = "\uF827";
  public static final String POS8 = "\uF828";
  public static final String POS16 = "\uF829";
  public static final String POS32 = "\uF82A";
  public static final String POS64 = "\uF82B";
  public static final String POS128 = "\uF82C";
  public static final String POS256 = "\uF82D";
  public static final String POS512 = "\uF82E";
  public static final String POS1024 = "\uF82F";

  public static final String BOSS_BAR_END = "\uA201" + NEG1;
  public static final String BOSS_BAR_CENTER_1 = "\uA202" + NEG1;
  public static final String BOSS_BAR_CENTER_2 = "\uA203" + NEG1;
  public static final String BOSS_BAR_CENTER_4 = "\uA204" + NEG1;
  public static final String BOSS_BAR_CENTER_8 = "\uA205" + NEG1;
  public static final String BOSS_BAR_CENTER_16 = "\uA206" + NEG1;
  public static final String BOSS_BAR_CENTER_32 = "\uA207" + NEG1;
  public static final String BOSS_BAR_CENTER_64 = "\uA208" + NEG1;
  public static final String BOSS_BAR_CENTER_128 = "\uA209" + NEG1;

  public static final String NAVIGATOR_PIN = "\uA20A";

  public static int getPixelWidth(String text) {
    int widthPixels = 0;

    for (char letterChar : text.toCharArray()) {
      MapFont.CharacterSprite charSprite = MinecraftFont.Font.getChar(letterChar);

      int charWidth = 8;
      if (charSprite != null) {
        charWidth = charSprite.getWidth() + 1;
      }

      widthPixels += charWidth;
    }

    return widthPixels;
  }

  public static String getNeg(int pixel) {
    String binary = new StringBuilder(Integer.toBinaryString(pixel)).reverse().toString();
    StringBuilder sb = new StringBuilder();
    int index = 0;
    for (char c : binary.toCharArray()) {
      if (c != '0') {
        String character = NegativeChar.getCharByWeight((int) Math.pow(2, index));
        sb.append(character);
      }
      index++;
    }

    return sb.toString();
  }

  public static String getPos(int pixel) {
    String binary = new StringBuilder(Integer.toBinaryString(pixel)).reverse().toString();
    StringBuilder sb = new StringBuilder();
    int index = 0;
    for (char c : binary.toCharArray()) {
      if (c != '0') {
        sb.append(PositiveChar.getCharByWeight((int) Math.pow(2, index)));
      }
      index++;
    }

    return sb.toString();
  }

  public static String getBossBarBg(int pixel) {
    int size = pixel - 2;
    String binary = new StringBuilder(Integer.toBinaryString(size)).reverse().toString();
    StringBuilder sb = new StringBuilder(CharRepo.BOSS_BAR_END);
    int index = 0;
    for (char c : binary.toCharArray()) {
      if (c != '0') {
        sb.append(BossBarBackgroundChar.getCharByWeight((int) Math.pow(2, index)));
      }
      index++;
    }

    sb.append(CharRepo.BOSS_BAR_END);

    return sb.toString();
  }

  public static String fromName(String name) {
    try {
      Field f = CharRepo.class.getField(name);
      return (String.valueOf(f.get(null)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private enum NegativeChar {
    NEG1(1, CharRepo.NEG1),
    NEG2(2, CharRepo.NEG2),
    NEG4(4, CharRepo.NEG4),
    NEG8(8, CharRepo.NEG8),
    NEG16(16, CharRepo.NEG16),
    NEG32(32, CharRepo.NEG32),
    NEG64(64, CharRepo.NEG64),
    NEG128(128, CharRepo.NEG128),
    NEG256(256, CharRepo.NEG256),
    NEG512(512, CharRepo.NEG512),
    NEG1024(1024, CharRepo.NEG1024);

    private int weight;
    private String s;

    NegativeChar(int weight, String s) {
      this.weight = weight;
      this.s = s;
    }

    static String getCharByWeight(int weight) {
      NegativeChar maxValue = NegativeChar.values()[0];
      for (NegativeChar c : NegativeChar.values()) {
        if (c.weight > maxValue.weight) {
          maxValue = c;
        }
        if (c.weight == weight)
          return c.s;
      }

      int multiplier = weight / maxValue.weight;

      return StringUtils.repeat(maxValue.s, multiplier);
    }
  }

  private enum PositiveChar {
    POS1(1, CharRepo.POS1),
    POS2(2, CharRepo.POS2),
    POS4(4, CharRepo.POS4),
    POS8(8, CharRepo.POS8),
    POS16(16, CharRepo.POS16),
    POS32(32, CharRepo.POS32),
    POS64(64, CharRepo.POS64),
    POS128(128, CharRepo.POS128),
    POS256(256, CharRepo.POS256),
    POS512(512, CharRepo.POS512),
    POS1024(1024, CharRepo.POS1024);

    private int weight;
    private String s;

    PositiveChar(int weight, String s) {
      this.weight = weight;
      this.s = s;
    }

    static String getCharByWeight(int weight) {
      PositiveChar maxValue = PositiveChar.values()[0];
      for (PositiveChar c : PositiveChar.values()) {
        if (c.weight > maxValue.weight) {
          maxValue = c;
        }
        if (c.weight == weight)
          return c.s;
      }

      int multiplier = weight / maxValue.weight;

      return StringUtils.repeat(maxValue.s, multiplier);
    }
  }

  private enum BossBarBackgroundChar {
    BG1(1, CharRepo.BOSS_BAR_CENTER_1),
    BG2(2, CharRepo.BOSS_BAR_CENTER_2),
    BG4(4, CharRepo.BOSS_BAR_CENTER_4),
    BG8(8, CharRepo.BOSS_BAR_CENTER_8),
    BG16(16, CharRepo.BOSS_BAR_CENTER_16),
    BG32(32, CharRepo.BOSS_BAR_CENTER_32),
    BG64(64, CharRepo.BOSS_BAR_CENTER_64),
    BG128(128, CharRepo.BOSS_BAR_CENTER_128);

    private int weight;
    private String s;

    BossBarBackgroundChar(int weight, String s) {
      this.weight = weight;
      this.s = s;
    }

    static String getCharByWeight(int weight) {
      BossBarBackgroundChar maxValue = BossBarBackgroundChar.values()[0];
      for (BossBarBackgroundChar c : BossBarBackgroundChar.values()) {
        if (c.weight > maxValue.weight) {
          maxValue = c;
        }
        if (c.weight == weight)
          return c.s;
      }

      int multiplier = weight / maxValue.weight;

      return StringUtils.repeat(maxValue.s, multiplier);
    }
  }
}