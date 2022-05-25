package window.input;

import math.Int2;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private final long window;
    public Int2 position = new Int2();
    public int scroll = 0;
    public Key lmb = new Key();
    public Key mmb = new Key();
    public Key rmb = new Key();

    public Mouse(long window) {
        this.window = window;
        glfwSetScrollCallback(window, (long win, double deltaStart, double deltaEnd) -> {
            scroll = (int)(deltaEnd - deltaStart);
        });
    }

    public void hide() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
    public void show() {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void move(Int2 pos) {
        glfwSetCursorPos(window, pos.x, pos.y);
    }

    public void update() {
        double[] posX = new double[1]; double[] posY = new double[1];
        glfwGetCursorPos(window, posX, posY);
        position = new Int2((int)posX[0], (int)posY[0]);
        lmb.update(Key.getMouseState(window, GLFW_MOUSE_BUTTON_LEFT));
        mmb.update(Key.getMouseState(window, GLFW_MOUSE_BUTTON_MIDDLE));
        rmb.update(Key.getMouseState(window, GLFW_MOUSE_BUTTON_RIGHT));
    }
}
