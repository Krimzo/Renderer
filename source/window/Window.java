package window;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long window = NULL;

    public Window() {}

    public interface StageMethod {
        void method() throws Exception;
    }
    public StageMethod start = () -> {};
    public StageMethod update = () -> {};
    public StageMethod end = () -> {};

    private void init(int width, int height, String title, boolean resizable) {
        if (!glfwInit()) {
            throw new Error("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        if ((window = glfwCreateWindow(width, height, title, NULL, NULL)) == NULL) {
            throw new Error("Failed to create a GLFW window");
        }
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }
    public void run(int width, int height, String title, boolean resizable) throws Exception {
        init(width, height, title, resizable);
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
    public void close() {
        glfwSetWindowShouldClose(window, true);
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
