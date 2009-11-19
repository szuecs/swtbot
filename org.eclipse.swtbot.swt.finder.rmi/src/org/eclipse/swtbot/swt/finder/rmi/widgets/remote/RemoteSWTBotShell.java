package org.eclipse.swtbot.swt.finder.rmi.widgets.remote;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.rmi.utils.Traverse;
import org.eclipse.swtbot.swt.finder.rmi.widgets.AbstractSWTBot;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.rmi.widgets.TimeoutException;

public class RemoteSWTBotShell implements IRemoteSWTBotShell {
	/** delegation */
	private SWTBotShell shell;

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#activate()
	 */
	public SWTBotShell activate() throws TimeoutException {
		return shell.activate();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#backgroundColor()
	 */
	public Color backgroundColor() {
		return shell.backgroundColor();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#close()
	 */
	public void close() throws TimeoutException {
		shell.close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#contextMenu(java.lang.String)
	 */
	public SWTBotMenu contextMenu(String text) throws WidgetNotFoundException {
		return shell.contextMenu(text);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return shell.equals(obj);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#foregroundColor()
	 */
	public Color foregroundColor() {
		return shell.foregroundColor();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#getText()
	 */
	public String getText() {
		return shell.getText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#getToolTipText()
	 */
	public String getToolTipText() {
		return shell.getToolTipText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#hashCode()
	 */
	public int hashCode() {
		return shell.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#isActive()
	 */
	public boolean isActive() {
		return shell.isActive();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#isEnabled()
	 */
	public boolean isEnabled() {
		return shell.isEnabled();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#isOpen()
	 */
	public boolean isOpen() {
		return shell.isOpen();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#isVisible()
	 */
	public boolean isVisible() {
		return shell.isVisible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#pressShortcut(int, char)
	 */
	public AbstractSWTBot<Shell> pressShortcut(int modificationKeys, char c) {
		return shell.pressShortcut(modificationKeys, c);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#pressShortcut(int, int, char)
	 */
	public AbstractSWTBot<Shell> pressShortcut(int modificationKeys,
			int keyCode, char c) {
		return shell.pressShortcut(modificationKeys, keyCode, c);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#pressShortcut(org.eclipse.jface.bindings.keys.KeyStroke)
	 */
	public AbstractSWTBot<Shell> pressShortcut(KeyStroke... keys) {
		return shell.pressShortcut(keys);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#setFocus()
	 */
	public void setFocus() {
		shell.setFocus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#toString()
	 */
	public String toString() {
		return shell.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swtbot.swt.finder.rmi.widgets.remote.IRemoteSWTBotShell#traverse(org.eclipse.swtbot.swt.finder.rmi.utils.Traverse)
	 */
	public boolean traverse(Traverse traverse) {
		return shell.traverse(traverse);
	}
}
