package src;

import src.models.Action;
import src.models.ActionType;
import src.services.ActionBuilder;
import src.services.FileReadWrite;
import src.services.PageEdit;

public class Editor {
    private FileReadWrite fileRW;
    private PageEdit pageEdit;
    private ActionBuilder actionBuilder;
    private boolean render;

    public Editor() {
        this.actionBuilder = new ActionBuilder(a -> { return applyAction(a); });
        this.render = false;
    }

    private Void applyAction(Action action) {
        if(action.getType() == ActionType.SAVE) {
            this.saveFile();
        } else if(action.getType() == ActionType.EXIT) {
            this.closeEditor();
        } else {
            this.pageEdit.applyAction(action);
        }
        
        if(this.render) {
            renderEditView();
        }

        return null;
    }

    private void renderEditView() {
        System.out.print("\033[H\033[2J");
        System.out.println(fileRW.getFileName() + " : ");
        System.out.println(pageEdit.editView(10));

        System.out.println("Cursor: C x y | Insert: I \"text\" | Delete: D | Save: S | Exit: E");
    }

    public void startRender() {
        render = true;
        renderEditView();
    }

    public void stopRender() {
        render = false;
        System.out.print("\033[H\033[2J");
    }

    public void openFile(String fileName) {
        fileRW = new FileReadWrite(fileName);
        pageEdit = new PageEdit(fileRW.readFile());
        actionBuilder.listenToInput();
    }

    public void saveFile() {
        fileRW.writeFile(pageEdit.getPage());
    }

    public void closeEditor() {
        stopRender();
        actionBuilder.close();
        pageEdit.close();
        System.exit(0);
    }
}
