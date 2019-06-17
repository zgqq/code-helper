package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ex.util.EditorUIUtil;
import com.intellij.util.DocumentUtil;
import com.intellij.util.text.CharArrayUtil;
import org.jetbrains.annotations.NotNull;


public class AutoIndentAction extends AnAction {
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
                () -> {
                    insertNewLineAtCaret(editor);});
    }
    

    public static void insertNewLineAtCaret(Editor editor) {
        EditorUIUtil.hideCursorInEditor(editor);
        Document document = editor.getDocument();
        if(!editor.isInsertMode()) {
            int caretLine = editor.getCaretModel().getLogicalPosition().line;
            int lineCount = document.getLineCount();
            if(caretLine < lineCount) {
                if (caretLine == lineCount - 1) {
                    document.insertString(document.getTextLength(), "\n");
                }
                LogicalPosition pos = new LogicalPosition(caretLine + 1, 0);
                editor.getCaretModel().moveToLogicalPosition(pos);
                editor.getSelectionModel().removeSelection();
                EditorModificationUtil.scrollToCaret(editor);
            }
            return;
        }
        EditorModificationUtil.deleteSelectedText(editor);
        // Smart indenting here:
        CharSequence text = document.getCharsSequence();

        final LogicalPosition logicalPosition = editor.getCaretModel().getLogicalPosition();
        int caretOffset = editor.getCaretModel().getOffset();

        editor.getCaretModel().moveToLogicalPosition(new LogicalPosition(logicalPosition.line-1, logicalPosition.column));
        int newCaretOffset = editor.getCaretModel().getOffset();
        editor.getCaretModel().moveToLogicalPosition(logicalPosition);

        int lineStartOffset = DocumentUtil.getLineStartOffset(newCaretOffset, document);
        int lineStartWsEndOffset = CharArrayUtil.shiftForward(text, lineStartOffset, " \t");

        String s = "" + text.subSequence(lineStartOffset, Math.min(caretOffset, lineStartWsEndOffset));
        document.insertString(caretOffset, s);
        editor.getCaretModel().moveToOffset(caretOffset + s.length());
        EditorModificationUtil.scrollToCaret(editor);
        editor.getSelectionModel().removeSelection();
    }


}
