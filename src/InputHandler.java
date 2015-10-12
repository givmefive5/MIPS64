import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InputHandler {

	private static String input;

	public static void setInput(File f) throws IOException {
		input = new String(Files.readAllBytes(f.toPath()));
		System.out.println(input);
	}

	public static void setInput(String text) {
		input = text;
	}
}
