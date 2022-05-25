package window;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private final long window;

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
    }
    public GLContext initGL() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        return new GLContext();
    }

    public StageMethod start = () -> {};
    public StageMethod update = () -> {};
    public StageMethod end = () -> {};
    public void run() throws Exception {
        show();
        start.method();
        while(!glfwWindowShouldClose(window)) {
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
