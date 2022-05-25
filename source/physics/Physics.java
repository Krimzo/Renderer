package physics;

import java.util.ArrayList;

public class Physics {
    public ArrayList<Physical> objects = new ArrayList<>();

    public Physics() {}

    public void update(float deltaT) {
        for (Physical obj : objects) {
            obj.update(deltaT);
        }
    }
}
