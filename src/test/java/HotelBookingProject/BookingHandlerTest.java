package HotelBookingProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingHandlerTest {

    private Hotel hotel;
    private Hotel savedNewHotel;
    private BookingHandler bookingHandler = new BookingHandler();

    private void initializeHotel(){
        Hotel hotel = new Hotel("Bennys Hotell");
        Room r1 = new Room("1000", 1, false);
        Room r2 = new Room("1001", 2, false);
        Room r3 = new Room("1002", 2, false);
        Room r4 = new Room("1003", 3, false);
        Room r5 = new Room("1004", 3, false);
        Room r6 = new Room("2000", 4, false);
        Room r7 = new Room("2001", 4, false);
        Room r8 = new Room("3000", 5, false);
        Room r9 = new Room("3001", 5, false);
        Room r10 = new Room("4000", 6, false);
        Room r11 = new Room("4000+", 6, true);
        Room r12 = new Room("3000+", 5, true);
        Room r13 = new Room("2000+", 4, true);
        hotel.addRoom(r1);
        hotel.addRoom(r2);
        hotel.addRoom(r3);
        hotel.addRoom(r4);
        hotel.addRoom(r5);
        hotel.addRoom(r6);
        hotel.addRoom(r7);
        hotel.addRoom(r8);
        hotel.addRoom(r9);
        hotel.addRoom(r10);
        hotel.addRoom(r11);
        hotel.addRoom(r12);
        hotel.addRoom(r13);
        hotel.bookRoom(r2, 6, 2, false);
        hotel.bookRoom(r5, 7, 1, true);
        hotel.bookRoom(r9, 4, 5, true);
        hotel.bookRoom(r13, 6, 4, false);
        this.hotel = hotel;
    }


    @BeforeEach
    public void setup(){
        initializeHotel();
    }

    @Test
    @DisplayName("Test that an exception is thrown when loading a non-existing file.")
    public void testLoadNonExistingFile(){
        assertThrows(FileNotFoundException.class, () -> bookingHandler.readBookingList("nofile", hotel), "Expected readBookingList to throw exception but nothing was thrown.");
    }

    @Test
    @DisplayName("Tests that an exception is thrown when loading an invalid file.")
    public void testLoadInvalidFile(){
        assertThrows(Exception.class, () -> bookingHandler.readBookingList("InvalidFile", hotel), "Expected readBookingList to throw exception but nothing was thrown.");
    }


    @Test
    @DisplayName("Tests that a new booking is created and that a new hotel equals the original when loading the save file.")
    public void testWriteBooking(){
        savedNewHotel = new Hotel("Test hotell");
        try {
            bookingHandler.writeBookingList("test-file-new", hotel);
        }
        catch (FileNotFoundException e) {
            fail("Could not save file.");
        }
        try {
            bookingHandler.readBookingList("test-file-new", savedNewHotel);
        }
        catch (FileNotFoundException e){
            fail("Could not load");
            return;
        }
        assertEquals(hotel.getAvailable_rooms().toString(), savedNewHotel.getAvailable_rooms().toString());
        assertEquals(hotel.getBooked_rooms().toString(), savedNewHotel.getBooked_rooms().toString());
    }

    @Test
    public void testReadBooking(){
        savedNewHotel = new Hotel("Test Hotell");
        try {
            savedNewHotel = bookingHandler.readBookingList("test-file", savedNewHotel);
        }
        catch (FileNotFoundException e){
            fail("Could not load");
            return;
        }

        assertEquals(hotel.getBooked_rooms().size(), savedNewHotel.getBooked_rooms().size());
        assertEquals(hotel.getAvailable_rooms().size(), savedNewHotel.getAvailable_rooms().size());
        assertEquals(hotel.getAvailable_rooms().toString(), savedNewHotel.getAvailable_rooms().toString());
        assertEquals(hotel.getBooked_rooms().toString(), savedNewHotel.getBooked_rooms().toString());
    }

    @AfterAll
    public void teardown(){
        Path path = FileSystems.getDefault().getPath("src/main/java/HotelBookingProject/Bookings/test-file-new.txt");
        try{
            Files.delete(path);
        }
        catch(Exception e){

        }
    }
    
}
