package com.seniorproject.ibeaconnavigation.model;

/**
 * Model for a room, which should have an associated building and
 * Bluetooth beacon.
 *
 * Created by Calvin on 6/1/2015.
 */
public class Room {
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

    public int getBldgNum() {
        return bldgNum;
    }

    public int getNum() {
        return num;
    }

    public String getBeaconAddr() {
        return beaconAddr;
    }

    public String toString() {
        return Building.getBuilding(bldgNum).getNum() + "-" + getNum();
    }
}
