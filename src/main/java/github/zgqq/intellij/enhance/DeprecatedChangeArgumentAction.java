package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class DeprecatedChangeArgumentAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        final int offset = editor.getCaretModel().getOffset();
        PsiElement pe = data.findElementAt(offset);
        ConsoleUtils.log("parent", pe.getParent());

        ConsoleUtils.logCaret(editor.getCaretModel());
        PsiExpressionList psiExpressionList = PsiTreeUtil.getParentOfType(pe, PsiExpressionList.class);

        PsiElement currentArg = null;
        if (psiExpressionList != null) {
            for (PsiExpression expression : psiExpressionList.getExpressions()) {
                final PsiElement[] children = expression.getChildren();
                ConsoleUtils.log("arg", expression);
                final PsiElement parentElement = PsiUtils.findParentElement(children, pe);
                currentArg = expression;
                if (parentElement != null) {
                    ConsoleUtils.log("argEle", expression);
                    ConsoleUtils.log("argChildEle", parentElement);
                    PsiElement nextArgument = PsiUtils.findNextArgument(expression, offset);
                    if (nextArgument != null) {
                        currentArg = nextArgument;
                    }
                }
            }
        } else {
            final PsiStatement parentOfType = PsiTreeUtil.getParentOfType(pe, PsiStatement.class);
            currentArg = PsiUtils.findNextArgument(parentOfType, offset);
        }

        if (currentArg != null) {
            final TextRange textRange = currentArg.getTextRange();
            EditorUtils.deleteText(e, currentArg, textRange.getStartOffset());
        }
    }

}