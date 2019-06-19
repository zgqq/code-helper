package github.zgqq.intellij.enhance;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.ui.content.Content;

public class ConsoleUtils {

    static boolean needLog = true;
    private static final Logger logger = Logger.getInstance(ConsoleUtils.class);

    public static void logCaret(CaretModel caretModel) {
        if (needLog && caretModel != null) {
            System.out.println("------------");
            System.out.println("tag: caret");
            System.out.println("offset:" + caretModel.getOffset());
            System.out.println("currentOffset:" + caretModel.getCurrentCaret().getOffset());
            System.out.println("------------");
        }
    }

    public static void log(String tag, PsiElement psiElement) {
        if (needLog && psiElement != null) {
            System.out.println("------------");
            System.out.println("tag:" + tag);
            System.out.println("class:" + psiElement.getClass().getCanonicalName());
            System.out.println("textOffset:" + psiElement.getTextOffset());
            System.out.println("textRange:" + psiElement.getTextRange());
            System.out.println("text:" + psiElement.getText());
            System.out.println("file:" + psiElement.getContainingFile()
                    .getVirtualFile().getCanonicalPath());
            System.out.println("------------");
        }
    }

    public static void print(String tag,
                             PsiElement psiElement, AnActionEvent e) {
        log(tag, psiElement);
        ToolWindow toolWindow = ToolWindowManager
                .getInstance(e.getProject()).getToolWindow("ShowPsiElement");
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(e.getProject()).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(),
                "ShowPsiElement Output", false);
        toolWindow.getContentManager().addContent(content);


        consoleView.print("Hello from MyPlugin!", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("------------", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("tag:" + tag, ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("class:" + psiElement.getClass().getCanonicalName(), ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("textOffset:" + psiElement.getTextOffset(), ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("textRange:" + psiElement.getTextRange(), ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("text:" + psiElement.getText(), ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("file:" + psiElement.getContainingFile().getVirtualFile().getCanonicalPath(), ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("------------", ConsoleViewContentType.NORMAL_OUTPUT);
    }


    public static void log(String log) {
        if (needLog) {
            System.out.println(log);
        }
    }
}
