package educanet;

import org.lwjgl.opengl.GL33;

public class Shaders {
    private static final String vertexShaderSource = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 vertexColors;\n" +
            "uniform mat4 matrix;" +
            "out vec4 fuckingColors;\n" +
            "void main()\n" +
            "{\n" +
            "gl_Position = matrix * vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
            "fuckingColors = vertexColors;\n" +
            "}";

    private static final String fragmentShaderSource = "#version 330 core\n" +
            "out vec4 FragColor;\n" +
            "in vec4 fuckingColors;\n"+
            "void main()\n" +
            "{\n" +
            "FragColor = fuckingColors;\n" +
            "}\n";
    public static int vertexShaderId;
    public static int fragmentShaderId;
    public static int shaderProgramId;

    public static void initShaders() {
        // Generate the shader ids
        vertexShaderId = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        fragmentShaderId = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

        //region: VertexShader
        // Compile the vertexShader
        GL33.glShaderSource(vertexShaderId, vertexShaderSource);
        GL33.glCompileShader(vertexShaderId);

        // Print the log... TODO: Check for errors
        System.out.println(GL33.glGetShaderInfoLog(vertexShaderId));
        //endregion

        //region: FragmentShader
        // Compile the fragmentShader
        GL33.glShaderSource(fragmentShaderId, fragmentShaderSource);
        GL33.glCompileShader(fragmentShaderId);

        // Print the log... TODO: Check for errors
        System.out.println(GL33.glGetShaderInfoLog(fragmentShaderId));
        //endregion

        //region: Shader attachment
        shaderProgramId = GL33.glCreateProgram();
        GL33.glAttachShader(shaderProgramId, vertexShaderId);
        GL33.glAttachShader(shaderProgramId, fragmentShaderId);
        GL33.glLinkProgram(shaderProgramId);

        System.out.println(GL33.glGetProgramInfoLog(shaderProgramId));
        //endregion

        // We don't need them anymore... It's saved on the GPU now
        GL33.glDeleteShader(vertexShaderId);
        GL33.glDeleteShader(fragmentShaderId);
    }

}
