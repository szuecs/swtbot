/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Toby Weston - initial API and implementation (Bug 259860)
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.rmi.widgets;

import static org.eclipse.swtbot.swt.finder.rmi.matchers.WidgetMatcherFactory.withMnemonic;

import java.io.Serializable;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.rmi.finders.EventContextMenuFinder;
import org.hamcrest.Matcher;

/**
 * Represents a tray item.
 * 
 * @author Toby Weston (Bug 259860)
 * @version $Id$
 */
public class SWTBotTrayItem extends AbstractSWTBot<TrayItem> implements
		Serializable {

	private static final long serialVersionUID = -6658001205322374160L;

	/**
	 * Constructs a new instance with the given widget.
	 * 
	 * @param widget
	 *            the tray item.
	 * @throws WidgetNotFoundException
	 *             if the widget is <code>null</code> or widget has been
	 *             disposed.
	 */
	public SWTBotTrayItem(TrayItem widget) throws WidgetNotFoundException {
		super(widget);
	}

	public SWTBotMenu contextMenu(String label) throws WidgetNotFoundException {
		EventContextMenuFinder finder = new EventContextMenuFinder();
		try {
			finder.register();
			notify(SWT.MenuDetect);
			Matcher<MenuItem> withMnemonic = withMnemonic(label);
			List<MenuItem> menus = finder.findMenus(withMnemonic);
			if (menus.isEmpty())
				throw new WidgetNotFoundException("Could not find a menu item");
			return new SWTBotMenu(menus.get(0));
		} finally {
			finder.unregister();
		}
	}

	/**
	 * Convenience API for {@link #contextMenu(String)}
	 */
	public SWTBotMenu menu(String label) {
		return contextMenu(label);
	}
}
