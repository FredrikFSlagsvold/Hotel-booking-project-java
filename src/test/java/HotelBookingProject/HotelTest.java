package HotelBookingProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HotelTest {

    private Hotel hotel;
    private Room availableRoom;
    private Room bookedRoom;
    private Room room3;
    private Room room4;
    private Room room5;
    
    @BeforeEach
    public void setup(){
        this.hotel = new Hotel("testHotel");
        this.availableRoom = new Room("100", 4, false);
        this.bookedRoom = new Room("200", 3, false);
        this.room3 = new Room("300", 4, true);
        this.room4 = new Room("400", 4, false);
        this.room5 = new Room("400", 4, false);
    }

    private void addAllRooms(){
        hotel.addRoom(availableRoom);
        hotel.addRoom(bookedRoom);
        hotel.addRoom(room3);
        hotel.addRoom(room4);
        hotel.addRoom(room5);
    }
    
    @Test
    @DisplayName("No rooms available or booked.")
        public void testConstructor(){
            assertTrue(hotel.getAvailable_rooms().isEmpty());
            assertTrue(hotel.getAvailable_rooms().isEmpty());
        }
    @Test
    @DisplayName("Added rooms are placed in the right list. Not possible to add the same room more than once.")
        public void testAddRooms(){
            hotel.addRoom(availableRoom);
            hotel.addRoom(bookedRoom);
            assertTrue(hotel.getAvailable_rooms().contains(availableRoom));
            assertTrue(hotel.getAvailable_rooms().contains(bookedRoom));
            assertTrue(hotel.getBooked_rooms().isEmpty());
            assertThrows(IllegalArgumentException.class, () -> hotel.addRoom(availableRoom), "Expected bookRoom to throw, but didn't.");

        }
    
    @Test
    @DisplayName("Booked rooms should be placed in the list of booked rooms and removed from the list of available rooms.")
        public void testBookRooms(){
            hotel.addRoom(availableRoom);
            hotel.addRoom(bookedRoom);
            assertEquals(2, hotel.getAvailable_rooms().size());
            assertEquals(0, hotel.getBooked_rooms().size());
            hotel.bookRoom(bookedRoom, 4, 2, true);
            assertTrue(bookedRoom.isBooked());
            assertEquals(4, bookedRoom.getDays_booked());
            assertEquals(2, bookedRoom.getPeopleAmount());
            assertTrue(bookedRoom.hasBreakfast());
            assertTrue(hotel.getBooked_rooms().contains(bookedRoom));
            assertFalse(hotel.getAvailable_rooms().contains(bookedRoom));
            assertEquals(1, hotel.getAvailable_rooms().size());
            assertEquals(1, hotel.getBooked_rooms().size());
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(bookedRoom, 4, 3, true), "Expected bookRoom to throw, but didn't.");
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(availableRoom, 0, 2, true), "Expected bookRoom to throw, but didn't.");
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(availableRoom, 1, 0, true), "Expected bookRoom to throw, but didn't.");
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(availableRoom, 1, 5, true), "Expected bookRoom to throw, but didn't.");
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(availableRoom, 1, -2, true), "Expected bookRoom to throw, but didn't.");
            assertThrows(IllegalArgumentException.class, () -> hotel.bookRoom(availableRoom, -5, 2, true), "Expected bookRoom to throw, but didn't.");
        }

    @Test
    @DisplayName("Validates the calculations of prices. Businessclassed rooms should add 500 to the price per day. Breakfast should add 200 per person per day.")
        public void testPriceCalculations(){
            addAllRooms();
            hotel.bookRoom(bookedRoom, 3, 2, false);
            assertEquals(2550, bookedRoom.getPrice());
            hotel.bookRoom(room3, 5, 4, false);
            hotel.bookRoom(room4, 5, 4, false);
            hotel.bookRoom(room5, 5, 4, true);
            assertEquals(8500, room3.getPrice());
            assertEquals(6000, room4.getPrice());
            assertEquals(10000, room5.getPrice());
        }

    @Test
    @DisplayName("Checks that a booking removal of a booked room resets the room values and places it in the correct list.")
        public void testRemoveBooking(){
            addAllRooms();
            hotel.bookRoom(bookedRoom, 3, 2, true);
            hotel.bookRoom(room3, 10, 3, true);
            hotel.bookRoom(room5, 4, 2, false);
            assertEquals(3, hotel.getBooked_rooms().size());
            assertEquals(2, hotel.getAvailable_rooms().size());
            assertThrows(IllegalArgumentException.class, () -> hotel.removeBooking(availableRoom), "Expected removeBooking to throw but didn't.");
            hotel.removeBooking(bookedRoom);
            assertFalse(hotel.getBooked_rooms().contains(bookedRoom));
            assertTrue(hotel.getAvailable_rooms().contains(bookedRoom));
            assertEquals(3, hotel.getAvailable_rooms().size());
            assertEquals(2, hotel.getBooked_rooms().size());
            assertFalse(bookedRoom.isBooked());
            assertEquals(0, bookedRoom.getDays_booked());
            assertEquals(0, bookedRoom.getPeopleAmount());
            assertEquals(0, bookedRoom.getPrice());
            assertFalse(bookedRoom.hasBreakfast());
        }

    @Test
    @DisplayName("Tests that booked and available rooms are removed.")
        public void testResetHotel(){
            addAllRooms();
            hotel.bookRoom(room3, 2, 2, true);
            hotel.bookRoom(room4, 7, 3, false);
            assertEquals(3, hotel.getAvailable_rooms().size());
            assertEquals(2, hotel.getBooked_rooms().size());
            hotel.resetHotel();
            assertTrue(hotel.getAvailable_rooms().isEmpty());
            assertTrue(hotel.getBooked_rooms().isEmpty());
        }
    
}
