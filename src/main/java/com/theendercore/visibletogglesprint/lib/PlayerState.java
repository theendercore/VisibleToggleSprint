package com.theendercore.visibletogglesprint.lib;

public class PlayerState {
    public Crosshair crosshair;
    public boolean hotbarEnabled;
    public TextState text;

    public PlayerState(boolean enableCross, Vec2i locationCross, CrosshairIcons icon, boolean hotbarEnabled, boolean enableText, Vec2i locationText, int colorText) {
        this.crosshair = new Crosshair(enableCross, locationCross, icon);
        this.hotbarEnabled = hotbarEnabled;
        this.text = new TextState(enableText, locationText, colorText);
    }

    public static class Crosshair {
        public boolean enable;
        public Vec2i location;
        public CrosshairIcons icon;

        public Crosshair(boolean e, Vec2i l, CrosshairIcons i) {
            this.enable = e;
            this.location = l;
            this.icon = i;
        }
    }

    public static class TextState {
        public boolean enable;
        public Vec2i location;
        public int color;

        TextState(boolean e, Vec2i l, int c) {
            this.enable = e;
            this.location = l;
            this.color = c;
        }
    }
}