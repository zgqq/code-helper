package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;

public class CommonUtils {
    
    public static LogicalPosition getLogicalPosition(CaretModel caretModel) {
        Caret currentCaret = caretModel.getCurrentCaret();
        LogicalPosition logicalPosition = currentCaret.getLogicalPosition();
        return logicalPosition;
    }
    
    public static Editor getEditorFrom(AnActionEvent e) {
        return e.getData(PlatformDataKeys.EDITOR);
    }
}
