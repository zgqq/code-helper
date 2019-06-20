package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

public class EditorUtils {

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
