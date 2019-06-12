package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;

public class CommonUtils {
    
    public static Editor getEditorFrom(AnActionEvent e) {
        return e.getData(PlatformDataKeys.EDITOR);
    }
}
