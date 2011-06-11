/**
 * Author: Alex Vangelov (email@data.bg)
 * License: Respect the work of the creator and have fun! 2011 Alex Vangelov
 */
package jetpack;


import jetpack.preferences.PreferenceConstants;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.aptana.terminal.views.TerminalView;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "jetpack"; //$NON-NLS-1$
	// The shared instance
	private static Activator plugin;
	public static TerminalView jetpackTerminal = null;
	
	public static MessageConsoleStream out = null;
	public static void jLog(String s) {
		if (out == null) {
			MessageConsole myConsole = findConsole("JetPackConsole");
			out = myConsole.newMessageStream();
		}
		out.println(s);
	}
	private static MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }
	/**
	 * The constructor
	 */
	public Activator() {
		jLog("JetPack activated"); 
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	public static void TerminalExecute(IProject project,String cmd) {
		if (jetpackTerminal == null) {
			IPreferenceStore pref = Activator.getDefault().getPreferenceStore();
			IPath sdk_path = new Path(pref.getString(PreferenceConstants.P_PATH));
			jetpackTerminal = TerminalView.openView(project.getName(), project.getName(), sdk_path);
			jetpackTerminal.sendInput(String.format("cd %s && source bin/activate && cd \"%s\"\n", sdk_path, project.getLocation()));
		}
		jetpackTerminal.setFocus();
		jetpackTerminal.sendInput(cmd);
	}

}
