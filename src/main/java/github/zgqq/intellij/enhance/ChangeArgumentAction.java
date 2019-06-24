package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public class ChangeArgumentAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        final Document document = editor.getDocument();
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        final int offset = editor.getCaretModel().getOffset();
        PsiElement pe = data.findElementAt(offset);
        ConsoleUtils.log("pe", pe);
        ConsoleUtils.log("parent", pe.getParent());

        ConsoleUtils.logCaret(editor.getCaretModel());
        PsiExpressionList psiExpressionList = PsiTreeUtil.getParentOfType(pe, PsiExpressionList.class);


        PsiExpression currentArg = null;
        if (psiExpressionList != null) {

//            PsiExpressionList parentExpressionList = PsiTreeUtil.getParentOfType(psiExpressionList, PsiExpressionList.class);
//            if (parentExpressionList != null) {
//                psiExpressionList = parentExpressionList;
//            }

            for (PsiExpression expression : psiExpressionList.getExpressions()) {
                final PsiElement[] children = expression.getChildren();
                ConsoleUtils.log("arg", expression);
                final PsiElement parentElement = PsiUtils.findParentElement(children, pe);
                if (parentElement != null) {
                    currentArg = expression;
                    ConsoleUtils.log("argEle", expression);
                    ConsoleUtils.log("argChildEle", parentElement);
                }
            }

            if (currentArg == null && psiExpressionList.getExpressionCount() > 0) {
                final PsiExpression mostNearBeforeElement = PsiUtils.getMostNearBeforeElement(Arrays.asList(psiExpressionList.getExpressions()),
                        pe.getTextOffset());
                currentArg = mostNearBeforeElement;
            }
        } else {
            final PsiStatement parentOfType = PsiTreeUtil.getParentOfType(pe, PsiStatement.class);
            final Collection<PsiExpressionList> childrenOfType = PsiTreeUtil.findChildrenOfType(parentOfType, PsiExpressionList.class);

            psiExpressionList = PsiUtils.findChildAfterOffset(childrenOfType, offset);


            if (psiExpressionList != null && psiExpressionList.getExpressionCount() > 0) {
                currentArg = psiExpressionList.getExpressions()[0];
                ConsoleUtils.log("argEle", currentArg);
            }
        }

        if (currentArg != null) {
            final TextRange textRange = currentArg.getTextRange();
            WriteCommandAction.runWriteCommandAction(editor.getProject(),
                    () -> {
                        document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
                        editor.getCaretModel().moveToOffset(textRange.getStartOffset());
                    });
        }
    }
}
