package renderer;

import ezgl.*;

import java.awt.Color;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {
    public Shaders shaders = null;
    public ArrayList<Renderable> renderables = new ArrayList<>();

    public Renderer() {}

    public void setClearColor(Color color) {
        final float toFlt = 1.0f / 255.0f;
        glClearColor(color.getRed() * toFlt, color.getGreen() * toFlt, color.getBlue() * toFlt, color.getAlpha() * toFlt);
    }
    public void setDepthTest(boolean enabled) {
        if (enabled) {
            glEnable(GL_DEPTH_TEST);
        }
        else {
            glDisable(GL_DEPTH_TEST);
        }
    }
    public void clear(boolean depth) {
        glClear(GL_COLOR_BUFFER_BIT | (depth ? GL_DEPTH_BUFFER_BIT : 0));
    }

    public void setWireframe(boolean wireframe) {
        glPolygonMode(GL_FRONT_AND_BACK, wireframe ? GL_LINE : GL_FILL);
    }
    public void setCull(boolean enabled, boolean cullBack) {
        if (enabled) {
            glEnable(GL_CULL_FACE);
        }
        else {
            glDisable(GL_CULL_FACE);
        }
        glCullFace(cullBack ? GL_BACK : GL_FRONT);
    }
    public void draw() {
        shaders.bind();
        for (Renderable renderable : renderables) {
            renderable.draw();
        }
    }
}
