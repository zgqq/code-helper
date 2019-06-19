package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class CopyWholeMethodAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final PsiElement methodPsiElement = MethodUtils.getCurrentMethodPsiElement(e);
        ClipboardUtils.setContents(methodPsiElement.getText());
    }
}
