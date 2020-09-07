/**
 * Класс SetShipController отвечает запросы от клиента
 */


package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.specification.ShipSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/rest")
public class SetShipController {

    private ShipService shipService;

    @Autowired
    public void setShipController(ShipService shipService) {
        this.shipService = shipService;
    }


//     * 1. Метод получает от Контролера запрос на список всех кораблей

    /**
     * 2. Метод получает запрос на создание нового корабля и получает @RequestBody Ship, отправляем новый корабль в
     * shipService.addShip(ship) и возвращаем ответ ввиде  ResponseEntity<Ship>
     *
     * @param ship - @RequestBody Ship
     * @return - ResponseEntity<Ship>
     */
    @RequestMapping(value = "/ships", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createNewShip(@RequestBody Ship ship) {
        // Проверка на пустой ship
        if (ship == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(shipService.addShip(ship), HttpStatus.OK);
    }

    /**
     * 3. Редактировать характеристики существующего корабля по полученному id
     *
     * @param id   - @PathVariable("id") Long id
     * @param ship - @RequestBody Ship ship
     * @return - Возвращает ResponseEntity<Ship>
     */
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateShipById(@PathVariable("id") Long id,
                                            @RequestBody Ship ship) {
        // Проверка на пустой id
        if (id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Ship>(shipService.updateShip(id, ship), HttpStatus.OK);
    }

    /**
     * 4. удалять корабль по id;
     *
     * @param id - @PathVariable("id") Long id
     * @return Возвращает ResponseEntity<Ship>
     */
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShipById(@PathVariable("id") Long id) {
        // Проверка на пустой id
        if (id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        shipService.deleteShip(id);
        return new ResponseEntity<Ship>(HttpStatus.OK);

    }

    /**
     * 5. получать корабль по id;
     *
     * @param id - ID
     * @return Возвращает ResponseEntity<Ship>
     */
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getToShipByID(@PathVariable("id") Long id) {
        if (id == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Ship>(shipService.getShipById(id), HttpStatus.OK);
    }

    /**
     * 6. получать отфильтрованный список кораблей в соответствии с переданными
     * фильтрами;
     *
     * @param name        - Имя корабля
     * @param planet      - Название планеты
     * @param shipType    - Тип корабля
     * @param after       - Период создания "от"
     * @param before      - Период создания "до"
     * @param isUsed      - Новый или старый
     * @param minSpeed    - Минимальная скорость
     * @param maxSpeed    - Максимальная скорость
     * @param minCrewSize - Минимальное число экипажа
     * @param maxCrewSize - Максимальное число экипажа
     * @param minRating   - Минимальный рейтинг
     * @param maxRating   - Максимальный рейтинг
     * @param order       - Заказа
     * @param pageNumber  - Число страниц
     * @param pageSize    - Размер страницы
     * @return - Возвращает ResponseEntity<Ship>
     */
    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    public ResponseEntity<?> getShipsArrayFilter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating,
            @RequestParam(value = "order", defaultValue = "ID") ShipOrder order,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize
    ) {
        // Определяем пагинацию
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));

        // Создаем спицификацию для просеивания списка кораблей
        Specification<Ship> specification = ShipSpecification.getSpecification(
                name,
                planet,
                shipType,
                after,
                before,
                isUsed,
                minSpeed,
                maxSpeed,
                minCrewSize,
                maxCrewSize,
                minRating,
                maxRating);
        return new ResponseEntity<>(shipService.getAllShips(specification, pageRequest).getContent(), HttpStatus.OK);
    }

    /**
     * Меод который обрабатывает запрос на вывод списка кораблей по фильтру
     *
     * @param name        - Имя корабля
     * @param planet      - Название планеты
     * @param shipType    - Тип корабля
     * @param after       - Период создания "от"
     * @param before      - Период создания "до"
     * @param isUsed      - Новый или старый
     * @param minSpeed    - Минимальная скорость
     * @param maxSpeed    - Максимальная скорость
     * @param minCrewSize - Минимальное число экипажа
     * @param maxCrewSize - Максимальное число экипажа
     * @param minRating   - Минимальный рейтинг
     * @param maxRating   - Максимальный рейтинг
     * @return возвращает список кораблей
     */
    //7. получать количество кораблей, которые соответствуют фильтрам.
    @RequestMapping(value = "/ships/count", method = RequestMethod.GET)
    public ResponseEntity<?> getShipCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "planet", required = false) String planet,
            @RequestParam(value = "shipType", required = false) ShipType shipType,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "isUsed", required = false) Boolean isUsed,
            @RequestParam(value = "minSpeed", required = false) Double minSpeed,
            @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(value = "minRating", required = false) Double minRating,
            @RequestParam(value = "maxRating", required = false) Double maxRating
    ) {
        // Создаем спицификацию для просеивания списка кораблей
        Specification<Ship> specification = ShipSpecification.getSpecification(
                name,
                planet,
                shipType,
                after,
                before,
                isUsed,
                minSpeed,
                maxSpeed,
                minCrewSize,
                maxCrewSize,
                minRating,
                maxRating);

        return new ResponseEntity<>(shipService.getQuantityShip(specification), HttpStatus.OK);
    }

}
