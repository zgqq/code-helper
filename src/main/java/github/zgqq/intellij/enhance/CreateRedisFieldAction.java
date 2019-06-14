package github.zgqq.intellij.enhance;

import com.intellij.codeInsight.daemon.impl.quickfix.JavaCreateFieldFromUsageHelper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;


public class CreateRedisFieldAction extends AnAction {
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        Project project = editor.getProject();
        Document document = editor.getDocument();
        PsiJavaFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);
        
        PsiElement dotOrField = data.findElementAt(editor.getCaretModel().getOffset() - 1);
        PsiElement fieldElement = data.findElementAt(editor.getCaretModel().getOffset());
        
        PsiClass targetClass;
        if (".".equals(dotOrField.getText())) {
            targetClass = getPsiClass(data, dotOrField);
        } else {
            fieldElement = dotOrField;
            PsiElement nextDotOrField = data.findElementAt(fieldElement
                    .getTextRange().getStartOffset() - 1);
            if (".".equals(nextDotOrField.getText())) {
                targetClass = getPsiClass(data, nextDotOrField);
            } else {
                targetClass = PsiTreeUtil.getParentOfType(dotOrField, PsiClass.class);
            }
        }
        
        final String intentField = fieldElement.getText();
        String fieldName = intentField.toUpperCase();
        String redisField = fieldName.replace("_", ":")
                .toLowerCase();
        
        
        PsiField field =
                JavaPsiFacade.getElementFactory(project).createField(fieldName,
                        PsiType.getJavaLangString(PsiManagerImpl.getInstance(project),
                                GlobalSearchScope.allScope(project)));
        
        final PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
        final PsiExpression initializer = factory.createExpressionFromText("\"" + redisField + "\"", field);
        field.setInitializer(initializer);
        
        if (!targetClass.isInterface()) {
            PsiUtil.setModifierProperty(field, PsiModifier.PUBLIC, true);
            PsiUtil.setModifierProperty(field, PsiModifier.STATIC, true);
            PsiUtil.setModifierProperty(field, PsiModifier.FINAL, true);
        }
        
        
        JavaCreateFieldFromUsageHelper javaCreateFieldFromUsageHelper = new JavaCreateFieldFromUsageHelper();
        
        PsiElement finalElementAtCaret = fieldElement;
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> {
            UIUtils.replaceString(document,
                    finalElementAtCaret.getTextRange(),
                    fieldName
            );
            javaCreateFieldFromUsageHelper.insertFieldImpl(targetClass, field, null);
        });
    }
    
    private PsiClass getPsiClass(PsiJavaFile data, PsiElement dotField) {
        PsiClass targetClass;
        PsiElement typePsi = data.findElementAt(dotField.getTextRange().getStartOffset() - 1);
        ConsoleUtils.log("typeClass", typePsi);
        PsiImportStatementBase singleImportStatement = data.
                getImportList().findSingleImportStatement(typePsi.getText());
        targetClass = (PsiClass) singleImportStatement.resolve();
        return targetClass;
    }
}
