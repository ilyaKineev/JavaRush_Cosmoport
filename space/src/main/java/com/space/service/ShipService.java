package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface ShipService {

    // получать список всех существующих кораблей из БД
    public Page getAllShips(Specification<Ship> specification, PageRequest pageRequest);

    // создавать новый корабль и добавляем в БД
    public Ship addShip(Ship ship);

    // редактировать характеристики существующего корабля и обнавляем БД
    public Ship updateShip(Long id, Ship ship);

    // удалять корабль из БД
    public void deleteShip(Long id);

    // получать корабль по id из БД
    public Ship getShipById(Long id);

    // получать отфильтрованный список кораблей в соответствии с переданными
    //фильтрами из БД
    public Page<Ship> getShipsArrayFilter(Specification<Ship> specification, PageRequest pageRequest);

    // получать количество кораблей, которые соответствуют фильтрам
    public Integer getQuantityShip(Specification<Ship> specification);
}
