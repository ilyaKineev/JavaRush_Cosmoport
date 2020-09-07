package com.space.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Сущность корабля
 */

@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;            // Long id ID корабля

    @Column(name = "name")
    private String name;        //String name Название корабля (до 50 знаков включительно)

    @Column(name = "planet")
    private String planet;      //String planet Планета пребывания (до 50 знаков включительно)

    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    private ShipType shipType;  //ShipType shipType Тип корабля


    @Column(name = "prodDate")
    @Temporal(TemporalType.DATE)
    private Date prodDate;      //Date prodDate Дата выпуска. Диапазон значений года 2800..3019 включительно

    @Column(name = "isUsed")
    private Boolean isUsed;     //Boolean isUsed Использованный / новый

    @Column(name = "speed")
    private Double speed;       //Double speed Максимальная скорость корабля. Диапазон значений 0,01..0,99 включительно. Используй математическое округление до сотых.

    @Column(name = "crewSize")
    private Integer crewSize;   //Integer crewSize Количество членов экипажа. Диапазон значений 1..9999 включительно.


    @Column(name = "rating")
    private Double rating;      //Double rating Рейтинг корабля. Используй математическое округление до сотых

    // Ниже геттеры и сеттеры, все актоматически сгенирированно. Расписывать не буду.
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean aTrue) {
        isUsed = aTrue;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Переопределенный метод toString()
     * @return
     */
    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isTrue=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }

    /**
     * Переопределенный метод equals
     * @param obj - обьект сравнения
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ship ship = (Ship) obj;
        return id == ship.id &&
                Objects.equals(name, ship.name) &&
                Objects.equals(planet, ship.planet) &&
                shipType == ship.shipType &&
                Objects.equals(prodDate, ship.prodDate) &&
                Objects.equals(isUsed, ship.isUsed) &&
                Objects.equals(speed, ship.speed) &&
                Objects.equals(crewSize, ship.crewSize) &&
                Objects.equals(rating, ship.rating);
    }

    /**
     * переопределенный метод hashCode()
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }

}
