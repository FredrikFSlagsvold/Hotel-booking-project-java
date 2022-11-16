package HotelBookingProject;

import java.io.FileNotFoundException;

public interface IBookingHandler {

    Hotel readBookingList(String filename, Hotel hotel) throws FileNotFoundException;

    void writeBookingList(String filename, Hotel hotel) throws FileNotFoundException;
    
}
