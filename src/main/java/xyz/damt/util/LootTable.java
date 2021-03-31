package xyz.damt.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;


public class LootTable {


        public static String serializeItemStack(ItemStack i) {
            String[] parts = new String[7];
            parts[0] = i.getType().name();
            parts[1] = Integer.toString(i.getAmount());
            parts[2] = String.valueOf(i.getDurability());
            parts[3] = (i.getItemMeta().hasDisplayName() ? i.getItemMeta().getDisplayName().replaceAll("\\xa7", "&").replaceAll("§", "&") : "");
            parts[4] = String.valueOf(i.getData().getData());
            parts[6] = getEnchants(i);
            parts[5] = serializeLore(i);
            return StringUtils.join(parts, ";");
        }

        public static ItemStack deserializeItemStack(String p) {

            String[] a = p.split(";");
            ItemStack i = new ItemStack(Material.getMaterial(a[0]), Integer.parseInt(a[1]));
            i.setDurability((short) Integer.parseInt(a[2]));
            ItemMeta meta = i.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', a[3]));
            if (a.length > 5)
                meta.setLore(deserializeLore(a[5]));
            MaterialData data = i.getData();
            data.setData((byte) Integer.parseInt(a[4]));
            i.setData(data);
            if (a.length > 6) {
                String[] parts = a[6].split(",");
                for (String s : parts) {
                    String label = s.split(":")[0];
                    String amplifier = s.split(":")[1];
                    Enchantment type = Enchantment.getByName(label);
                    if (type == null) {
                        continue;
                    }
                    int f;
                    try {
                        f = Integer.parseInt(amplifier);
                    } catch (Exception ex) {
                        continue;
                    }
                    meta.addEnchant(type, f, true);
                }
            }

            i.setItemMeta(meta);
            return i;
        }

        private static String splitArmorStand = "/A/";

        public static String serializeArmorStand(ArmorStand stand) {
            String name = stand.getCustomName();
            Location loc = stand.getLocation();
            String visible = Boolean.toString(stand.isVisible());
            String nameVisible = Boolean.toString(stand.isCustomNameVisible());
            String gravity = Boolean.toString(stand.hasGravity());
            String pickup = Boolean.toString(stand.getCanPickupItems());
            String remove = Boolean.toString(stand.getRemoveWhenFarAway());
            return name + splitArmorStand + serializeLocation(loc) + splitArmorStand + visible
                    + splitArmorStand + nameVisible + splitArmorStand + gravity + splitArmorStand + pickup + splitArmorStand + remove;
        }


        private static String splitInventory = "/I/";
        private static String splitInventorySlot = "/S/";
        private static String splitItems = "/IT/";

        public static String serializeInventory(Inventory inv) {
            String name = inv.getName();
            int size = inv.getSize();
            String items = "";
            for (int i = 0; i < size; i++) {
                ItemStack item = inv.getItem(i);
                if (item != null && item.getType() != Material.AIR)
                    items += i + splitInventorySlot + serializeItemStack(item) + (i + 1 == size ? "" : splitItems);
            }
            return name + splitInventory + size + splitInventory + items;
        }

        public static Inventory deserializeInventory(String s) {
            String[] split = s.split(splitInventory);
            Inventory inv = Bukkit.createInventory(null, Integer.parseInt(split[1]), split[0]);
            if (split.length > 2) {
                String[] items = split[2].split(splitItems);
                for (int i = 0; i < items.length; i++) {
                    String[] itemSplit = items[i].split(splitInventorySlot);
                    int slot = Integer.parseInt(itemSplit[0]);
                    ItemStack item = deserializeItemStack(itemSplit[1]);
                    inv.setItem(slot, item);
                }
            }
            return inv;
        }


        private static String splitLocation = "/L/";

        public static String serializeLocation(Location loc) {
            return loc.getX() + splitLocation + loc.getY() + splitLocation + loc.getZ() + splitLocation + loc.getWorld().getName();
        }

        public static Location deserializeLocation(String s) {
            double x, y, z;
            World world;
            String[] sep = s.split(splitLocation);
            x = Double.parseDouble(sep[0]);
            y = Double.parseDouble(sep[1]);
            z = Double.parseDouble(sep[2]);
            world = Bukkit.getWorld(sep[3]);
            return new Location(world, x, y, z);
        }

        public static String serializeItemFlags(Set<ItemFlag> itemFlags) {
            String seperator = "/IFLAG/";
            String s = "";
            for (ItemFlag flag : itemFlags)
                s += flag.toString() + seperator;
            return s;
        }

        public static Set<ItemFlag> deserializeItemFlags(String s) {
            String seperator = "/IFLAG/";
            Set<ItemFlag> itemFlags = new HashSet<>();
            String[] split = s.split(seperator);
            for (int i = 0; i < split.length - 1; i++)
                itemFlags.add(ItemFlag.valueOf(split[i]));
            return itemFlags;
        }


        private static String getEnchants(ItemStack i) {
            List<String> e = new ArrayList<String>();
            Map<Enchantment, Integer> en = i.getEnchantments();
            for (Enchantment t : en.keySet()) {
                e.add(t.getName() + ":" + en.get(t));
            }
            return StringUtils.join(e, ",");
        }

        public static String serializeLore(ItemStack i) {
            if (!(i.hasItemMeta() && i.getItemMeta().hasLore()))
                return "";
            String str = "";
            List<String> lore = i.getItemMeta().getLore();
            for (int x = 0; x < lore.size(); x++)
                str += lore.get(x).replaceAll("\\xa7", "&").replaceAll("§", "&") + (x + 1 == lore.size() ? "" : "/L/");
            return str;
        }

        public static List<String> deserializeLore(String str) {
            List<String> lore = new ArrayList<String>();
            for (String s : str.split("/L/"))
                lore.add(ChatColor.translateAlternateColorCodes('&', s.replaceAll("\\xa7", "&").replaceAll("§", "&")));
            return lore;
        }


    }

