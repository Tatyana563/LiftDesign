import java.util.Objects;

public class Passenger {

    private final int currentFloor;
    private final int destinationFloor;

    public Passenger(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return currentFloor == passenger.currentFloor &&
                destinationFloor == passenger.destinationFloor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentFloor, destinationFloor);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "currentFloor=" + currentFloor +
                ", destinationFloor=" + destinationFloor +
                '}';
    }
}
