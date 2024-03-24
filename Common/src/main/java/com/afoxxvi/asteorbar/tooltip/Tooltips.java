package com.afoxxvi.asteorbar.tooltip;

import java.util.ArrayList;
import java.util.List;

public class Tooltips {
    public static int width = 0;
    private static int targetY = 0;
    private static int currentY = 0;
    private static int lastHeight = 0;
    private static final List<String> debugStrings = new ArrayList<>();

    public static int getCurrentY() {
        return currentY;
    }

    public static int getTargetY() {
        return targetY;
    }

    public static void addScroll(int dy) {
        targetY += dy;
    }

    public static void resetDebugStrings() {
        debugStrings.clear();
    }

    public static List<String> getDebugStrings() {
        return debugStrings;
    }

    public static void addDebugString(String str) {
        debugStrings.add(str);
    }

    public static void checkReset(int height) {
        if (Tooltips.lastHeight != height) {
            Tooltips.lastHeight = height;
            targetY = 4;
            currentY = 4;
        }
    }

    private static void doScroll() {
        final int diff = targetY - currentY;
        if (diff == 0) {
            return;
        }
        int step = diff / 10;
        if (diff > 0) {
            step = Math.max(1, step);
        } else {
            step = Math.min(-1, step);
        }
        currentY += step;
    }

    public static int getRealY(int top, int bottom, int screenHeight) {
        doScroll();
        if (bottom - top < screenHeight - 8) {
            return top;
        }
        if (currentY > 4) {
            targetY = 4;
            currentY = targetY;
        }
        if (currentY + bottom - top < screenHeight - 4) {
            targetY = screenHeight - 4 - bottom + top;
            currentY = targetY;
        }
        return currentY;
    }
}
