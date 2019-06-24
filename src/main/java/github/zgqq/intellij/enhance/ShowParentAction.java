package github.zgqq.intellij.enhance;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.NotNull;

public class ShowParentAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiElement data = e.getData(LangDataKeys.PSI_FILE);

        final ConsoleView consoleView = ConsoleUtils.getConsoleView(e);

        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        ConsoleUtils.print("if", pe, consoleView);
        ConsoleUtils.print("if2", pe.getParent(), consoleView);
        ConsoleUtils.print("if3", pe.getParent().getParent(), consoleView);
        ConsoleUtils.print("if4", pe.getParent().getParent().getParent(), consoleView);
        ConsoleUtils.print("if5", pe.getParent().getParent().getParent().getParent(), consoleView);
        ConsoleUtils.print("if6", pe.getParent().getParent().getParent().getParent().getParent(), consoleView);
    }
}
