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

public interface IRemoteSWTBotShell {

	public SWTBotShell activate() throws TimeoutException;

	public Color backgroundColor();

	public void close() throws TimeoutException;

	public SWTBotMenu contextMenu(String text) throws WidgetNotFoundException;

	public boolean equals(Object obj);

	public Color foregroundColor();

	public String getText();

	public String getToolTipText();

	public int hashCode();

	public boolean isActive();

	public boolean isEnabled();

	public boolean isOpen();

	public boolean isVisible();

	public AbstractSWTBot<Shell> pressShortcut(int modificationKeys, char c);

	public AbstractSWTBot<Shell> pressShortcut(int modificationKeys,
			int keyCode, char c);

	public AbstractSWTBot<Shell> pressShortcut(KeyStroke... keys);

	public void setFocus();

	public String toString();

	public boolean traverse(Traverse traverse);

}