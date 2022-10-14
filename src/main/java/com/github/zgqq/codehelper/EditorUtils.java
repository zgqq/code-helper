package com.github.zgqq.codehelper;

import com.intellij.codeStyle.CodeStyleFacade;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.util.DocumentUtil;

public class EditorUtils {

    public static void writeIndentText(AnActionEvent e, String text, int offset) {
        WriteActionUtils.run(e, editor -> {
            final Document document = editor.getDocument();
            final int lineNumber = document.getLineNumber(offset);
            final int insertLine = lineNumber + 1;
            final int lineStartOffset1 = document.getLineStartOffset(insertLine);
            final Project project = e.getProject();
            CodeStyleFacade codeStyleFacade = CodeStyleFacade.getInstance(project);
            String indent = codeStyleFacade.getLineIndent(editor, null, lineStartOffset1, true);
            if (DocumentUtil.isLineEmpty(document, insertLine)) {
                document.insertString(lineStartOffset1, indent + text);
            } else {
                document.insertString(lineStartOffset1, indent + text + "\n");
            }
        });
    }

    public static void deleteText(AnActionEvent e, PsiElement psiElement) {
        WriteActionUtils.run(e, editor -> {
            final Document document = editor.getDocument();
            final TextRange textRange = psiElement.getTextRange();
            document.deleteString(textRange.getStartOffset(),
                    textRange.getEndOffset()
            );
        });
    }

    public static void deleteText(AnActionEvent e, PsiElement psiElement, int moveToOffset) {
        WriteActionUtils.run(e, editor -> {
            final TextRange textRange = psiElement.getTextRange();
            final Document document = editor.getDocument();
            document.deleteString(textRange.getStartOffset(),
                    textRange.getEndOffset());
            editor.getCaretModel().moveToOffset(moveToOffset);
        });
    }
}
