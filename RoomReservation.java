/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package activity;

import java.time.LocalTime;

public class RoomReservation {

    private String building;
    private String room;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String subject;
    private String instructor;

    public RoomReservation(
            String building,
            String room,
            String day,
            LocalTime startTime,
            LocalTime endTime,
            String subject,
            String instructor) {

        this.building = building;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.instructor = instructor;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getInstructor() {
        return instructor;
    }
}
