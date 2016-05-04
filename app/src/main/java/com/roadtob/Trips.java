package com.roadtob;


import java.io.Serializable;
import java.sql.Timestamp;

public class Trips implements Serializable {

	private static final long serialVersionUID = 1069130001;

	private Integer   id;
	private Integer   transportId;
	private Integer   driverId;
	private Timestamp departureDate;
	private Timestamp arrivalDate;
	private Boolean   isDisabled;
	private Integer   routineId;
	private Timestamp changedAt;
	private Timestamp createdAt;
	private Double    price;
	private String    priceCurrency;
	private Long      departurePoint;
	private Long      arrivalPoint;

	public Trips() {}

	public Trips(Trips value) {
		this.id = value.id;
		this.transportId = value.transportId;
		this.driverId = value.driverId;
		this.departureDate = value.departureDate;
		this.arrivalDate = value.arrivalDate;
		this.isDisabled = value.isDisabled;
		this.routineId = value.routineId;
		this.changedAt = value.changedAt;
		this.createdAt = value.createdAt;
		this.price = value.price;
		this.priceCurrency = value.priceCurrency;
		this.departurePoint = value.departurePoint;
		this.arrivalPoint = value.arrivalPoint;
	}

	public Trips(
		Integer   id,
		Integer   transportId,
		Integer   driverId,
		Timestamp departureDate,
		Timestamp arrivalDate,
		Boolean   isDisabled,
		Integer   routineId,
		Timestamp changedAt,
		Timestamp createdAt,
		Double    price,
		String    priceCurrency,
		Long      departurePoint,
		Long      arrivalPoint
	) {
		this.id = id;
		this.transportId = transportId;
		this.driverId = driverId;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.isDisabled = isDisabled;
		this.routineId = routineId;
		this.changedAt = changedAt;
		this.createdAt = createdAt;
		this.price = price;
		this.priceCurrency = priceCurrency;
		this.departurePoint = departurePoint;
		this.arrivalPoint = arrivalPoint;
	}

	public Integer getId() {
		return this.id;
	}

	public Trips setId(Integer id) {
		this.id = id;
		return this;
	}

	public Integer getTransportId() {
		return this.transportId;
	}

	public Trips setTransportId(Integer transportId) {
		this.transportId = transportId;
		return this;
	}

	public Integer getDriverId() {
		return this.driverId;
	}

	public Trips setDriverId(Integer driverId) {
		this.driverId = driverId;
		return this;
	}

	public Timestamp getDepartureDate() {
		return this.departureDate;
	}

	public Trips setDepartureDate(Timestamp departureDate) {
		this.departureDate = departureDate;
		return this;
	}

	public Timestamp getArrivalDate() {
		return this.arrivalDate;
	}

	public Trips setArrivalDate(Timestamp arrivalDate) {
		this.arrivalDate = arrivalDate;
		return this;
	}

	public Boolean getIsDisabled() {
		return this.isDisabled;
	}

	public Trips setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
		return this;
	}

	public Integer getRoutineId() {
		return this.routineId;
	}

	public Trips setRoutineId(Integer routineId) {
		this.routineId = routineId;
		return this;
	}

	public Timestamp getChangedAt() {
		return this.changedAt;
	}

	public Trips setChangedAt(Timestamp changedAt) {
		this.changedAt = changedAt;
		return this;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Trips setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public Double getPrice() {
		return this.price;
	}

	public Trips setPrice(Double price) {
		this.price = price;
		return this;
	}

	public String getPriceCurrency() {
		return this.priceCurrency;
	}

	public Trips setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
		return this;
	}

	public Long getDeparturePoint() {
		return this.departurePoint;
	}

	public Trips setDeparturePoint(Long departurePoint) {
		this.departurePoint = departurePoint;
		return this;
	}

	public Long getArrivalPoint() {
		return this.arrivalPoint;
	}

	public Trips setArrivalPoint(Long arrivalPoint) {
		this.arrivalPoint = arrivalPoint;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Trips (");

		sb.append(id);
		sb.append(", ").append(transportId);
		sb.append(", ").append(driverId);
		sb.append(", ").append(departureDate);
		sb.append(", ").append(arrivalDate);
		sb.append(", ").append(isDisabled);
		sb.append(", ").append(routineId);
		sb.append(", ").append(changedAt);
		sb.append(", ").append(createdAt);
		sb.append(", ").append(price);
		sb.append(", ").append(priceCurrency);
		sb.append(", ").append(departurePoint);
		sb.append(", ").append(arrivalPoint);

		sb.append(")");
		return sb.toString();
	}
}
