package com.space.repository;

import com.space.model.Ship;
import java.util.List;

/**
 * Классы для создания и обработки запросов по принципу DAO. В конечном вараиане не участвуют.
 */
public interface ShipRepository {

    // получать список всех существующих кораблей из БД
    public List<Ship> getAllShips();

    // создавать новый корабль и добавляем в БД
    public void addShip(Ship ship);

    // редактировать характеристики существующего корабля и обнавляем БД
    public void updateShip(Ship ship, long id);

    // удалять корабль из БД
    public void deleteShip(long id);

    // получать корабль по id из БД
    public Ship getToShipById(long id);

    // получать отфильтрованный список кораблей в соответствии с переданными
    //фильтрами из БД
    public List<Ship> getShipsArrayFilter();

    // получать количество кораблей, которые соответствуют фильтрам
    public long getQuantityShip();
}
