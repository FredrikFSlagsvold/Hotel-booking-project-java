package HotelBookingProject;

public class Room {

    private String name;
    private boolean bookingstatus = false;
    private int days_booked;
    private int room_size;
    private int price;
    private int people_amount;
    private boolean hasbreakfast = false;
    private boolean business_class = false;


    public boolean isBusinessClass(){
        return business_class;
    }

    public Room(String name, int room_size, boolean business_class) {
        this.name = name;
        this.business_class = business_class;
        if (room_size >= 0){
            this.room_size = room_size;
        }
        else throw new IllegalArgumentException("The room size cannot be a negative number.");
    }

    public boolean isBooked() {
        return bookingstatus;
    }
    
    public void setBookingstatus(boolean bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    public boolean getBookingStatus(){
        return bookingstatus;
    }

    public int getDays_booked() {
        return days_booked;
    }

    public void setPeopleAmount(int people_amount){
        if (people_amount >= 0 && people_amount <= room_size){
            this.people_amount = people_amount;
        }
        else throw new IllegalArgumentException("The amount of people must be a positive number.");
    }

    public int getPeopleAmount(){
        return people_amount;
    }

    public void setDays_booked(int days_booked) {
        if (days_booked >= 0){
            this.days_booked = days_booked;
        }
        else throw new IllegalArgumentException("Days booked must be a positive number.");
    }

    public int getRoom_size() {
        return room_size;
    }

    public void setRoom_size(int room_size) {
        if (room_size > 0){
            this.room_size = room_size;
        }
        else throw new IllegalArgumentException("The room size must be bigger than zero.");
    }

    public int getPrice() {
        return price;
    }

    public boolean hasBreakfast() {
        return hasbreakfast;
    }

    public void setBreakfast(boolean hasbreakfast) {
        this.hasbreakfast = hasbreakfast;
    }

    public String getRoomName(){
        return name;
    }

    public void setTotalPrice() {
        pricePerRoomSize();
        pricePerClass();
        pricePerDay();
    }

    public void resetPrice(){
        this.price = 0;
    }

    private int pricePerDay(){
        pricePerBreakfast();
        this.price = price*days_booked;
        return price;
    }

    private int pricePerBreakfast(){
        if (this.hasBreakfast() == true){
            price += 200*people_amount;
        }
        return price;
    }
    
    private void pricePerClass(){
        if (this.isBusinessClass() == true){
            this.price += 500;
        }
    }

    private int pricePerRoomSize(){
        if (room_size == 1){
            this.price = 275;
            return price;
        }
        else if (room_size == 2){
            this.price = 550;
            return price;
        }
        else if (room_size == 3){
            this.price = 850;
            return price;
        }
        else if (room_size == 4){
            this.price = 1200;
            return price;
        }
        else if (room_size == 5){
            this.price = 1500;
            return price;
        }
        else if (room_size == 6){
            this.price = 1800;
            return price;
        }
        else if (room_size > 6){
            throw new IllegalArgumentException("There are no rooms big enough for six people. You have to order separate rooms.");
        }
        else throw new IllegalArgumentException("You can only order a select number of rooms from sizes one to six.");
    }

    @Override
    public String toString() {
        return "Room " + name + ":" + " Business: "+ business_class + ", room size: " + room_size + ", number of people: " + people_amount + ", breakfast included: "+ hasbreakfast + ", days booked: " + days_booked +
             ", price: " + price;
    }

    public static void main(String[] args) {
        
    }
}
