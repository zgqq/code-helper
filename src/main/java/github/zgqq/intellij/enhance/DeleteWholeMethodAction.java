package github.zgqq.intellij.enhance;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import org.jetbrains.annotations.NotNull;

public class DeleteWholeMethodAction extends AnAction {
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = CommonUtils.getLogicalPosition(caretModel);
        
        MethodUtils.selectCurrentMethod(e);
        IdeaVIMUtils.pressVimKeys(editor, 'd');
        
        caretModel.moveToLogicalPosition(logicalPosition);
        
    }
}
