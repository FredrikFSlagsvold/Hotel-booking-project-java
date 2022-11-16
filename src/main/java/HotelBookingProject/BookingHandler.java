package HotelBookingProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookingHandler implements IBookingHandler {

    @Override
    public void writeBookingList(String filename, Hotel hotel) throws FileNotFoundException {

        try (PrintWriter writer = new PrintWriter(getFile(filename))){
            writer.println(hotel.getName());
            for (Room room : hotel.getBooked_rooms()){
                writer.println(room.getRoomName() + ";" + room.getBookingStatus() + ";" + room.getRoom_size() + ";" + room.isBusinessClass() + ";" + room.getDays_booked() + ";" + room.getPeopleAmount() + ";" + room.hasBreakfast() + ";" + room.getPrice());
            }
            for (Room availableRoom : hotel.getAvailable_rooms()){
                writer.println(availableRoom.getRoomName() + ";" + availableRoom.getBookingStatus() + ";" + availableRoom.getRoom_size() + ";" + availableRoom.isBusinessClass() + ";" + availableRoom.getDays_booked() + ";" + availableRoom.getPeopleAmount() + ";" + availableRoom.hasBreakfast() + ";" + availableRoom.getPrice());

            }
            writer.flush();
            writer.close();   
        }
    }

    @Override
    public Hotel readBookingList(String filename, Hotel hotel) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(getFile(filename))){
            hotel.resetHotel();
            scanner.nextLine();
            while (scanner.hasNextLine()){
                String[] properties = scanner.nextLine().split(";");
                Room r = new Room(/*name*/ properties[0], /*room_size*/Integer.parseInt(properties[2]), /*business class*/ Boolean.parseBoolean(properties[3]));
                hotel.addRoom(r);
                if (Boolean.parseBoolean(properties[1]) == true){
                    hotel.bookRoom(r, /*days_booked*/ Integer.parseInt(properties[4]), /*people_amount*/ Integer.parseInt(properties[5]), /*breakfast*/ Boolean.parseBoolean(properties[6]));
                }
            }
            scanner.close();
            return hotel;
        }
    }
    
    private File getFile(String filename){
        return new File("src/main/java/HotelBookingProject/Bookings/"+ filename + ".txt");
    }

}

