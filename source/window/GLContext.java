package window;

import math.*;

import java.awt.*;

import static org.lwjgl.opengl.GL41.*;

public class GLContext {
    protected GLContext() {}

    public void setViewport(Int2 pos, Int2 size) {
        glViewport(pos.x, pos.y, size.x, size.y);
    }

    public void setClearColor(Color color) {
        final float toFlt = 1.0f / 255.0f;
        glClearColor(color.getRed() * toFlt, color.getGreen() * toFlt, color.getBlue() * toFlt, color.getAlpha() * toFlt);
    }
    public void clear(boolean depth) {
        glClear(GL_COLOR_BUFFER_BIT | (depth ? GL_DEPTH_BUFFER_BIT : 0));
    }

    public void setDepthTest(boolean enabled) {
        if (enabled) {
            glEnable(GL_DEPTH_TEST);
        }
        else {
            glDisable(GL_DEPTH_TEST);
        }
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
}
