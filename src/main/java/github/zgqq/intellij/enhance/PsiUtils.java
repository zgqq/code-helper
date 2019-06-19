package github.zgqq.intellij.enhance;

import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

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

    public static void navigate(PsiElement variableClass) {
        if (variableClass instanceof Navigatable) {
            Navigatable element = (Navigatable) variableClass;
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
}
