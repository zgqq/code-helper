package github.zgqq.intellij.enhance;

import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.KeyHandler;
import com.maddyhome.idea.vim.helper.EditorDataContext;

import javax.swing.*;

public class IdeaVIMUtils {
    
    public static void pressVimKeys(Editor editor, char... keys) {
        if (keys != null) {
            for (char key : keys) {
                KeyStroke c = KeyStroke.getKeyStroke(key);
                KeyHandler.getInstance().handleKey(editor, c, new EditorDataContext(editor));
            }
        }
    }
}
