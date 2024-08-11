package task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Consumer;

public class FileProcessor {
	public void processFile(String filePath, Consumer<String> lineProcessor) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineProcessor.accept(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
