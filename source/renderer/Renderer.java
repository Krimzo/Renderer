package renderer;

import entity.*;
import ezgl.*;

import java.util.ArrayList;

public class Renderer {
    public Shaders shaders = null;
    public Camera camera = new Camera();
    public ArrayList<Drawable> objects = new ArrayList<>();

    public Renderer() {}

    public void render() {
        shaders.setUniform("VP", camera.matrix());
        for (Drawable obj : objects) {
            obj.draw(shaders);
        }
    }
}
