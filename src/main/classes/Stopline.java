public class Stopline extends InanimatedObject {
    public static int count = 1;
    private Road road;

    public Stopline(Road road) {
        super(0, 0, false, true);
        this.road = road;
    }

    public Stopline(int roadId) {
        super(0, 0, false, true);
        this.road = null;
    }

    public Road getRoad(){
        return this.road;
    }
}
