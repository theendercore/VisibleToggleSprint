package com.theendercore.visibletogglesprint.lib;

public enum CrosshairIcons{
    DEFAULT( 0, "DEFAULT"),
    MINIMAL_ONE(4,"MINIMAL_ONE") ,
    MINIMAL_TWO(8, "MINIMAL_TWO"),
    MINIMAL_THREE(12, "MINIMAL_THREE");
    public final int x;
    public final String name;

    CrosshairIcons(int x, String name){ this.x = x; this.name = name;}
}