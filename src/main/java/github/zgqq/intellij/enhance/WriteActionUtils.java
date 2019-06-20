package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;

public class WriteActionUtils {

    public static void run(AnActionEvent e, WriteAction writeDocument) {
        Editor editor = CommonUtils.getEditorFrom(e);
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
                () -> {
                    writeDocument.run(editor);
                });
    }
}
