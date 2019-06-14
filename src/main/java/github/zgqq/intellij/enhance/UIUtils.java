package github.zgqq.intellij.enhance;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;


public class UIUtils {
    
    
    public static void replaceString(
            Document document,
            TextRange textRange,
            String str
    ) {
        document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
        document.insertString(textRange.getStartOffset(), str);
    }
}
