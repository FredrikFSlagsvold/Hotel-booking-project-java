package HotelBookingProject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomTest {

    private Room room;

    @BeforeEach
    public void setup(){
        this.room = new Room("100", 2, false);
    }

    @Test
    @DisplayName("Checks that the room cannot have a negative room size value")
        public void testConstructor(){
            assertThrows(IllegalArgumentException.class, () -> new Room("testroom", -1, false), "Expected new Room to throw exeption, but didn't.");
            Room r1 = new Room("101", 1, true);
            assertEquals("101", r1.getRoomName());
            assertEquals(1, r1.getRoom_size());
            assertTrue(r1.isBusinessClass());
        }

    @Test
    @DisplayName("Checks that a negtive amount of people cannot use the room and that the amount of people does not exceed the room size.")
        public void testPeopleAmount(){
            assertThrows(IllegalArgumentException.class, () -> room.setPeopleAmount(5), "Expected setPeopleAmount to throw exception, but didn't");
            assertThrows(IllegalArgumentException.class, () -> room.setPeopleAmount(-1), "Expected setPeopleAmount to throw exception, but didn't");
            room.setPeopleAmount(2);
            assertEquals(2, room.getPeopleAmount());
        }

    @Test
    @DisplayName("Confirms that the number of days booked cannot be negative.")
        public void testDaysBooked(){
            assertThrows(IllegalArgumentException.class, () -> room.setDays_booked(-1), "Expected setPeopleAmount to throw exception, but didn't");
            room.setDays_booked(14);
            assertEquals(14, room.getDays_booked());
        }

    @Test
    @DisplayName("Checks if the room size can be a negative number or zero.")
        public void testRoomSize(){
            assertThrows(IllegalArgumentException.class, () -> room.setRoom_size(-1), "Expected setPeopleAmount to throw exception, but didn't");
            assertThrows(IllegalArgumentException.class, () -> room.setRoom_size(0), "Expected setPeopleAmount to throw exception, but didn't");
            room.setRoom_size(5);
            assertEquals(5, room.getRoom_size());
        }

    @Test
    @DisplayName("Checks that the price is calculated correctly.")
        public void checkRoomPrice(){
            Room room2 = new Room("102", 6, true);
            Room room3 = new Room("102", 6, false);
            Room room4 = new Room("103", 6, false);
            room.setDays_booked(5);
            room.setPeopleAmount(2);
            room.setBreakfast(true);
            room.setTotalPrice();
            assertEquals(4750, room.getPrice());
            room2.setDays_booked(8);
            room2.setPeopleAmount(4);
            room2.setBreakfast(true);
            room2.setTotalPrice();
            assertEquals(24800, room2.getPrice());
            room3.setDays_booked(8);
            room3.setPeopleAmount(4);
            room3.setBreakfast(true);
            room3.setTotalPrice();
            assertEquals(20800, room3.getPrice());
            room4.setDays_booked(8);
            room4.setPeopleAmount(4);
            room4.setBreakfast(false);
            room4.setTotalPrice();
            assertEquals(14400, room4.getPrice());
        }

    @Test
    @DisplayName("Tests that the price is reset.")
        public void testResetPrice(){
            room.setDays_booked(5);
            room.setPeopleAmount(2);
            room.setBreakfast(true);
            room.setTotalPrice();
            assertEquals(4750, room.getPrice());
            room.resetPrice();
            assertEquals(0, room.getPrice());
        }
         
}
