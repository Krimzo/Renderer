package entity;

import ezgl.*;
import math.*;
import physics.*;
import renderer.*;

public class Entity implements Physical, Drawable {
    public Float3 scale = new Float3(1.0f);
    public Float3 rotation = new Float3();
    public Float3 position = new Float3();

    public Float3 acceleration = new Float3();
    public Float3 velocity = new Float3();
    public Float3 angular = new Float3();

    public Mesh mesh;
    public Texture texture;

    public Entity(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;
    }

    public Mat4 matrix() {
        return Mat4.translate(position).mul(Mat4.rotate(rotation)).mul(Mat4.scale(scale));
    }

    @Override
    public void update(float deltaT) {
        velocity = velocity.add(acceleration.mul(deltaT));
        position = position.add(velocity.mul(deltaT));
        rotation = rotation.add(angular.mul(deltaT));
    }

    @Override
    public void draw(Shaders shaders) {
        shaders.setUniform("W", matrix());
        texture.bind(0);
        mesh.draw(shaders);
    }
}
