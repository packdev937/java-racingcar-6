package racingcar.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import racingcar.response.RaceResultResponse;

public class Cars {

    private final int CARS_INPUT_CRITERION = 2;
    private final List<Car> cars;

    public Cars(List<String> carNames) {
        validate(carNames);
        this.cars = carNames.stream()
            .map(Car::fromName)
            .collect(Collectors.toList());
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public void moveAllCars() {
        cars.forEach(car -> car.moveForward(RandomNumber.generateRandomNumber()));
    }

    public int getMaxPosition() {
        return cars.stream()
            .mapToInt(Car::getPosition)
            .max()
            .orElseThrow(() -> new IllegalStateException("Car 리스트가 존재하지 않습니다"));
    }

    public List<String> findWinners() {
        int maxPosition = getMaxPosition();
        return cars.stream()
            .filter(car -> car.getPosition() == maxPosition)
            .map(Car::getName)
            .collect(Collectors.toList());
    }

    public void validate(List<String> carNames) {
        validateCarsSize(carNames);
        validateDuplicatedCarNames(carNames);
    }

    public void validateCarsSize(List<String> carNames) {
        if (carNames.size() < CARS_INPUT_CRITERION) {
            throw new IllegalArgumentException("자동차는 두 대 이상 입력해야 합니다.");
        }
    }

    public void validateDuplicatedCarNames(List<String> carNames) {
        Set<String> uniqueNames = new HashSet<>(carNames);
        if (uniqueNames.size() != carNames.size()) {
            throw new IllegalArgumentException("중복되는 이름이 있으면 안됩니다.");
        }
    }

    public RaceResultResponse toResponse() {
        RaceResultResponse response = new RaceResultResponse();
        cars.forEach(response::updateCarPosition);
        return response;
    }
}
