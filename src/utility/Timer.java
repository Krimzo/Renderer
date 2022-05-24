package utility;

public class Timer {
    private float elapsedStart;
    private float deltaStart;
    private float deltaEnd;

    public Timer() {
        final float currTime = getTime();
        elapsedStart = currTime;
        deltaStart = currTime;
        deltaEnd = currTime;
    }

    private float getTime() {
        return System.nanoTime() * 1e-9f;
    }

    public void reset() {
        newElapsedT();
        newDeltaT();
    }

    public void newElapsedT() {
        elapsedStart = getTime();
    }
    public float getElapsedT() {
        return getTime() - elapsedStart;
    }

    public void newDeltaT() {
        deltaStart = deltaEnd;
        deltaEnd = getTime();
    }
    public float getDeltaT() {
        return deltaEnd - deltaStart;
    }
}
