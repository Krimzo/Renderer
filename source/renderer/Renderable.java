package renderer;

import ezgl.Mesh;
import ezgl.Texture;

public class Renderable {
    public Mesh mesh;
    public Texture texture;

    public Renderable(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;
    }

    public void draw() {
        texture.bind(0);
        mesh.draw();
    }
}
