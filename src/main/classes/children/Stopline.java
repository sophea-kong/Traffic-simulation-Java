package children;

import parents.InanimatedObject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Stopline stopline = (Stopline) o;
        return java.util.Objects.equals(road, stopline.road);
    }

    @Override
    public String toString() {
        return "Stopline{" +
                "id=" + id +
                ", road=" + road +
                '}';
    }
}
