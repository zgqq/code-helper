package com.github.zgqq.codehelper;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;

public class WriteActionUtils {

    public static void run(AnActionEvent e, WriteEditorAction writeEditorAction) {
        Editor editor = CommonUtils.getEditorFrom(e);
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
                () -> {
                    writeEditorAction.run(editor);
                });
    }
}
