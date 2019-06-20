package github.zgqq.intellij.enhance;

import com.intellij.openapi.editor.Editor;

public interface WriteAction {
    void run(Editor editor);
}
