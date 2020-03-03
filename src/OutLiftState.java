import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OutLiftState extends CommonLiftState {

    private final Map<Integer, List<Passenger>> waitingPassenger;


    public OutLiftState(int maxFloor, final Map<Integer, List<Passenger>> waitingPassenger) {
        super(maxFloor);
        this.waitingPassenger = waitingPassenger;
    }

    @Override
    public void next(Lift lift) {
        if (Objects.isNull(lift.getPassengers()) || lift.getPassengers().isEmpty()) {
            lift.setState(new FillLiftState(maxFloor, waitingPassenger));
            return;
        }
        outPassenger(lift);
        lift.setState(new FillLiftState(maxFloor, waitingPassenger));
    }

    private void outPassenger(Lift lift) {
        for (Iterator<Passenger> iterator = lift.getPassengers().iterator(); iterator.hasNext(); ) {
            final Passenger passenger = iterator.next();
            if (passenger.getDestinationFloor() == lift.getCurrentFloor()) {
                iterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        return "out state";
    }
}
