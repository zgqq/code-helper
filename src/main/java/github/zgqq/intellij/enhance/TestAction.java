package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.openapi.util.ActionCallback;

import java.awt.event.InputEvent;

public class TestAction extends AnAction {
    public TestAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
//    Project project = event.getProject();
//      String[] emacs = ActionManager.getInstance().getActionIds("emacs");
//    Messages.showMessageDialog(project, String.join("_",emacs)+"hhah"
//            , "Greeting", Messages.getInformationIcon());

        String actionId = "emacsIDEAs.AceJump";
        AnAction action = ActionManager.getInstance().getAction(actionId);
        InputEvent inputEvent = ActionCommand.getInputEvent(actionId);
        ActionCallback actionCallback = ActionManager.getInstance().tryToExecute(action, inputEvent,
                null, ActionPlaces.UNKNOWN, true);

        actionCallback.doWhenDone(() -> {
            System.out.println("nice");
        });
    }
}
