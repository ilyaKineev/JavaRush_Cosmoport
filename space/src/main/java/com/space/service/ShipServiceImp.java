package com.space.service;

import com.space.exception.BadRequestException;
import com.space.exception.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipAPIRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;


@Service
public class ShipServiceImp implements ShipService {


    // Сущность для работы с DAO
    // private ShipRepository shipRepository = new ShipRepositoryImp();
    //    @Autowired
    //    public void setShipRepository(ShipRepository shipRepository) {
    //        this.shipRepository = shipRepository;
    //    }


    private final ShipAPIRepository shipAPIRepository;

    /**
     * Метод для работы с ShipAPIRepository
     * @param shipAPIRepository
     */
    @Autowired
    public ShipServiceImp(ShipAPIRepository shipAPIRepository) {
        this.shipAPIRepository = shipAPIRepository;
    }

    /**
     * Метод возвращает список всех кораблей
     *
     * @param specification
     * @param pageRequest
     * @return - возвращаем список всех кораблей
     */
    @Override
    public Page<?> getAllShips(Specification<Ship> specification, PageRequest pageRequest) {
        return shipAPIRepository.findAll(specification, pageRequest);
    }

    /**
     * Добавляем корабль в БД
     *
     * @param ship - корабль
     * @return
     */
    @Override
    public Ship addShip(Ship ship) {

        // Переписать в отдельный метод

        if (ship.getName() == null
                || ship.getPlanet() == null
                || ship.getShipType() == null
                || ship.getProdDate() == null
                || ship.getSpeed() == null
                || ship.getCrewSize() == null) {
            throw new BadRequestException("One of Ship params is null");
        }
        checkShipParams(ship);

        if (ship.getUsed() == null) {
            ship.setIsUsed(false);
        }

        ship.setRating(calculateRating(ship));

        return shipAPIRepository.saveAndFlush(ship);
    }

    /**
     * @param ship
     * @return
     */
    private Double calculateRating(Ship ship) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(ship.getProdDate());
        int year = cal.get(Calendar.YEAR);

        BigDecimal raiting = new BigDecimal((80 * ship.getSpeed() * (ship.getUsed() ? 0.5 : 1)) / (3019 - year + 1));
        raiting = raiting.setScale(2, RoundingMode.HALF_UP);

        return raiting.doubleValue();
    }

    /**
     * Редактируем характеристики корабля
     *
     * @param ship - новые характеристики
     * @param id   - номер старого корабля
     * @return
     */
    @Override
    public Ship updateShip(Long id, Ship ship) {

        checkShipParams(ship);

        if (!shipAPIRepository.existsById(id))
            throw new ShipNotFoundException("Ship not found");

        Ship editedShip = shipAPIRepository.findById(id).get();

        if (ship.getName() != null)
            editedShip.setName(ship.getName());

        if (ship.getPlanet() != null)
            editedShip.setPlanet(ship.getPlanet());

        if (ship.getShipType() != null)
            editedShip.setShipType(ship.getShipType());

        if (ship.getProdDate() != null)
            editedShip.setProdDate(ship.getProdDate());

        if (ship.getSpeed() != null)
            editedShip.setSpeed(ship.getSpeed());

        if (ship.getUsed() != null)
            editedShip.setIsUsed(ship.getUsed());

        if (ship.getCrewSize() != null)
            editedShip.setCrewSize(ship.getCrewSize());

        editedShip.setRating(calculateRating(editedShip));

        return shipAPIRepository.saveAndFlush(editedShip);
    }

    /**
     * Удаляем корабль по ID
     *
     * @param id
     */
    @Override
    public void deleteShip(Long id) {

        if (shipAPIRepository.existsById(id)) {
            shipAPIRepository.deleteById(id);
        } else {
            throw new ShipNotFoundException("Ship not found");
        }
    }

    /**
     * Получаем корабль по ID
     *
     * @param id - ID
     * @return возвращаем обект корабля
     */
    @Override
    public Ship getShipById(Long id) {
        if (!shipAPIRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship not found");
        }
        return shipAPIRepository.findById(id).get();
    }

    /**
     * Получаем список кораблей в соответсвии с фильтром и сортировки
     *
     * @param specification
     * @return
     */
    @Override
    public Page<Ship> getShipsArrayFilter(Specification<Ship> specification, PageRequest pageRequest) {
        return shipAPIRepository.findAll(specification, pageRequest); // времене пересылаем все корабли
    }

    /**
     * Метод возвращает количество кораблей из БД
     *
     * @return - возвращаем число кораблей
     */
    @Override
    public Integer getQuantityShip(Specification<Ship> specification) {
        return shipAPIRepository.findAll(specification).size();
    }

    private void checkShipParams(Ship ship) {

        if (ship.getName() != null && (ship.getName().length() < 1 || ship.getName().length() > 50))
            throw new BadRequestException("Incorrect Ship.name");

        if (ship.getPlanet() != null && (ship.getPlanet().length() < 1 || ship.getPlanet().length() > 50))
            throw new BadRequestException("Incorrect Ship.planet");

        if (ship.getCrewSize() != null && (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999))
            throw new BadRequestException("Incorrect Ship.crewSize");

        if (ship.getSpeed() != null && (ship.getSpeed() < 0.01D || ship.getSpeed() > 0.99D))
            throw new BadRequestException("Incorrect Ship.speed");

        if (ship.getProdDate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ship.getProdDate());
            if (cal.get(Calendar.YEAR) < 2800 || cal.get(Calendar.YEAR) > 3019)
                throw new BadRequestException("Incorrect Ship.date");
        }
    }
}
