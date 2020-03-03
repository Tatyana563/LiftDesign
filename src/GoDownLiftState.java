import java.util.List;
import java.util.Map;

public class GoDownLiftState extends CommonLiftState {

    private final Map<Integer, List<Passenger>> waitingPassenger;
    public GoDownLiftState(int maxFloor, final Map<Integer, List<Passenger>> waitingPassenger) {
        super(maxFloor);
        this.waitingPassenger = waitingPassenger;
    }

    @Override
    public void next(Lift lift) {
        //lift reaches the first floor and must change direction, passengers get out, then new come in.
        if (lift.getCurrentFloor()<=0) {
            lift.setCurrentFloor(lift.getCurrentFloor()+1);
            lift.setDirection(Direction.UP);
            lift.setState(new OutLiftState(maxFloor, waitingPassenger, true));
        } else {
            lift.setCurrentFloor(lift.getCurrentFloor()-1);
            lift.setDirection(Direction.DOWN);
            lift.setState(new OutLiftState(maxFloor, waitingPassenger));
        }

    }

    @Override
    public String toString() {
        return "go down state";
    }
}
