package io;

import core.MapData;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {
    public static MapData load(String path) throws FileNotFoundException {
        File mapFile = new File(path);
        Scanner scanner = new Scanner(mapFile);

        int rows = 0;
        int cols = 0;
        int stackCapacity = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty() || line.startsWith(";")) {
                continue;
            }
            String[] parts = line.split(" ");
            rows = Integer.parseInt(parts[0]);
            cols = Integer.parseInt(parts[1]);
            stackCapacity = Integer.parseInt(parts[2]); 
            break;
        }

        char[][] map = new char[rows][cols];
        int currentRow = 0;
        while (scanner.hasNextLine() && currentRow < rows) {
            String line = scanner.nextLine();
            if (line.trim().startsWith(";")) {
                continue;
            }
            map[currentRow] = line.toCharArray();
            currentRow++;
        }

        scanner.close();

        return new MapData(rows, cols, stackCapacity, map);
    }
}
