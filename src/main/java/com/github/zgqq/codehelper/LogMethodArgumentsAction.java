package com.github.zgqq.codehelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LogMethodArgumentsAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = CommonUtils.getEditorFrom(e);
        PsiFile data = (PsiJavaFile) e.getData(LangDataKeys.PSI_FILE);

        PsiElement element = data.findElementAt(editor.getCaretModel().getOffset());
        PsiElement parentOfType = element;
        PsiElement mostOuterMethod;
        do {
            mostOuterMethod = parentOfType;
            parentOfType = PsiTreeUtil.getParentOfType(parentOfType, PsiMethod.class);
        } while (parentOfType != null);

        if (mostOuterMethod != parentOfType) {
            final PsiMethod mostOuterMethod1 = (PsiMethod) mostOuterMethod;
            final PsiParameterList parameterList = mostOuterMethod1.getParameterList();
            final PsiParameter[] parameters1 = parameterList.getParameters();

            StringBuilder log = new StringBuilder();
            log.append("LOGGER.info(\"");
            final String[] strings = StringUtils.splitByCharacterTypeCamelCase(mostOuterMethod1.getName());

            final ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < strings.length; i++) {
                final String string = strings[i];
                if (i == 0) {
                    final String capitalize = StringUtils.capitalize(string);
                    if (capitalize.endsWith("e")) {
                        list.add(capitalize.substring(0, capitalize.length() - 1) + "ing");
                    } else if (capitalize.endsWith("t")) {
                        list.add(capitalize + "ting");
                    } else {
                        list.add(capitalize + "ing");
                    }
                } else {
                    list.add(StringUtils.uncapitalize(string));
                }
            }

            final String join = StringUtils.join(
                    list,
                    ' '
            );

            log.append(join);
            StringBuilder paramLog = new StringBuilder();
            StringBuilder variableLog = new StringBuilder();
            for (int i = 0; i < parameters1.length; i++) {
                final PsiParameter psiParameter = parameters1[i];
                final String name = psiParameter.getName();
                if (variableLog.length() > 0) {
                    variableLog.append(", " + name);
                } else {
                    variableLog.append(name);
                }
                paramLog.append(", " + name + ":{}");
            }
            log.append(paramLog);
            if (variableLog.length() > 0) {
                log.append("\", ");
                log.append(variableLog);
            }
            log.append(");");

            final int endOffset = mostOuterMethod1.getBody().getLBrace().getTextRange().getEndOffset();
            EditorUtils.writeIndentText(e, log.toString(), endOffset);
        }
    }
}
