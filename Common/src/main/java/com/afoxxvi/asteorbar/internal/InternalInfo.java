package com.afoxxvi.asteorbar.internal;

import com.afoxxvi.asteorbar.AsteorBar;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Arrays;

public class InternalInfo {
    //activate start
    public static boolean activated = false;
    //activate end

    //status start
    public static short skillShow = 0;
    public static long[] skillGlint = new long[16];
    public static int[] skillGlintColor = new int[16];
    public static byte[] skillCooldown = new byte[16];
    public static byte[] skillMana = new byte[16];
    //status end

    //info start
    public static String[] skillName = new String[16];
    //info end

    //helmet start
    public static short mana = 0;
    public static short manaMax = 0;
    public static long manaGlint = 0;
    public static byte manaType = 0;
    public static int exp = 0;
    public static int expRequire = 0;
    //helmet end

    //toggle start
    public static short toggle = 0;
    //toggle end

    private static final Object object = new Object();

    /**
     * Buffer format:
     * activated: boolean
     */
    public static Object decodeActivate(FriendlyByteBuf buffer) {
        try {
            activated = buffer.readBoolean();
        } catch (Exception ignored) {
        }
        return object;
    }

    /**
     * Buffer format:
     * contents: byte
     * show: short (if contents & 1)
     * glint_color: int (if contents & 2)
     * glint_raw: short (if contents & 2)
     * cooldown: byte[16] (if contents & 4)
     * mana: byte[16] (if contents & 8)
     */
    public static Object decodeStatus(FriendlyByteBuf buffer) {
        try {
            byte contents = buffer.readByte();
            if ((contents & (1)) != 0) {
                skillShow = buffer.readShort();
            }
            if ((contents & (1 << 1)) != 0) {
                int glint_color = buffer.readInt();
                short glint_raw = buffer.readShort();
                for (int i = 0; i < 16; i++) {
                    if ((glint_raw & 1) > 0) {
                        skillGlint[i] = AsteorBar.tick;
                        skillGlintColor[i] = glint_color;
                    }
                    glint_raw >>= 1;
                }
            }
            if ((contents & (1 << 2)) != 0) {
                buffer.readBytes(skillCooldown);
            }
            if ((contents & (1 << 3)) != 0) {
                buffer.readBytes(skillMana);
            }
        } catch (Exception ignored) {
        }
        return object;
    }

    /**
     * Buffer format:
     * total: byte
     * stringLength: short
     * skillName: String[total]
     */
    public static Object decodeInfo(FriendlyByteBuf buffer) {
        try {
            byte total = buffer.readByte();
            Arrays.fill(skillName, "null");
            for (int i = 0; i < total; i++) {
                skillName[i] = buffer.readUtf();
            }
        } catch (Exception ignored) {
        }
        return object;
    }

    /**
     * Buffer format:
     * mana: short
     * manaMax: short
     * manaGlint: boolean
     * manaType: byte
     * exp: int
     * expRequire: int
     */
    public static Object decodeHelmet(FriendlyByteBuf buffer) {
        try {
            mana = buffer.readShort();
            manaMax = buffer.readShort();
            var manaGlint = buffer.readBoolean();
            if (manaGlint) {
                InternalInfo.manaGlint = AsteorBar.tick;
            }
            manaType = buffer.readByte();
            exp = buffer.readInt();
            expRequire = buffer.readInt();
        } catch (Exception ignored) {
        }
        return object;
    }

    /**
     * Buffer format:
     * toggle: short
     */
    public static Object decodeToggle(FriendlyByteBuf buffer) {
        try {
            toggle = buffer.readShort();
        } catch (Exception ignored) {
        }
        return object;
    }
}
