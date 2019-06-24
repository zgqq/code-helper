package github.zgqq.intellij.enhance;

import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PsiUtils {

    public static PsiClass searchPsiClass(PsiJavaFile data, Project project, PsiClass targetClass, PsiElement typePsi) {
        final PsiClass[] classesByName = PsiShortNamesCache.getInstance(project).getClassesByName(typePsi.getText(),
                GlobalSearchScope.projectScope(project));
        String fullname = data.getPackageName() + "." + typePsi.getText();
        ConsoleUtils.log("Searching fullname " + fullname);
        if (classesByName != null && classesByName.length > 0) {
            for (PsiClass psiClass : classesByName) {
                ConsoleUtils.log("Matching class " + psiClass.getQualifiedName());
                if (psiClass.getQualifiedName().equals(fullname)) {
                    targetClass = psiClass;
                }
            }
        }
        return targetClass;
    }

    public static void navigate(PsiElement psiElement) {
        if (psiElement instanceof Navigatable) {
            Navigatable element = (Navigatable) psiElement;
            if (element.canNavigate()) {
                element.navigate(true);
            }
        }
    }

    public static PsiElement findParentElement(PsiElement[] children, PsiElement target) {
        if (children == null || children.length == 0) {
            return null;
        }
        for (int i = 0; i < children.length; i++) {
            final PsiElement child = children[i];
            if (child.isEquivalentTo(target)) {
                return child;
            }
            final PsiElement parentElement = findParentElement(child.getChildren(), target);
            if (parentElement != null) {
                return child;
            }
        }
        return null;
    }

    public static <T extends PsiElement> T findChildAfterOffset(T[] childrenOfType, int offset) {
        return findChildAfterOffset(Arrays.asList(childrenOfType), offset);
    }

    public static <T extends PsiElement> T findChildAfterOffset(Collection<T> childrenOfType, int offset) {
        int minDistance = Integer.MAX_VALUE;
        T mostNearExpression = null;
        for (T expressionList : childrenOfType) {
            if (expressionList instanceof PsiExpressionList) {
                PsiExpressionList list = (PsiExpressionList) expressionList;
                if (list.isEmpty()) {
                    continue;
                }
            }
            ConsoleUtils.log("child", expressionList);
            final int textOffset = expressionList.getTextOffset();
            if (offset < textOffset) {
                int distance = textOffset - offset;
                if (distance < minDistance) {
                    minDistance = distance;
                    mostNearExpression = expressionList;
                }
            }
        }
        return mostNearExpression;
    }

    public static PsiElement findNextArgument(PsiElement parentOfType, int offset) {
        PsiElement currentArg = null;
        PsiExpressionList psiExpressionList;
        final Collection<PsiExpressionList> childrenOfType = PsiTreeUtil.findChildrenOfType(parentOfType, PsiExpressionList.class);
        psiExpressionList = PsiUtils.findChildAfterOffset(childrenOfType, offset);

        if (psiExpressionList != null && psiExpressionList.getExpressionCount() > 0) {
            currentArg = psiExpressionList.getExpressions()[0];
            ConsoleUtils.log("argEle", currentArg);
        }
        return currentArg;
    }

    public static <T extends PsiNameIdentifierOwner> void navigateToNamedElement(PsiElement pe, Class<T> clazz) {
        final T parentOfType = PsiTreeUtil.getParentOfType(pe, clazz);
        if (parentOfType != null) {
            PsiUtils.navigate(parentOfType.getNameIdentifier().getNavigationElement());
        }
    }

    public static <T extends PsiElement> T getMostNearBeforeElement(Collection<T> childrenOfType, int offset) {
        int minDistance = Integer.MAX_VALUE;
        T mostNearExpression = null;
        for (T expressionList : childrenOfType) {
            final int textOffset = expressionList.getTextOffset();
            if (offset > textOffset) {
                int distance =  offset - textOffset;
                if (distance < minDistance) {
                    minDistance = distance;
                    mostNearExpression = expressionList;
                }
            }
        }
        return mostNearExpression;
    }
}
