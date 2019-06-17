package github.zgqq.intellij.enhance;


import com.intellij.codeInsight.template.macro.MacroUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NotNull;

public class GoDeclarationByVariableAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        final PsiVariable[] vars = MacroUtil.getVariablesVisibleAt(pe, "from");
        for (PsiVariable var : vars) {
            ConsoleUtils.log("var", var);
        }

        final PsiElement parent = pe.getParent();
        PsiClass variableClass = null;
        if (parent instanceof PsiReferenceExpression) {
            PsiReferenceExpression expression = (PsiReferenceExpression) parent;
            final PsiType type = expression.getType();
            variableClass = PsiTypesUtil.getPsiClass(type);
        } else if (parent instanceof PsiVariable) {
            PsiVariable psiVariable = (PsiVariable) parent;
            variableClass = PsiTypesUtil.getPsiClass(psiVariable.getType());
        }

        PsiUtils.navigate(variableClass);
    }
}
