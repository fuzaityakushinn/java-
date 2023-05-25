package type;

import java.time.LocalDate;

import java.util.*;
public abstract class Hotel {
    private String type;  // 房型
    private int id;  // 具体房型id
    private double price;  // 价格
    private int days;  // 预订天数
    private String date;
    private double payMoney;  // 应付金额
    private Boolean breakfast = false;
    private LocalDate checkInDate = LocalDate.now();
    private LocalDate checkOutDate = LocalDate.now();
    private boolean occupied = false;
    private List<LocalDate> bookingDates = new ArrayList<>();
    Hotel(){
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setCheckInDate(LocalDate date) {
        this.checkInDate = date;
        this.bookingDates.add(date);  // changed
    }
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public void setCheckOutDate(LocalDate date) {
        this.checkOutDate = date;
        this.bookingDates.add(date);  // changed
    }
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public int getDays() {
        return days;
    }
    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }
    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }
    public Boolean getBreakfast() {
        return breakfast;
    }

    public abstract double getPayMoney(int days);

    public String getName() {
        return type;
    }

    public void setOccupied(boolean b) {
        this.occupied = b;
    }
    public boolean isOccupied() {
        return occupied;
    }
    public void addBookingDate(LocalDate date) {
        bookingDates.add(date);

    }
    public void setBookingDates(List<LocalDate> bookingDates) {
        this.bookingDates = bookingDates;
    }

    public List<LocalDate> getBookingDates() {
        return bookingDates;
    }


}
