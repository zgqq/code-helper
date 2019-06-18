package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class ShowParentAction extends AnAction  {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        ConsoleUtils.log("if", pe);
        ConsoleUtils.log("if2", pe.getParent());
        ConsoleUtils.log("if3", pe.getParent().getParent());
        ConsoleUtils.log("if4", pe.getParent().getParent().getParent());
        ConsoleUtils.log("if5", pe.getParent().getParent().getParent().getParent());
        ConsoleUtils.log("if6", pe.getParent().getParent().getParent().getParent().getParent());
    }
}
