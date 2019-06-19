package github.zgqq.intellij.enhance;

import com.intellij.openapi.ide.CopyPasteManager;

import java.awt.datatransfer.StringSelection;

public class ClipboardUtils {

    public static void setContents(String text) {
        CopyPasteManager instance = CopyPasteManager.getInstance();
        StringSelection stringSelection = new StringSelection(text);
        instance.setContents(stringSelection);
    }
}
