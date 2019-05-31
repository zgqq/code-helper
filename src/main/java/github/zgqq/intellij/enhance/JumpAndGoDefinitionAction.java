package github.zgqq.intellij.enhance;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import org.hunmr.acejump.AceJumpAction;
import org.hunmr.common.ChainActionEvent;

import java.awt.event.InputEvent;

public class JumpAndGoDefinitionAction extends AnAction {
    
    public JumpAndGoDefinitionAction() {
        super("JumpAndGoDefinition");
    }
    
    @Override
    public void actionPerformed(AnActionEvent e) {
        AceJumpAction.getInstance().actionPerformed(createActionEvent(e));
    }
    
    private ChainActionEvent createActionEvent(AnActionEvent e) {
        Runnable selectedHook = () -> {
            System.out.println("selected");
            String actionId = "GotoDeclaration";
            AnAction action = ActionManager.getInstance().getAction(actionId);
            InputEvent inputEvent = ActionCommand.getInputEvent(actionId);
            ActionManager.getInstance().tryToExecute(action, inputEvent,
                    null, ActionPlaces.UNKNOWN, true);
            
        };
        
        Editor newEditor = getEditorFrom(e);
        Project eventProject = getEventProject(e);
        return new ChainActionEvent(e, selectedHook, newEditor, eventProject);
    }
    
    
    private Editor getEditorFrom(AnActionEvent e) {
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
