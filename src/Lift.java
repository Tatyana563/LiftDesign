import java.util.*;

// design pattern state is used;
public class Lift {
    public static final int MIN_FLOOR = 5;
    public static final int MAX_FLOOR = 21;
    public static final int MIN_PASSENGER = 0;
    public static final int MAX_PASSENGER = 11;
    private final int maxCapacity;
    private final Collection<Passenger> passengers;
    private LiftState state;
    private Direction direction;
    private int currentFloor;
    public static final int buildingHeight = generateRandom(MIN_FLOOR, MAX_FLOOR);
    public static final int numberOfPassengersPerFloor = generateRandom(MIN_PASSENGER, MAX_PASSENGER);
    public static Map<Integer, List<Passenger>> passengerOnFloor = new TreeMap();

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Lift(int maxCapacity, LiftState state) {
        this.maxCapacity = maxCapacity;
        this.state = state;
        currentFloor = 0;
        passengers = new ArrayList<>(maxCapacity);
    }

    //delegation
    public void run() {
        state.next(this);
    }

    public boolean isFull() {
        return passengers.size() >= maxCapacity;
    }

    public Collection<Passenger> getPassengers() {
        return passengers;
    }

    public LiftState getState() {
        return state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setState(LiftState state) {
        this.state = state;
    }


    public static int generateRandom(int MIN, int MAX) {
        Random floorAmount = new Random();
        int number = floorAmount.nextInt(MAX - MIN) + MIN;
        return number;
    }

    public static Map<Integer, List<Passenger>> createPassengers() {
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


    public static void main(String[] args) {
        /*  Map<Integer, List<Passenger>> map =createPassengers();
         *//*        map.put(0, new ArrayList<>(Arrays.asList(new Passenger(0, 3), new Passenger(0, 4), new Passenger(0, 10), new Passenger(0, 10), new Passenger(0, 10), new Passenger(0, 10))));
        map.put(1, new ArrayList<>(Arrays.asList(new Passenger(1, 3), new Passenger(1, 4), new Passenger(1, 10))));
        map.put(3, new ArrayList<>(Arrays.asList(new Passenger(3, 0), new Passenger(3, 1), new Passenger(3, 10))));
        map.put(6, new ArrayList<>(Arrays.asList(new Passenger(6, 0), new Passenger(6, 1), new Passenger(6, 8), new Passenger(6, 3))));*//*
         */
        Map<Integer, List<Passenger>> map = createPassengers();
        Lift lift = new Lift(5, new FillLiftState(buildingHeight, map));
        lift.setDirection(Direction.UP);
        lift.setCurrentFloor(0);
        while (true) {
            System.out.println("------current floor = " + lift.getCurrentFloor() + " passenger in lift = "
                    + lift.getPassengers().size() + " waiting passenger = " + map.get(lift.getCurrentFloor())
                    + " state = " + lift.getState());
            lift.run();
            //lift stops work when waiting list is empty+lift's cabin is empty
            if ((map.values() == null || map.values().stream().filter(item -> item.size() != 0).count() == 0)
                    && lift.getPassengers().isEmpty()) {
                System.out.println("------current floor = " + lift.getCurrentFloor() + " passenger in lift = "
                        + lift.getPassengers().size() + " waiting passenger = " + map.get(lift.getCurrentFloor())
                        + " state = " + lift.getState());
                break;
            }
        }
    }
}
