package educanet;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    private float x;
    private float y;
    private float z;

    public float[] vertices;

    private float[] fuckingcolors = {
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };

    private float[] colorSquareGreen = { // green green whats your problem green
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
    };

    private float[] colorSquareRed = { // red
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
    };

    private float[] colorSquareWhite = { // unused
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };

    private final int[] indices = {
            0, 1, 3, // First triangle
            1, 2, 3 // Second triangle
    };

    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int squareColorId;
    private FloatBuffer cb;
    private static int uniformMatrixLocation;
    public Matrix4f matrix;
    public FloatBuffer matrixFloatBuffer;

    public Square(float x, float y, float length) {
        this.x = x;
        this.y = y;
        this.z = length;
        float[] vertices = {
                x + length, y, 0.0f, // 0 -> Top right
                x + length, y - length, 0.0f, // 1 -> Bottom right
                x, y - length, 0.0f, // 2 -> Bottom left
                x, y, 0.0f, // 3 -> Top left
        };
        matrix = new Matrix4f()
                .identity()
                .translate(0.25f, 0.25f, 0.25f);
        // 4x4 -> FloatBuffer of size 16
        matrixFloatBuffer = BufferUtils.createFloatBuffer(16);

        this.vertices = vertices;
        cb = BufferUtils.createFloatBuffer(colorSquareGreen.length).put(colorSquareGreen).flip();

        // Generate all the ids
        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();

        // Get uniform location
        uniformMatrixLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "matrix");

        // Tell OpenGL we are currently using this object (vaoId)
        GL33.glBindVertexArray(squareVaoId);

        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        // colors
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        FloatBuffer cb = BufferUtils.createFloatBuffer(fuckingcolors.length)
                .put(fuckingcolors)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        // Change to VBOs...
        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glUseProgram(educanet.Shaders.shaderProgramId);

        // Sending Mat4 to GPU
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(cb);
        MemoryUtil.memFree(ib);
    }

    public void render() {
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
        GL33.glUseProgram(Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public void greenLight(){
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        cb = BufferUtils.createFloatBuffer(colorSquareRed.length).put(colorSquareRed).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);
        MemoryUtil.memFree(cb);
    }

    public void redLight(){
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        cb = BufferUtils.createFloatBuffer(colorSquareGreen.length).put(colorSquareGreen).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);
        MemoryUtil.memFree(cb);
    }

    private static float xSpeed = (float) 0.01;
    private static float ySpeed = (float) 0.01;

    public void update(long window) {

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) { //dopredu
            matrix = matrix.translate(0f, ySpeed, 0f);
            this.y = y + ySpeed;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) { //doleva
            matrix = matrix.translate(-xSpeed, 0f, 0f);
            this.x = x - xSpeed;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) { //dozadu
            matrix = matrix.translate(0f, -ySpeed, 0f);
            this.y = y - ySpeed;
        }
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) { //doprava
            matrix = matrix.translate(xSpeed, 0f, 0f);
            this.x = x + xSpeed;
        }

        // TODO: Send to GPU only if position updated
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public float getZ() { return z; }
}