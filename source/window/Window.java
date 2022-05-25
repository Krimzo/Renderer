package window;

import math.Int2;
import org.lwjgl.opengl.GL;
import window.callbacks.*;
import window.input.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public final long window;
    public final Keyboard keyboard;
    public final Mouse mouse;

    public Window(int width, int height, String title, boolean resizable) {
        if (!glfwInit()) {
            throw new Error("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        if ((window = glfwCreateWindow(width, height, title, NULL, NULL)) == NULL) {
            throw new Error("Failed to create a GLFW window");
        }
        keyboard = new Keyboard(window);
        mouse = new Mouse(window);
    }
    public GLContext initGL() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        return new GLContext();
    }

    public StageCallback start = () -> {};
    public StageCallback update = () -> {};
    public StageCallback end = () -> {};
    public void run() throws Exception {
        show();
        start.method();
        while(!glfwWindowShouldClose(window)) {
            keyboard.update();
            mouse.update();
            update.method();
            glfwPollEvents();
        }
        end.method();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
    public void stop() {
        glfwSetWindowShouldClose(window, true);
    }

    public void setResizeCB(ResizeCallback callback) {
        glfwSetFramebufferSizeCallback(window, (long window, int width, int height) -> {
            callback.method(new Int2(width, height));
        });
    }

    public Int2 getSize() {
        int[] sizeX = new int[1];
        int[] sizeY = new int[1];
        glfwGetWindowSize(window, sizeX, sizeY);
        return new Int2(sizeX[0], sizeY[0]);
    }
    public Int2 getPosition() {
        int[] posX = new int[1];
        int[] posY = new int[1];
        glfwGetWindowPos(window, posX, posY);
        return new Int2(posX[0], posY[0]);
    }

    public void hide() {
        glfwHideWindow(window);
    }
    public void show() {
        glfwShowWindow(window);
    }

    public void swap() {
        glfwSwapBuffers(window);
    }

    public void setTitle(String text) {
        glfwSetWindowTitle(window, text);
    }
    public void setVSync(boolean enabled) {
        glfwSwapInterval(enabled ? 1 : 0);
    }
}
