import ezgl.*;
import math.*;
import utility.*;
import window.*;
import renderer.*;

import java.awt.Color;

import static org.lwjgl.opengl.GL33.*;

public class Main {
    private static final Window window = new Window();
    private static final Renderer renderer = new Renderer();
    private static final Timer timer = new Timer();

    public static void start() throws Exception {
        Mesh testMesh = new Mesh(new Vertex[] {
                new Vertex(new Float3(-0.5f, -0.5f, 0.5f), new Float2(0.0f)), new Vertex(new Float3(-0.5f, 0.5f, 0.5f), new Float2(0.0f, 1.0f)), new Vertex(new Float3(0.5f, 0.5f, 0.5f), new Float2(1.0f)),
                new Vertex(new Float3(-0.5f, -0.5f, 0.5f), new Float2(0.0f)), new Vertex(new Float3(0.5f, -0.5f, 0.5f), new Float2(1.0f, 0.0f)), new Vertex(new Float3(0.5f, 0.5f, 0.5f), new Float2(1.0f))
        });
        Texture testTexture = new Texture("resource/textures/dogo.png");
        renderer.renderables.add(new Renderable(testMesh, testTexture));

        renderer.shaders = new Shaders(
                File.parseShader("shaders/Default.glsl", GL_VERTEX_SHADER),
                File.parseShader("shaders/Default.glsl", GL_FRAGMENT_SHADER)
        );
        renderer.shaders.setUniform("colorMap", 0);

        renderer.setClearColor(new Color(50, 50, 50));
        window.setVSync(true);
        timer.reset();
    }

    public static void update() {
        timer.newDeltaT();
        renderer.clear(true);
        renderer.draw();
        window.swap();
        window.setTitle(String.valueOf((int)(1.0f / timer.getDeltaT())));
    }

    public static void end() {

    }

    public static void main(String[] args) throws Exception {
        window.start = Main::start;
        window.update = Main::update;
        window.end = Main::end;
        window.run(800, 450, "Test", true);
    }
}
