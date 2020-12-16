package xyz.damt.teams.enums;

import org.bukkit.Material;
import xyz.damt.teams.configuration.Util;

public enum Teams {

    RED(Material.WOOL, 1, Util.chat("&cRed Team")),
    GOLD(Material.WOOL, 2, Util.chat("&6Gold Team")),
    GREEN(Material.WOOL, 3, Util.chat("&aGreen Team"));

    private Material mat;
    private String name;
    private int data;

    private Teams(Material material, int data ,String display) {
        this.mat = material;
        this.name = display;
        this.data = (byte) data;
    }

    public String getDisplay() {
        return name;
    }
    public Material getMaterial() {
        return mat;
    }



}
