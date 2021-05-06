package educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        String loadedmaze = MazeLoader.Maze1(); // epic
        String[] coords = loadedmaze.split("\n");

        //region: Window init
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        // TODO: Imagine using a mac lmaomalalmao couldn't be me omegalul

        // Create the window...
        // We can set multiple options with glfwWindowHint ie. fullscreen, resizability etc.
        long window = GLFW.glfwCreateWindow(800, 600, "TEN CTVEREC JE ZIVEJ A MENI BARVY KDYZ KOLIDUJE AAAAAA", 0, 0);
        if(window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Něco si hodně hodně hodně moc dosral nebo si se přepsal");
        }
        GLFW.glfwMakeContextCurrent(window);

        // Tell GLFW, that we are using OpenGL
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        // Resize callback
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });
        //endregion

        // Main game loop
        //Game.init(window); // you just lost the game

        // Setup shaders
        Shaders.initShaders();

        ArrayList<Square> squArrayList = new ArrayList<>();
        for (int i = 0; i < coords.length; i++) {
            String[] singleCoord = coords[i].split(";");
            Square newSquare = new Square(
                    Float.parseFloat(singleCoord[0]),// x
                    Float.parseFloat(singleCoord[1]),// y
                    Float.parseFloat(singleCoord[2]) // z
            );
            squArrayList.add(newSquare);
        }

        System.out.println(loadedmaze); // prints out the loadedmaze because why not

        Square gamer = new Square(0f, 0f, 0.25f);

        while (!GLFW.glfwWindowShouldClose(window)) {
            // Key input management
            if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true); // Send a shutdown signal...

            // Change the background color
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            boolean isTouching = false; // 👉👈

            //render part
            for (int i = 0; i < squArrayList.size(); i++) {
                squArrayList.get(i).render(); // square renderer
            }

            //collision part
            for (int i = 0; i < squArrayList.size(); i++) {
                if(collisionCheck(gamer, squArrayList.get(i))) {
                    isTouching = true;
                }
            }

            gamer.update(window);
            gamer.render();
            if(isTouching) {
                gamer.redLight();
            } else {
                gamer.greenLight();
            }
            // Swap the color buffer -> screen tearing solution
            GLFW.glfwSwapBuffers(window);
            // Listen to input
            GLFW.glfwPollEvents();
        }

        // Don't forget to cleanup
        GLFW.glfwTerminate();
    }

    public static boolean collisionCheck(Square a, Square b) {
        return (a.getX() + a.getZ() > b.getX() &&
                a.getX() < b.getX() + b.getZ() &&
                a.getY() + a.getZ() / 2 + a.getZ() > b.getY() &&
                a.getY() + a.getZ() / 2 < b.getY() + b.getZ());
    }

}
