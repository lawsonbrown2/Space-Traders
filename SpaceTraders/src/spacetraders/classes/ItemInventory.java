package spacetraders.classes;

import java.util.HashMap;

public class ItemInventory {
    private HashMap<Good, Integer> goodMap;
    private int size = 0;

    public int getSize() {
        return size;
    }

    public ItemInventory() {
        goodMap = new HashMap<Good, Integer>(20);
    }
    public void addGood(Good good) {
        if (goodMap.containsKey(good)) {
            int val = goodMap.get(good) + 1;
            goodMap.replace(good, val);
        } else {
            goodMap.put(good, 1);
        }
    }
    public void removeGood(Good good) {
        if (goodMap.containsKey(good)) {
            if (goodMap.get(good) == 1) {
                goodMap.remove(good);
            } else {
                int val = goodMap.get(good) - 1;
                goodMap.replace(good, val);
            }
        }
    }
    public int getNumberOfGood(Good good) {
        return goodMap.getOrDefault(good, 0);
    }

    public HashMap<Good, Integer> getGoodMap() {
        return goodMap;
    }
    public String toString() {
        String itemInventoryString = "";
        Person person = new Person();
        for (Good good: person.getShip().getItemInventory().getGoodMap().keySet()) {
            String key = good.getName();
            String value = person.getShip().getItemInventory().getGoodMap().get(good).toString();
            itemInventoryString += key + " " + value + " ";
        }
        return itemInventoryString;
    }
}
