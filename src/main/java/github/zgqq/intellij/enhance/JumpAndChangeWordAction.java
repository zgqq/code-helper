package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.KeyHandler;
import com.maddyhome.idea.vim.command.CommandState;
import com.maddyhome.idea.vim.helper.EditorDataContext;

import javax.swing.*;

public class JumpAndChangeWordAction extends JumpHookAction {
    
    private static final Logger logger = Logger.getInstance(JumpAndChangeWordAction.class);
    
    protected Runnable createRunnable(AnActionEvent anActionEvent) {
        Editor editor = getEditorFrom(anActionEvent);
        return () -> {
            try {
                if (CommandState.getInstance(editor).getMode() == CommandState.Mode.COMMAND) {
                    pressVimKeys(editor, 'c', 'w');
                } else {
                    executeCommand("EditorDeleteToWordEnd");
                }
            } catch (Throwable throwable) {
                logger.error(throwable);
            }
        };
    }
    
    private void pressVimKeys(Editor editor, char... keys) {
        if (keys != null) {
            for (char key : keys) {
                KeyStroke c = KeyStroke.getKeyStroke(key);
                KeyHandler.getInstance().handleKey(editor, c, new EditorDataContext(editor));
            }
        }
    }
}
