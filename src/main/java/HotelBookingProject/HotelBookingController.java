package HotelBookingProject;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HotelBookingController {

    private Hotel hotel;
    private Room room;
    private int numberOfPeople;
    private int roomSize;
    private int numberOfDays;
    private IBookingHandler bookingHandler = new BookingHandler();

    @FXML
    public ComboBox<Room> RoomsAvailable;

    @FXML
    public ComboBox<Integer> RoomSize, pickDays, peopleCount;

    @FXML
    public CheckBox BreakfastIncluded;

    @FXML
    public ListView<Room> ShowBookedRooms;

    @FXML
    public Button AddBooking, bookingRemoval, saveButton, loadBooking;

    @FXML
    public Label commentaryField;

    @FXML
    public ImageView hotelLogo;

    @FXML
    public Image logo = new Image(getClass().getResourceAsStream("hotel4.png"));


    @FXML
    public void initialize(){
        displayImage();
        initializeHotel();
        RoomSize.getItems().setAll(1,2,3,4,5,6);
        pickDays.getItems().setAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        AddBooking.setDisable(true);
        bookingRemoval.setDisable(true);
        peopleCount.setDisable(true);
        RoomsAvailable.setDisable(true);
    }

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
        this.hotel = hotel;
    }

    private void displayImage(){
        hotelLogo.setImage(logo);
    }

    public void saveBookingList(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save bookinglist");
        dialog.setHeaderText("Choose a name for your list");
        dialog.setContentText("Name: ");
        if (ShowBookedRooms.getItems().isEmpty() == false){
            try{
                String bookingName = dialog.showAndWait().get();
                bookingHandler.writeBookingList(bookingName, this.hotel);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Booking saved");
                alert.setHeaderText(null);
                alert.setContentText("Booking saved successfully!");
                alert.showAndWait();

            }
            catch (FileNotFoundException e){
            }
            catch (NoSuchElementException e) {
            }
        }
        dialog.close();
    }

    public void readBooking(){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save bookinglist:");
        dialog.setHeaderText("Choose a name for your list");
        dialog.setContentText("Name: ");

        try{
            String bookingName = dialog.showAndWait().get();
            bookingHandler.readBookingList(bookingName, hotel);
            ShowBookedRooms.getItems().setAll(hotel.getBooked_rooms());
            RoomSize.getSelectionModel().clearSelection();
            RoomsAvailable.getItems().clear();
            RoomsAvailable.getSelectionModel().clearSelection();
            AddBooking.setDisable(true);

        }
        catch (FileNotFoundException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error!");
            alert.setContentText("No such file exists.");
            alert.showAndWait();
            alert.close();     
        }
        catch (NoSuchElementException e){

        }
    }

    public void sortAvaliableRooms(){
        RoomsAvailable.setDisable(false);
        peopleCount.setDisable(false);
        
        //nullstiller peoplecount hver gang man sorterer.
        peopleCount.getItems().clear();
        if (RoomSize.getSelectionModel().getSelectedItem() != null){
            this.roomSize = RoomSize.getSelectionModel().getSelectedItem();
        }
        for (Room room : hotel.getAvailable_rooms()){
            if (roomSize == room.getRoom_size() && room.isBooked() == false){
                RoomsAvailable.getItems().add(room);
            }

            //Fjerner rom slik at de ikke blir liggende i listen hvis man går fra størrelse 2 til 3 uten å booke.
            if (roomSize != room.getRoom_size()){
                RoomsAvailable.getItems().remove(room);
            }
        }

        //legger til antall mennesker som kan bo i et rom i peopleCount-listen.
        for (int i = 1; i <= roomSize; i++) {
            peopleCount.getItems().add(i);
        }
    }

    public void selectRoom(){
        this.room = RoomsAvailable.getSelectionModel().getSelectedItem();
        bookingRemoval.setDisable(true);
        AddBooking.setDisable(false);
    }

    public void setDays(){
        this.numberOfDays = pickDays.getSelectionModel().getSelectedItem();
    }

    private void updateBookingList(){
        //fjerner opptatte rom fra listen
        if (room.isBooked()){
            RoomsAvailable.getItems().remove(room);
        }
        ShowBookedRooms.getItems().setAll(hotel.getBooked_rooms());
    }

    private void checkBreakfast(){
        if (BreakfastIncluded.isSelected()){
            room.setBreakfast(true);
        }
        else room.setBreakfast(false);
    }

    public void addBooking(){

        try {
            checkBreakfast();
            this.numberOfPeople = peopleCount.getSelectionModel().getSelectedItem();
            hotel.bookRoom(room, numberOfDays, numberOfPeople, room.hasBreakfast());

            updateBookingList();
            //Nullstiller frokost-valget mellom bookinger.
            BreakfastIncluded.setSelected(false);
            
            //Nullstiller valget av rom mellom bookinger.
            RoomsAvailable.getSelectionModel().clearSelection();
    
            //Gjør at man ikke kan booke før man velger et nytt rom-objekt.
            AddBooking.setDisable(true);
            commentaryField.setText("");
        }    
        catch (NullPointerException e){
            commentaryField.setText("You must choose a room and how many people are staying with us before booking.");
        }
        catch (IllegalArgumentException e){
            commentaryField.setText("You must choose how many days you are staying with us before booking.");
        }
        
    }

    public void removeBooking(){
        try {
            this.room = ShowBookedRooms.getSelectionModel().getSelectedItem();
            hotel.removeBooking(room);
            updateBookingList();
    
            //Legger rommet tilbake i riktig ComboBox.
            if (roomSize == room.getRoom_size() && room.isBooked() == false){
                RoomsAvailable.getItems().add(room);
            }
    
            //Gjør at man forsatt kan booke valgt rom, selv om man først har avbooket et annet rom. Hvis ikke hadde man booket det avbookede rommet når man trykket på book rom.
            this.room = RoomsAvailable.getSelectionModel().getSelectedItem();
            bookingRemoval.setDisable(true);
        }
        catch (NullPointerException e){
            commentaryField.setText("You have not made any booking.");
        }

    }

    public void enableBookingRemoval(){
        bookingRemoval.setDisable(false);
    }

}
