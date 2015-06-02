package com.seniorproject.ibeaconnavigation.model;

import java.io.Serializable;

/**
 * Model for a room, which should have an associated building and
 * Bluetooth beacon.
 *
 * Created by Calvin on 6/1/2015.
 */
public class Room implements Serializable {
    private static final long serialVersionUID = 0x05231994;
    private String name = "";
    private int num;
    private int bldgNum;
    private final String beaconAddr;
    // Tags for bundling intent extras
    public static String TAG_BEACON_ADDR = "BEACON_ADDRESS";
    public static String TAG_LABEL = "TARGET_ROOM_LABEL";

    public Room(int roomNumber, int bldgNum, String beaconAddr) {
        this.num = roomNumber;
        this.bldgNum = bldgNum;
        this.beaconAddr = beaconAddr;
    }

    public Room(int roomNumber, int bldgNum, String beaconAddr, String name) {
        this.num = roomNumber;
        this.bldgNum = bldgNum;
        this.beaconAddr = beaconAddr;
        this.name = name;
    }

    public int getBldgNum() {
        return bldgNum;
    }

    public int getNum() {
        return num;
    }

    public String getBeaconAddr() {
        return beaconAddr;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format(
                "%03d-%03d %s", Building.getBuilding(bldgNum).getNum(), getNum(), getName());
    }
}
