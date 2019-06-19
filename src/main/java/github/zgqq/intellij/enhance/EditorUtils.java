package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

public class EditorUtils {

    public static void deleteText(AnActionEvent e, PsiElement psiElement) {
        WriteActionUtils.run(e, document -> {
            final TextRange textRange = psiElement.getTextRange();
            document.deleteString(textRange.getStartOffset(),
                    textRange.getEndOffset()
            );
        });
    }

}
