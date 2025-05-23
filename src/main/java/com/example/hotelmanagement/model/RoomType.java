package com.example.hotelmanagement.model;

// based on capacity/number of beds & the size of these
public enum RoomType {
    SINGLE, // 1 single bed x 8
    DOUBLE, // 1 double bed x 180
    JUNIOR_SUITE, // 1 bedroom (queen bed) + a small living room with sofa x 20
    MEDIUM_SUITE, // 2 bedrooms (2x queen bed) + a small living room with sofa x 5
    LARGE_SUITE, // 2 bedrooms (1x queen bed, 3x single beds)  + a small living room with sofa x 35
    PRESIDENTIAL_SUITE // private elevators included x1
}
