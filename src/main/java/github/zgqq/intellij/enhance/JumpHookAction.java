package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.openapi.util.ActionCallback;
import org.hunmr.acejump.AceJumpAction;
import org.hunmr.common.ChainActionEvent;

import java.awt.event.InputEvent;

public class JumpHookAction extends AnAction {
    
    @Override
    public void actionPerformed(AnActionEvent e) {
        AceJumpAction.getInstance().actionPerformed(createActionEvent(e));
    }
    
    private ChainActionEvent createActionEvent(AnActionEvent e) {
        
        Editor newEditor = getEditorFrom(e);
        Project eventProject = getEventProject(e);
        Runnable selectedHook = createRunnable(e);
        return new ChainActionEvent(e, selectedHook, newEditor, eventProject);
    }
    
    protected Runnable createRunnable(AnActionEvent event) {
        return () -> {
        
        };
    }
    
    protected ActionCallback executeCommand(String actionId) {
        AnAction action = ActionManager.getInstance().getAction(actionId);
        InputEvent inputEvent = ActionCommand.getInputEvent(actionId);
        return ActionManager.getInstance().tryToExecute(action, inputEvent,
                null, ActionPlaces.UNKNOWN, true);
    }
    
    
    protected Editor getEditorFrom(AnActionEvent e) {
        if (e instanceof ChainActionEvent) {
            ChainActionEvent chainActionEvent = (ChainActionEvent) e;
            Editor editor = chainActionEvent.getEditor();
            if (editor != null) {
                return editor;
            }
        }
        return e.getData(PlatformDataKeys.EDITOR);
    }
}
