package ezgl;

import math.*;
import renderer.*;
import utility.*;
import window.*;

import static org.lwjgl.opengl.GL46.*;

public class Mesh extends GLRequired implements Drawable {
    private final int vertexCount;
    private final int vao;
    private final int vbo;

    public Mesh(GLContext context, Vertex[] vertices) {
        super(context);
        vertexCount = vertices.length;

        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, toRawData(vertices), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 32, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 32, 20);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    public Mesh(GLContext context, String objFilePath) throws Exception {
        this(context, File.parseObj(objFilePath, true));
    }
    protected void finalize() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
    }

    private float[] toRawData(Vertex[] vertices) {
        float[] buffer = new float[vertices.length * 8];
        for(int i = 0; i < vertices.length; i++) {
            buffer[i * 8] = vertices[i].world.x;
            buffer[i * 8 + 1] = vertices[i].world.y;
            buffer[i * 8 + 2] = vertices[i].world.z;
            buffer[i * 8 + 3] = vertices[i].texture.x;
            buffer[i * 8 + 4] = vertices[i].texture.y;
            buffer[i * 8 + 5] = vertices[i].normal.x;
            buffer[i * 8 + 6] = vertices[i].normal.y;
            buffer[i * 8 + 7] = vertices[i].normal.z;
        }
        return buffer;
    }

    @Override
    public void draw(Shaders shaders) {
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, vertexCount);
        glBindVertexArray(0);
    }
}
