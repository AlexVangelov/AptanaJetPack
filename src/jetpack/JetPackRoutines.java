/**
 * Author: Alex Vangelov (email@data.bg)
 * License: Respect the work of the creator and have fun! 2011 Alex Vangelov
 */
package jetpack;

import java.io.File;
import java.net.URI;

import jetpack.builder.ToggleNatureAction;
import jetpack.preferences.PreferenceConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


import com.aptana.terminal.views.TerminalView;


public class JetPackRoutines {
	public static TerminalView jetpackTerminal;
	
	public static IProject createProject(String projectName, URI location, Shell shell) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = createEmptyProject(projectName,location);
		ToggleNatureAction.toggleNature(project);
		
		IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
		IPath sdk_path = new Path(pref.getString(PreferenceConstants.P_PATH));
		
		File cfx = new File(String.format("%s/bin/cfx", sdk_path));
		if (!cfx.exists()) {
			MessageBox box = new MessageBox(shell,SWT.ICON_ERROR);
			box.setMessage("Mozilla JetPack is not configured!\nGo to Window>Preferences>JetPack Config\nand set Addon SDK location.\n(http://jetpack.mozillalabs.com)");
			box.open();
		} else {
			Activator.TerminalExecute(project, "cfx init\r");
		}
		return project;
	}
	
	public static IProject createEmptyProject(String projectName, URI location) {
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		
		if (!newProject.exists()) {
			URI projectLocation = location;
			IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
			if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
				projectLocation = null;
			}
			desc.setLocationURI(projectLocation);
			try {
				newProject.create(desc, null);
				if (!newProject.isOpen()) {
					newProject.open(null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		return newProject;
	}
	
}

