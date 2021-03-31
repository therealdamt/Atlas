package xyz.damt.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> list(List<String> list) {
        final List<String> translateList = new ArrayList<String>();
        list.forEach(s -> {
            translateList.add(translate(s));
        });
        return translateList;
    }

}
