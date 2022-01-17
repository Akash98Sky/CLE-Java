import src.Editor;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <file_path>");
            System.exit(1);
        }

        final String filePath = args[0];

        Editor editor = new Editor();

        editor.openFile(filePath);

        editor.startRender();
    }
}