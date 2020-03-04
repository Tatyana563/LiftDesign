import java.util.*;

public final class GeneratePassengers {
    public static final int MIN_FLOOR = 5;
    public static final int MAX_FLOOR = 21;
    public static final int MIN_PASSENGER = 0;
    public static final int MAX_PASSENGER = 11;
    public static final int buildingHeight = generateRandom(MIN_FLOOR, MAX_FLOOR);
    public static final int numberOfPassengersPerFloor = generateRandom(MIN_PASSENGER, MAX_PASSENGER);
    public static Map<Integer, List<Passenger>> passengerOnFloor = new TreeMap();

    private GeneratePassengers() {
    }

    public final static int generateRandom(int MIN, int MAX) {
        Random floorAmount = new Random();
        int number = floorAmount.nextInt(MAX - MIN) + MIN;
        return number;
    }

    public final static Map<Integer, List<Passenger>> createPassengers() {
        List<Passenger> passengers = new ArrayList<>(numberOfPassengersPerFloor);

        for (int j = 0; j < buildingHeight; j++) {

            for (int i = 0; i < numberOfPassengersPerFloor; i++) {

                int destinationFloor = generateRandom(0, buildingHeight);

                destinationFloor = (destinationFloor == j) ? (destinationFloor == buildingHeight)
                        ? buildingHeight - 1 : (j + 1)
                        : destinationFloor;
                final Passenger passenger = new Passenger(j, destinationFloor);
                passengers.add(passenger);
            }
        }
        for (int j = 0; j < buildingHeight; j++) {
            for (int i = 0; i < passengers.size(); i++) {
                if (j == passengers.get(i).getCurrentFloor()) {
                    List<Passenger> pass = passengerOnFloor.get(j);
                    if (pass == null) {
                        pass = new ArrayList<>();
                    }
                    pass.add(passengers.get(i));
                    passengerOnFloor.put(j, pass);
                }
            }
        }
        return passengerOnFloor;
    }
}
