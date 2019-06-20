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
import org.jetbrains.annotations.NotNull;

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
                             PsiElement psiElement,
                             ConsoleView consoleView) {
        log(tag, psiElement);
        consoleView.print("------------\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("tag:" + tag + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("class:" + psiElement.getClass().getCanonicalName() + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("textOffset:" + psiElement.getTextOffset() + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("textRange:" + psiElement.getTextRange() + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("text:" + psiElement.getText() + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("file:" + psiElement.getContainingFile().getVirtualFile().getCanonicalPath() + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("------------\n", ConsoleViewContentType.NORMAL_OUTPUT);
//        consoleView.attachToProcess();
    }

    @NotNull
    public static ConsoleView getConsoleView(AnActionEvent e) {
        ToolWindow toolWindow = ToolWindowManager
                .getInstance(e.getProject()).getToolWindow("Event Log");
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(e.getProject()).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(),
                "ShowPsiElement Output", false);
        toolWindow.getContentManager().addContent(content);
        return consoleView;
    }


    public static void log(String log) {
        if (needLog) {
            System.out.println(log);
        }
    }
}
