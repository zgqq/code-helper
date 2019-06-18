package github.zgqq.intellij.enhance;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.psi.PsiElement;

public class ConsoleUtils {
    static boolean needLog = false;

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
    
    public static void log(String log) {
        if (needLog) {
            System.out.println(log);
        }
    }
}
