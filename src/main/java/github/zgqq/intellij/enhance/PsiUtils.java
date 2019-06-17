package github.zgqq.intellij.enhance;

import com.intellij.openapi.project.Project;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

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
}
