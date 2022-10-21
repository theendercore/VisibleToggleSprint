package com.theendercore.visibletogglesprint.lib;

import com.google.gson.JsonObject;

public class Vec2i {
    public int x;
    public int y;
    public Vec2i(int x, int y){
        this.x = x;
        this.y = y;
    }
    public JsonObject toJSON(){
        JsonObject j = new JsonObject();
        j.addProperty("x", this.x);
        j.addProperty("y", this.y);
        return j;
    }
}