package entity;

import ezgl.Mesh;
import ezgl.Texture;
import renderer.Renderable;

public class Entity extends Renderable {
    public Entity() {
        super(null, null);
    }
    public Entity(Mesh mesh, Texture texture) {
        super(mesh, texture);
    }

}
