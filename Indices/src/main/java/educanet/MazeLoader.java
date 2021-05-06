package educanet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeLoader {

    public static String Maze1() {
        String loadedmaze = "-0.19999999;-1.0;0.4\n" +
                "0.20000005;-1.0;0.4\n" +
                "0.6;-1.0;0.4\n" +
                "0.6;-0.6;0.4\n" +
                "-1.0;-0.19999999;0.4\n" +
                "-0.6;-0.19999999;0.4\n" +
                "-0.19999999;-0.19999999;0.4\n" +
                "0.6;-0.19999999;0.4\n" +
                "-1.0;0.20000005;0.4\n" +
                "-1.0;0.6;0.4\n" +
                "-0.6;0.6;0.4\n" +
                "-0.19999999;0.6;0.4";
/*
        try {
            BufferedReader br = new BufferedReader(new FileReader("lvl1.txt"));

            String readLine = br.readLine();

            for (int i = 0; readLine != null; i++) {
                loadedmaze += readLine + "\n";
                readLine = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return loadedmaze;
    }
}
