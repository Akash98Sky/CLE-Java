package src.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import src.models.Page;

public class FileReadWrite {
    private String filePath;
    private File file;

    public FileReadWrite(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return file.getName();
    }

    public Page readFile() {
        Page page = new Page();
        if(file.exists()) {
            try {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String s;
                while((s = fileReader.readLine()) != null) {
                    page.appendLine(s);
                }
                fileReader.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return page;
    }

    public void writeFile(Page page) {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(page.getText());
            fileWriter.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
