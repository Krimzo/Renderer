package utility;

public class Timer {
    private long elapsedStart;
    private long deltaStart;
    private long deltaEnd;

    public Timer() {
        final long currTime = System.nanoTime();
        elapsedStart = currTime;
        deltaStart = currTime;
        deltaEnd = currTime;
    }

    public void reset() {
        newElapsedT();
        newDeltaT();
    }

    public void newElapsedT() {
        elapsedStart = System.nanoTime();
    }
    public float getElapsedT() {
        return (System.nanoTime() - elapsedStart) * 1e-9f;
    }

    public void newDeltaT() {
        deltaStart = deltaEnd;
        deltaEnd = System.nanoTime();
    }
    public float getDeltaT() {
        return (deltaEnd - deltaStart) * 1e-9f;
    }
}
