package window.input;

import window.callbacks.*;

import static org.lwjgl.glfw.GLFW.*;

public class Key {
    public boolean state = false;
    public KeyCallback press = () -> {};
    public KeyCallback down = () -> {};
    public KeyCallback release = () -> {};

    protected Key() {}

    protected static boolean getKeyState(long window, int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }
    protected static boolean getMouseState(long window, int button) {
        return glfwGetMouseButton(window, button) == GLFW_PRESS;
    }

    protected void update(boolean newState) {
        if (state) {
            if (!newState) {
                release.method();
            }
            else {
                down.method();
            }
        }
        else if (newState) {
            press.method();
        }
        state = newState;
    }
}
