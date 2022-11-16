package HotelBookingProject;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    
    private List<Room> availabe_rooms = new ArrayList<>();
    private List<Room> booked_rooms = new ArrayList<>();
    private String name;


    public Hotel(String name) {
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void addRoom(Room selectedroom){
        if (selectedroom.isBooked() == false && availabe_rooms.contains(selectedroom) == false){
            this.availabe_rooms.add(selectedroom);
        }
        else throw new IllegalArgumentException("The selected room has already been booked or is already in this hotel.");
    }

    public void removeBooking(Room room){
        if (room.isBooked() && booked_rooms.contains(room)){
            room.setDays_booked(0);
            room.setPeopleAmount(0);
            room.resetPrice();
            room.setBookingstatus(false);
            room.setBreakfast(false);
            booked_rooms.remove(room);
            availabe_rooms.add(room);
        }
        else throw new IllegalArgumentException("The selected room has not been booked.");
    }

    public void bookRoom(Room room, int days_booked, int people_amount, boolean breakfast){
        if (room.isBooked() == false && this.availabe_rooms.contains(room) && days_booked > 0 && people_amount > 0){
            room.setDays_booked(days_booked);
            room.setPeopleAmount(people_amount);
            room.setBreakfast(breakfast);
            room.setTotalPrice();
            room.setBookingstatus(true);
            availabe_rooms.remove(room);
            booked_rooms.add(room);
        }
        else throw new IllegalArgumentException("The selected room has already been booked or does not exist. You must also choose a number of days and an amount of people bigger than zero. The amount of people cannot exceed the room size.");
    }

    public List<Room> getAvailable_rooms() {
        return new ArrayList<Room>(this.availabe_rooms);
    }

    public List<Room> getBooked_rooms() {
        return new ArrayList<Room>(this.booked_rooms); //Kan ikke lenger endre på den interne listen utenfra.
    }

    public void resetHotel(){
        availabe_rooms.clear();
        booked_rooms.clear();
    }

    public static void main(String[] args) {
        Hotel h1 = new Hotel("Hotell Royale");
        // System.out.println(h1.getName());
        // System.out.println(h1.getAvailable_rooms());
        // System.out.println(h1.getBooked_rooms());
        Room r1 = new Room("1000", 2, false);
        Room r2 = new Room("1001", 4, false);
        Room r3 = new Room("2000", 3, false);
        Room r4 = new Room("2001", 3, true);
        Room r5 = new Room("2001", 4, false);
        h1.addRoom(r1);
        h1.addRoom(r2);
        h1.addRoom(r3);
        h1.addRoom(r4);
        h1.addRoom(r5);
        h1.bookRoom(r1, 4, 2, false);
        h1.bookRoom(r3, 1, 3, false);
        h1.bookRoom(r4, 1, 3, true);
        System.out.println(r1.getPrice());
        System.out.println(r1.getPrice());
        System.out.println(r1.getPrice());
        h1.removeBooking(r3);
        System.out.println(h1.getAvailable_rooms());
        System.out.println(h1.getBooked_rooms());
    }
}
