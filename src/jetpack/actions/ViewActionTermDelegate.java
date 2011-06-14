package jetpack.actions;

import jetpack.Activator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ViewActionTermDelegate implements IViewActionDelegate {

	private ISelection selection;
	
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
	public void init(IViewPart view) {
		// TODO Auto-generated method stub

	}

}
