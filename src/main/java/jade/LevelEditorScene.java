package jade;

import java.awt.event.KeyEvent;

import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "    #type vertex\n" +
            "    #version 330 core\n" +
            "    layout (location=0) in vec3 aPos;\n" +
            "    layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "    out vec4 fColor;\n" +
            "\n" +
            "    void main()\n" +
            "    {\n" +
            "        fColor = aColor;\n" +
            "        gl_Position = vec4(aPos, 1.0);\n" +
            "    }";
    private String fragmentShaderSrc = "    #type fragment \n" +
            "    #version 330 core\n" +
            "    \n" +
            "    in vec4 fColor;\n" +
            "    \n" +
            "    out vec4 color;\n" +
            "    \n" +
            "    void main()\n" +
            "    {\n" +
            "        color = fColor;\n" +
            "    }";

    private int vertexID, fragmentID, shaderProgram;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        //Compile and link the shaders

        // 1. Load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        //2. Pass the Shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader complimation fail");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_VERTEX_SHADER);
        //2. Pass the Shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation fail");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //Link Shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);
        //Video is stopped at 12.36, The first Square is the title
    }

    @Override
    public void update(float dt) {


    }
}
