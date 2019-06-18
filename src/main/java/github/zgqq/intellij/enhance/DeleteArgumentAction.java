package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class DeleteArgumentAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);

    public PsiElement findParentElement(PsiElement[] children, PsiElement target) {
        if (children == null || children.length == 0) {
            return null;
        }
        for (int i = 0; i < children.length; i++) {
            final PsiElement child = children[i];
            if (child.isEquivalentTo(target)) {
                return child;
            }
            final PsiElement parentElement = findParentElement(child.getChildren(), target);
            if (parentElement != null) {
                return child;
            }
        }
        return null;
    }
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        final Document document = editor.getDocument();
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        ConsoleUtils.log("parent", pe.getParent());


        PsiExpressionList psiExpressionList = PsiTreeUtil.getParentOfType(pe, PsiExpressionList.class);

        PsiExpression currentArg = null;
        if (psiExpressionList != null) {
            for (PsiExpression expression : psiExpressionList.getExpressions()) {
                final PsiElement[] children = expression.getChildren();
                ConsoleUtils.log("arg", expression);
                final PsiElement parentElement = findParentElement(children, pe);
                if (parentElement != null) {
                    currentArg = expression;
                    ConsoleUtils.log("argEle", expression);
                    ConsoleUtils.log("argChildEle", parentElement);
                }
            }
        }

        if (currentArg != null) {
            final TextRange textRange = currentArg.getTextRange();
            WriteCommandAction.runWriteCommandAction(editor.getProject(),
                    () -> {
                        document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
                    });
        }


//        PsiExpression ifStatement = PsiTreeUtil.getParentOfType(pe, PsiExpression.class);
//        ConsoleUtils.log("if",ifStatement);

//        PsiExpression ifStatement2 = PsiTreeUtil.getParentOfType(pe, PsiParenthesizedExpression.class);
//        ConsoleUtils.log("if2",ifStatement2);

//        ConsoleUtils.log("if3", ifStatement.getParent());
//        ConsoleUtils.log("if4", ifStatement.getParent().getParent());
//        ConsoleUtils.log("if5", ifStatement.getParent().getParent().getParent());
//        PsiPolyadicExpression

    }
}
