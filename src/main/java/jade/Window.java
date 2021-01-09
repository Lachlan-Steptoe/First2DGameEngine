package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private float r, g, b, a;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static Window get(){
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        init();
        loop();

        // Free the memory on closure
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Close GLFW
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        //incase of error
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GFLW.");
        }
        //Configure GFLW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create the Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback (glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        //Make the OpenGl context current
        glfwMakeContextCurrent(glfwWindow);
        //Enable V-sync
        glfwSwapInterval(1);

        //Make the window visable
        glfwShowWindow(glfwWindow);

        //This line is critical for LWJGL's intercoperation with GLFW
        //OpenGL context, or any context that is mangaged externally
        //Lwjgl detects the context that is current in the current thread
        //creates the GLCapabilities instance and makes the OPENGL
        //bindings are availabe for use
        GL.createCapabilities();
    }


    public void loop(){
        while (!glfwWindowShouldClose(glfwWindow))
        {
            glfwPollEvents();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
