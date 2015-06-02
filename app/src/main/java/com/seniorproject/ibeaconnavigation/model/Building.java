package com.seniorproject.ibeaconnavigation.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * Model for a building, which should have a mapping of associated Room objects.
 *
 * Created by Calvin on 6/1/2015.
 */
public class Building implements Serializable {
    private static final long serialVersionUID = 0x05231993;
    private int num;
    private String name;
    private HashMap<Integer, Room> rooms;

    public static String TAG_ROOMS = "BLDG_ROOMS";
    public static String TAG_BLDG_NUM = "BLDG_NUM";

    /* Mapping of building numbers to their model objects */
    protected static HashMap<Integer, Building> bldgs = new HashMap<Integer, Building>() {{
        put(1, new Building(1, "Administration", null));
        put(2, new Building(2, "Cochett Education",
                new HashMap<Integer, Room>() {{
                    put(12, new Room(12, 2, "00:07:80:15:89:2C"));
                }}));
        put(3, new Building(3, "Business",
                new HashMap<Integer, Room>() {{
                    put(111, new Room(111, 3, "00:07:80:15:89:2C"));
                }}));
        put(4, new Building(4, "Research Development", null));
        put(5, new Building(5, "Architecture & Environmental", null));
        put(14, new Building(14, "Frank E. Pilling Computer Science",
                new HashMap<Integer, Room>() {{
                    put(201, new Room(201, 14, "00:07:80:15:89:E0"));
                    put(212, new Room(212, 14, "00:07:80:15:89:2C", "Dr. Janzen's Office"));
                    put(235, new Room(235, 14, "00:07:80:15:89:2C", "CSL Main"));
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

    public Collection<Room> getRooms() {
        return rooms.values();
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
        return String.format("%03d %s", getNum(), getName());
    }
}
