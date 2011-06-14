package jetpack;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class JetTerminal implements IObjectActionDelegate {
	
	private ISelection selection;

	public JetTerminal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		IResource r = Activator.extractSelection(selection);
		if (r != null) Activator.OpenTerminal(r.getProject());
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;	
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

}
