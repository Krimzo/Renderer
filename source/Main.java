import entity.*;
import ezgl.*;
import math.*;
import physics.*;
import utility.*;
import window.*;
import renderer.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.opengl.GL33.*;

public class Main {
    private static final Window window = new Window(1600, 900, "Test", true);
    private static final GLContext context = window.initGL();
    private static final Timer timer = new Timer();
    private static final Renderer renderer = new Renderer();
    private static final Physics physics = new Physics();

    private static Entity lockedEntity = null;
    private static final ArrayList<Entity> entities = new ArrayList<>();
    public static void userStart() throws Exception {
        Mesh cubeMesh = new Mesh(context, "resource/meshes/cube.obj");
        Texture dogoTexture = new Texture(context, "resource/textures/dogo.png");

        final float range = 50.0f;
        final float maxSpin = 180.0f;
        Random rand = new Random();
        for (int i = 0; i < 5000; i++) {
            Entity dogo = new Entity(cubeMesh, dogoTexture);
            dogo.position.x = (rand.nextFloat() * 2.0f - 1.0f) * range;
            dogo.position.y = (rand.nextFloat() * 2.0f - 1.0f) * range;
            dogo.position.z = (rand.nextFloat() * 2.0f - 1.0f) * range;
            dogo.angular.y = (rand.nextFloat() * 2.0f - 1.0f) * maxSpin;
            renderer.objects.add(dogo);
            physics.objects.add(dogo);
            entities.add(dogo);
        }
        renderer.camera.setDefaultMovement(window, timer);

        window.keyboard.space.press = () -> {
            if (lockedEntity != null) {
                lockedEntity = null;
            }
            else {
                float minDistance = Float.MAX_VALUE;
                for (Entity entity : entities) {
                    final float distance = renderer.camera.position.sub(entity.position).len();
                    if (distance < minDistance) {
                        minDistance = distance;
                        lockedEntity = entity;
                    }
                }
            }
        };

        final boolean[] wireframeEnabled = { false };
        window.keyboard.v.press = () -> {
            wireframeEnabled[0] = !wireframeEnabled[0];
            context.setWireframe(wireframeEnabled[0]);
        };
    }
    public static void userUpdate() {
        if (lockedEntity != null) {
            final float ySin = (float) Math.sin(Math.toRadians(lockedEntity.rotation.y));
            final float yCos = (float) Math.cos(Math.toRadians(lockedEntity.rotation.y));
            renderer.camera.position = lockedEntity.position.add(new Float3(-ySin, 2.0f, -yCos));

            Float3 newForward = lockedEntity.position.sub(renderer.camera.position);
            renderer.camera.setForward(new Float3(newForward.x, 0.0f, newForward.z));
        }
    }
    public static void userEnd() {}

    public static void start() throws Exception {
        window.setResizeCB((Int2 newSize) -> context.setViewport(new Int2(), newSize));
        window.setVSync(true);
        context.setDepthTest(true);
        context.setClearColor(new Color(50, 50, 50));
        renderer.shaders = new Shaders(context,
                File.parseShader("shaders/Renderer.glsl", GL_VERTEX_SHADER),
                File.parseShader("shaders/Renderer.glsl", GL_FRAGMENT_SHADER)
        );
        renderer.shaders.setUniform("colorMap", 0);
        userStart();
        timer.reset();
    }

    public static void update() {
        timer.newDeltaT();
        physics.update(timer.getDeltaT());
        context.clear(true);
        userUpdate();
        renderer.render();
        window.swap();
        window.setTitle(String.valueOf((int)(1.0f / timer.getDeltaT())));
    }

    public static void end() {
        userEnd();
    }

    public static void main(String[] args) throws Exception {
        window.start = Main::start;
        window.update = Main::update;
        window.end = Main::end;
        window.run();
    }
}
