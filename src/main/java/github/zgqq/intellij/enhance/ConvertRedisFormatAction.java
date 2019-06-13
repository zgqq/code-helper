package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class ConvertRedisFormatAction extends AnAction {
    
    private static final Logger logger = Logger.getInstance(ConvertRedisFormatAction.class);
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        CopyPasteManager instance = CopyPasteManager.getInstance();
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        
        Document document = editor.getDocument();
        
        String transferData;
        PsiElement pe = data.findElementAt(editor.getCaretModel().getOffset());
        
        if (pe instanceof PsiWhiteSpace) {
            pe = data.findElementAt(editor.getCaretModel().getOffset() - 1);
        }
        
        System.out.println(pe.getText());
        TextRange textRange = pe.getTextRange();
        System.out.println(textRange);
        System.out.println(pe.getClass().getName());
        if (pe instanceof PsiIdentifier) {
            PsiIdentifier psiIdentifier = (PsiIdentifier) pe;
            transferData = psiIdentifier.getText();
        } else {
            Transferable contents = instance.getContents();
            try {
                transferData = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception ex) {
                logger.warn("Unable to get transfer data", ex);
                return;
            }
        }
        
        String constant = transferData.toUpperCase();
        String redis = transferData.replace("_", ":")
                .toLowerCase();
        String express = "String " +constant + " = \"" + redis + "\";";
        System.out.println("Inserting " + express);
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
                () -> {
                    document.deleteString(textRange.getStartOffset(), textRange.getEndOffset());
                    document.insertString(textRange.getStartOffset(), express
                    );
                }
        );
    }
}
