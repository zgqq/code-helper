package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class SelectWholeMethodAction extends AnAction  {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        MethodUtils.selectCurrentMethod(e);
    }
}
