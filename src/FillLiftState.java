import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FillLiftState extends CommonLiftState {

    private final Map<Integer, List<Passenger>> waitingPassenger;

    public FillLiftState(int maxFloor, final Map<Integer, List<Passenger>> waitingPassenger) {
        super(maxFloor);
        this.waitingPassenger = waitingPassenger;
    }

    @Override
    public void next(Lift lift) {
//in case there are no passengers on the floor - lift doesn't stop
        if (Objects.isNull(waitingPassenger) || waitingPassenger.isEmpty()) {
            lift.setState(lift.getDirection() == Direction.UP ? new GoUpLiftState(maxFloor, waitingPassenger) : new GoDownLiftState(maxFloor, waitingPassenger));
            return;
        }
// if the last floor has passengers who want to go down
        if (lift.getCurrentFloor() >= maxFloor) {
            fillWaitingPassenger(lift);
            lift.setDirection(Direction.DOWN);
            lift.setState(new GoDownLiftState(maxFloor, waitingPassenger));
        } else {
            fillWaitingPassenger(lift);
            lift.setState(lift.getDirection() == Direction.DOWN ? new GoDownLiftState(maxFloor, waitingPassenger) : new GoUpLiftState(maxFloor, waitingPassenger));
        }
    }

    //lift reaches the floor with waiting passengers and takes them in if it isn't full.
    private void fillWaitingPassenger(Lift lift) {
        if (waitingPassenger.containsKey(lift.getCurrentFloor()) && !lift.isFull()) {
            for (Iterator<Passenger> iterator = waitingPassenger.get(lift.getCurrentFloor()).iterator(); iterator.hasNext(); ) {
                if (!lift.isFull()) {
                    lift.getPassengers().add(iterator.next());
                    iterator.remove();
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "fill state";
    }
}
