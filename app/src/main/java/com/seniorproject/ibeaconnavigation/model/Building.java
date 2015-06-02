package com.seniorproject.ibeaconnavigation.model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Model for a building, which should have a mapping of associated Room objects.
 *
 * Created by Calvin on 6/1/2015.
 */
public class Building {
    private int num;
    private String name;
    private HashMap<Integer, Room> rooms;

    /* Mapping of building numbers to their model objects */
    protected static HashMap<Integer, Building> bldgs = new HashMap<Integer, Building>() {{
        put(1, new Building(1, "Administration", null));
        put(2, new Building(2, "Cochett Education", null));
        put(3, new Building(3, "Business", null));
        put(4, new Building(4, "Research Development", null));
        put(5, new Building(5, "Architecture & Environmental", null));
        put(14, new Building(14, "Frank E. Pilling Computer Science",
                new HashMap<Integer, Room>() {{
                    put(201, new Room(201, 14, "00:07:80:15:89:2C"));
                }}));
    }};

    public Building(int bldgNum, String name, HashMap<Integer, Room> rooms) {
        this.num = bldgNum;
        this.name = name;
        this.rooms = rooms;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Get a Room of this Building by its room number.
     * @param roomNum
     * @return Room of this building whose number matches the given number.
     */
    public Room getRoom(int roomNum) {
        return rooms.get(roomNum);
    }

    public static Collection<Building> getBuildings() {
        return bldgs.values();
    }

    /**
     * Get a Building by its building number.
     * @param bldgNum
     * @return Building whose number matches the given number.
     */
    public static Building getBuilding(int bldgNum) {
        return bldgs.get(bldgNum);
    }

    public String toString() {
        return getNum() + " " + getName();
    }
}
