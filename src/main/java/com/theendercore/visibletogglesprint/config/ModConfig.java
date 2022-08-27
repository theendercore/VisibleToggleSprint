package com.theendercore.visibletogglesprint.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static com.theendercore.visibletogglesprint.VisibleToggleSprint.MODID;

public class ModConfig {


    public static final int DEFAULT_SPRINT_LOCATION = -4;
    public static final int DEFAULT_SNEAK_LOCATION = 3;
    private static ModConfig SINGLE_INSTANCE = null;
    private final File configFile;
    private int sprintLocationX;
    private int sprintLocationY;
    private int sneakLocationX;
    private int sneakLocationY;

    public ModConfig() {
        this.configFile = FabricLoader.getInstance().getConfigDir().resolve(MODID + ".json").toFile();
        this.sprintLocationX = DEFAULT_SPRINT_LOCATION;
        this.sprintLocationY = DEFAULT_SPRINT_LOCATION;
        this.sneakLocationX = DEFAULT_SNEAK_LOCATION;
        this.sneakLocationY = DEFAULT_SNEAK_LOCATION;


    }

    public static ModConfig getConfig() {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new ModConfig();
        }

        return SINGLE_INSTANCE;
    }

    public void load() {
        try {
            String jsonStr = new String(Files.readAllBytes(this.configFile.toPath()));
            if (!jsonStr.equals("")) {
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonStr);
                this.sprintLocationX = jsonObject.has("sprintLocationX") ? jsonObject.getAsJsonPrimitive("sprintLocationX").getAsInt() : DEFAULT_SPRINT_LOCATION;
                this.sprintLocationY = jsonObject.has("sprintLocationY") ? jsonObject.getAsJsonPrimitive("sprintLocationY").getAsInt() : DEFAULT_SPRINT_LOCATION;
                this.sneakLocationX = jsonObject.has("sneakLocationX") ? jsonObject.getAsJsonPrimitive("sneakLocationX").getAsInt() : DEFAULT_SNEAK_LOCATION;
                this.sneakLocationY = jsonObject.has("sneakLocationY") ? jsonObject.getAsJsonPrimitive("sneakLocationY").getAsInt() : DEFAULT_SNEAK_LOCATION;
            }
        } catch (IOException e) {
            // Do nothing, we have no file, and thus we have to keep everything as default
        }
    }

    public void save() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sprintLocationX", this.sprintLocationX);
        jsonObject.addProperty("sprintLocationY", this.sprintLocationY);
        jsonObject.addProperty("sneakLocationX", this.sneakLocationX);
        jsonObject.addProperty("sneakLocationY", this.sneakLocationY);

        try (PrintWriter out = new PrintWriter(configFile)) {
            out.println(jsonObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getSprintLocationX() {
        return sprintLocationX;
    }
    public void setSprintLocationX(int sprintLocationX) {
        this.sprintLocationX = sprintLocationX;
    }

    public int getSprintLocationY() {
        return sprintLocationY;
    }
    public void setSprintLocationY(int sprintLocationY) {
        this.sprintLocationY = sprintLocationY;
    }

    public int getSneakLocationX() {
        return sneakLocationX;
    }
    public void setSneakLocationX(int sneakLocationX) {
        this.sneakLocationX = sneakLocationX;
    }

    public int getSneakLocationY() {
        return sneakLocationY;
    }
    public void setSneakLocationY(int sneakLocationY) {
        this.sneakLocationY = sneakLocationY;
    }

}