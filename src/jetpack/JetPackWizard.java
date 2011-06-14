/**
 * Author: Alex Vangelov (email@data.bg)
 * License: Respect the work of the creator and have fun! 2011 Alex Vangelov
 */
package jetpack;

import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

public class JetPackWizard extends Wizard implements INewWizard,
IExecutableExtension {
	private IConfigurationElement _configurationElement;
	private static final String WIZ_NAME = "JetPack Project Wizard";
	private static final String WIZ_TITLE = "Mozilla JetPack Project";
	private static final String WIZ_DESCR = "Create Firefox 4 Add-on.";
	private WizardNewProjectCreationPage _pageOne;
	
	
	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_configurationElement = config;
	}
	
	public JetPackWizard() {
		setWindowTitle("JetPack Wizard");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performFinish() {
		String name = _pageOne.getProjectName();
		URI location = null;
		if (!_pageOne.useDefaults()) {
			location = _pageOne.getLocationURI();
		} 
		JetPackRoutines.createProject(name, location,this.getShell());
		BasicNewProjectResourceWizard.updatePerspective(_configurationElement);
		return true;
	}
	
	@Override
	public void addPages() {
		 super.addPages();
	    _pageOne = new WizardNewProjectCreationPage(WIZ_NAME);
	    _pageOne.setTitle(WIZ_TITLE);
	    _pageOne.setDescription(WIZ_DESCR);
	    addPage(_pageOne);
	}
}
