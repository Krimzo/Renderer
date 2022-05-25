import entity.*;
import ezgl.*;
import math.*;
import physics.*;
import utility.*;
import window.*;
import renderer.*;

import java.awt.Color;

import static org.lwjgl.opengl.GL33.*;

public class Main {
    private static final Window window = new Window(800, 450, "Test", true);
    private static final GLContext context = window.initGL();
    private static final Timer timer = new Timer();
    private static final Renderer renderer = new Renderer();
    private static final Physics physics = new Physics();

    public static void start() throws Exception {
        context.setDepthTest(true);
        context.setClearColor(new Color(50, 50, 50));
        window.setVSync(true);

        renderer.shaders = new Shaders(context,
                File.parseShader("shaders/Renderer.glsl", GL_VERTEX_SHADER),
                File.parseShader("shaders/Renderer.glsl", GL_FRAGMENT_SHADER)
        );
        renderer.shaders.setUniform("colorMap", 0);

        Mesh cubeMesh = new Mesh(context, "resource/meshes/cube.obj");
        Texture dogoTexture = new Texture(context, "resource/textures/dogo.png");
        Entity cube1 = new Entity(cubeMesh, dogoTexture);
        cube1.position.z = 5.0f;
        cube1.angular.y = -36.0f;

        renderer.objects.add(cube1);
        physics.objects.add(cube1);

        timer.reset();
    }

    public static void update() {
        timer.newDeltaT();
        physics.update(timer.getDeltaT());
        context.clear(true);
        renderer.render();
        window.swap();
        window.setTitle(String.valueOf((int)(1.0f / timer.getDeltaT())));
    }

    public static void end() {

    }

    public static void main(String[] args) throws Exception {
        window.start = Main::start;
        window.update = Main::update;
        window.end = Main::end;
        window.run();
    }
}
