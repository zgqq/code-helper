package github.zgqq.intellij.enhance;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;


public class ShowPsiElementAction extends AnAction {
    
    public static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup("My notification group",
                    NotificationDisplayType.BALLOON, true);
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        PsiElement navigationElement = pe.getNavigationElement();
        
        PsiReference reference = pe.getReference();
        
        String message = "origin:" + getString(pe);
        String message2 = "navigate:" + getString(navigationElement);
        String message3 = "refer:";
        
        if (reference != null) {
            PsiElement resolve = reference.resolve();
            message3 += getString(resolve);
        }
        
        
        showMyMessage(message + message2 + message3);
    }
    
    private String getString(PsiElement pe) {
        String text = pe.getText();
        String name = pe.getClass().getName();
        return "text:" + text + ",name:" + name + ",range:" + pe.getTextRange();
    }
    
    void showMyMessage(String message) {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO.createNotification(message, NotificationType.ERROR);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);
            }
        });
    }
}
