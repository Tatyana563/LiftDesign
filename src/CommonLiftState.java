public abstract class CommonLiftState implements LiftState{

    protected final int maxFloor;
    protected Direction direction;

    protected CommonLiftState(int maxFloor) {
        this.maxFloor = maxFloor;
        this.direction = Direction.UP;
    }

}
