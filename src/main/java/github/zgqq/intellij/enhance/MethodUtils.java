package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class MethodUtils {
    
    public static void selectCurrentMethod(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
    
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        CaretModel caretModel = editor.getCaretModel();
        Caret currentCaret = caretModel.getCurrentCaret();
    
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        PsiMethod containingMethod = PsiTreeUtil.getParentOfType(pe, PsiMethod.class);
    
        if (containingMethod != null) {
            pe = containingMethod;
        }
    
        if (pe instanceof PsiAnnotation) {
            ActionUtils.execute("MethodDown");
        } else if (pe instanceof PsiMethod) {
            PsiMethod m = (PsiMethod) pe;
            int startOffset = m.getNameIdentifier().getTextRange().getStartOffset();
            currentCaret.moveToOffset(startOffset);
        }
    
        IdeaVIMUtils.pressVimKeys(editor, 'v');
        ActionUtils.execute("EditorSelectWord");
        ActionUtils.execute("EditorSelectWord");
    
    }
    
}
