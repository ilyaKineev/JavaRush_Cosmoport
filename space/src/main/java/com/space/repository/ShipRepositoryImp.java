package com.space.repository;

import com.space.model.Ship;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Классы для создания и обработки запросов по принципу DAO. В конечном вараиане не участвуют.
 */
@Repository
public class ShipRepositoryImp implements ShipRepository {

    // Сессии для создания соединения с базой данный
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Ship> getAllShips() {
        List<Ship> shipsList = new ArrayList<>();

        Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery("from Ship").list();
    }

    @Override
    public void addShip(Ship ship) {

        // Создаем соединение с БД для добавления ship
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(ship);

    }

    @Override
    public void updateShip(Ship ship ,long id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(ship);

    }

    @Override
    public void deleteShip(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Ship ship = (Ship) session.load(Ship.class, new Long(id));
    }

    @Override
    public Ship getToShipById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Ship ship = (Ship) session.load(Ship.class, new Long(id));
        return ship;
    }

    @Override
    public List<Ship> getShipsArrayFilter() {

        return null;
    }

    @Override
    public long getQuantityShip() {
        return 0;
    }
}

//     private static Map<Integer, Ship> shipsMap = new HashMap<>();
//
//
//    static{
//        Ship shipOne = new Ship(1,"ПервыйТест","Земля", ShipType.TRANSPORT,new Date(1595555555),true, 500.0,15000);
//        Ship shipTwo = new Ship(1,"ПервыйТест","Земля",ShipType.TRANSPORT,new Date(1595555555),true, 500.0,15000);
//        Ship shipThree = new Ship(1,"ПервыйТест","Земля",ShipType.TRANSPORT,new Date(1595555555),true, 500.0,15000);
//        Ship shipFOur = new Ship(1,"ПервыйТест","Земля",ShipType.TRANSPORT,new Date(1595555555),true, 500.0,15000);
//
//        shipsMap.put(1,shipOne);
//        shipsMap.put(2,shipTwo);
//        shipsMap.put(3,shipThree);
//        shipsMap.put(4,shipFOur);
//    }