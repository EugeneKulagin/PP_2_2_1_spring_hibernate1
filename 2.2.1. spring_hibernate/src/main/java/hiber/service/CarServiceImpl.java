package hiber.service;

import hiber.dao.CarDao;
import hiber.model.Car;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Transactional
    @Override
    public void add(Car car) {
        carDao.add(car);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Car> listCars() {
        return carDao.listCars();
    }
}