import java.util.*;

// design pattern state is used;
public class Lift {

    private final int maxCapacity;
    private final Collection<Passenger> passengers;
    private LiftState state;
    private Direction direction;
    private int currentFloor;


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


    public static void main(String[] args) {

        Map<Integer, List<Passenger>> map = GeneratePassengers.createPassengers();

        Lift lift = new Lift(5, new FillLiftState(GeneratePassengers.buildingHeight, map));
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
