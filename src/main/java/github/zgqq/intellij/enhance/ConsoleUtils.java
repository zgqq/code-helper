package github.zgqq.intellij.enhance;

import com.intellij.psi.PsiElement;

public class ConsoleUtils {
    static boolean needLog = true;
    
    
    public static void log(String tag, PsiElement psiElement) {
        if (needLog) {
            System.out.println("------------");
            System.out.println("tag:" + tag);
            System.out.println("class:" + psiElement.getClass().getCanonicalName());
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
