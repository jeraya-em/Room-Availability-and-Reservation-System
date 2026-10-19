/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package activity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;

public class ReservationStore {

    public static final String KALAP_BUILDING = "Kalap Building";
    public static final String MAIN_BUILDING = "Main Building";

    private static final LocalTime OPEN_TIME =
            LocalTime.of(7, 0);

    private static final LocalTime CLOSE_TIME =
            LocalTime.of(20, 30);

    private static final List<String> DAYS = Arrays.asList(
        "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"
    );

    private static final ArrayList<RoomReservation> reservations =
            new ArrayList<>();

    public static ArrayList<String> getRooms(String building) {
        ArrayList<String> rooms = new ArrayList<>();

        if (KALAP_BUILDING.equals(building)) {
            rooms.addAll(Arrays.asList(
                "Room 101", "Room 102", "Room 103",
                "Room 104", "Room 105",
                "Room 201", "Room 202", "Room 203",
                "Room 204", "Room 205"
            ));
        } else if (MAIN_BUILDING.equals(building)) {
            rooms.addAll(Arrays.asList(
                "BPA Room",
                "Math Room",
                "iLab",
                "ComLab",
                "ScienceLab room",
                "SNED room"
            ));
        }

        return rooms;
    }

    public static synchronized String addReservation(
            RoomReservation reservation) {

        if (reservation == null) {
            return "Reservation information is missing.";
        }

        String building = reservation.getBuilding();
        String room = reservation.getRoom();
        String day = reservation.getDay();
        LocalTime start = reservation.getStartTime();
        LocalTime end = reservation.getEndTime();

        if (!KALAP_BUILDING.equals(building)
                && !MAIN_BUILDING.equals(building)) {
            return "Please select a valid building.";
        }

        if (!getRooms(building).contains(room)) {
            return "Please select a valid room.";
        }

        if (!DAYS.contains(day)) {
            return "Please select a valid day.";
        }

        if (start == null || end == null || !start.isBefore(end)) {
            return "End time must be later than start time.";
        }

        if (start.isBefore(OPEN_TIME) || end.isAfter(CLOSE_TIME)) {
            return "Reservations must be between 7:00 AM and 8:30 PM.";
        }

        if (reservation.getSubject() == null
                || reservation.getSubject().trim().isEmpty()) {
            return "Please enter the subject or course.";
        }

        if (reservation.getInstructor() == null
                || reservation.getInstructor().trim().isEmpty()) {
            return "Please enter the instructor's name.";
        }

        for (RoomReservation existing : reservations) {
            boolean sameRoom =
                    existing.getBuilding().equals(building)
                    && existing.getRoom().equals(room)
                    && existing.getDay().equals(day);

            boolean overlap =
                    start.isBefore(existing.getEndTime())
                    && end.isAfter(existing.getStartTime());

            if (sameRoom && overlap) {
                return "This room is already reserved during that time.";
            }
        }

        reservations.add(reservation);
        return "SUCCESS";
    }

    public static synchronized ArrayList<RoomReservation>
            getReservations(String building, String day) {

        ArrayList<RoomReservation> result = new ArrayList<>();

        for (RoomReservation reservation : reservations) {
            if (reservation.getBuilding().equals(building)
                    && reservation.getDay().equals(day)) {
                result.add(reservation);
            }
        }

        result.sort(
            Comparator.comparing(RoomReservation::getStartTime)
                .thenComparing(RoomReservation::getRoom)
        );

        return result;
    }
}
