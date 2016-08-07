package ru.busride;


import java.io.Serializable;

public class Trips implements Serializable {

    private static final long serialVersionUID = 1069130001;

    private Integer   id;
    private Integer transport_id;
    private String departure_date;
    private String arrival_date;
    private Integer routine_id;
    private Double    price;
    private String price_currency;
    private String departure_point;
    private String arrival_point;
    private Integer seats;
    private Integer sits_total;

    public Trips() {}

    public Trips(Trips value) {
        this.id = value.id;
        this.transport_id = value.transport_id;
        this.departure_date = value.departure_date;
        this.arrival_date = value.arrival_date;
        this.routine_id = value.routine_id;
        this.price = value.price;
        this.price_currency = value.price_currency;
        this.departure_point = value.departure_point;
        this.arrival_point = value.arrival_point;
        this.seats = value.seats;
        this.sits_total = value.sits_total;
    }

    public Trips(
            Integer   id,
            Integer transport_id,
            String departure_date,
            String arrival_date,
            Integer routine_id,
            Double    price,
            String price_currency,
            String departure_point,
            String arrival_point,
            Integer seats,
            Integer sits_total
    ) {
        this.id = id;
        this.transport_id = transport_id;
        this.departure_date = departure_date;
        this.arrival_date = arrival_date;
        this.routine_id = routine_id;
        this.price = price;
        this.price_currency = price_currency;
        this.departure_point = departure_point;
        this.arrival_point = arrival_point;
        this.seats = seats;
        this.sits_total = sits_total;
    }

    public Integer getId() {
        return this.id;
    }

    public Trips setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getTransport_id() {
        return this.transport_id;
    }

    public Trips setTransport_id(Integer transport_id) {
        this.transport_id = transport_id;
        return this;
    }

    public String getDeparture_date() {
        return this.departure_date;
    }

    public Trips setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
        return this;
    }

    public String getArrival_date() {
        return this.arrival_date;
    }

    public Trips setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
        return this;
    }


    public Integer getRoutine_id() {
        return this.routine_id;
    }

    public Trips setRoutine_id(Integer routine_id) {
        this.routine_id = routine_id;
        return this;
    }


    public Double getPrice() {
        return this.price;
    }

    public Trips setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getPrice_currency() {
        return this.price_currency;
    }

    public Trips setPrice_currency(String price_currency) {
        this.price_currency = price_currency;
        return this;
    }

    public String getDeparture_point() {
        return this.departure_point;
    }

    public Trips setDeparture_point(String departure_point) {
        this.departure_point = departure_point;
        return this;
    }

    public String getArrival_point() {
        return this.arrival_point;
    }

    public Trips setArrival_point(String arrival_point) {
        this.arrival_point = arrival_point;
        return this;
    }

    public Integer getSeats() {
        return this.seats;
    }

    public Trips setSeatsOrdered(Integer seats) {
        this.seats = seats;
        return this;
    }

    public Integer getSeatsTotal() {
        return this.sits_total;
    }

    public Trips setSeatsTotal(Integer seats) {
        this.sits_total = seats;
        return this;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Trips (");

        sb.append(id);
        sb.append(", ").append(transport_id);
        sb.append(", ").append(departure_date);
        sb.append(", ").append(arrival_date);
        sb.append(", ").append(routine_id);
        sb.append(", ").append(price);
        sb.append(", ").append(price_currency);
        sb.append(", ").append(departure_point);
        sb.append(", ").append(arrival_point);
        sb.append(", ").append(seats);
        sb.append(", ").append(sits_total);

        sb.append(")");
        return sb.toString();
    }
}
