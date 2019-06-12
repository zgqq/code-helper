package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.ui.playback.commands.ActionCommand;

import java.awt.event.InputEvent;

public class ActionUtils {
    
    public static void execute(String actionId) {
        AnAction action = ActionManager.getInstance().getAction(actionId);
        InputEvent inputEvent = ActionCommand.getInputEvent(actionId);
        ActionManager.getInstance().tryToExecute(action, inputEvent,
                null, ActionPlaces.UNKNOWN, true);
    }
}
