package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIfStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;

public class CopyIfStatementAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = CommonUtils.getLogicalPosition(caretModel);
        
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
    
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
    
        PsiIfStatement ifStatement = PsiTreeUtil.getParentOfType(pe, PsiIfStatement.class);
        ConsoleUtils.log("if",ifStatement);
        CopyPasteManager instance = CopyPasteManager.getInstance();
        StringSelection stringSelection = new StringSelection(ifStatement.getText());
        instance.setContents(stringSelection);
        caretModel.moveToLogicalPosition(logicalPosition);
    }
}
