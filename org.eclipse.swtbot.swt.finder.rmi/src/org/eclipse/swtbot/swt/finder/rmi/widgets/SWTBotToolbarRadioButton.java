/*******************************************************************************
 * Copyright (c) 2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.rmi.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.swt.finder.rmi.ReferenceBy;
import org.eclipse.swtbot.swt.finder.rmi.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.rmi.Style;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.rmi.results.BoolResult;
import org.eclipse.swtbot.swt.finder.rmi.results.VoidResult;
import org.eclipse.swtbot.swt.finder.rmi.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.rmi.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.rmi.utils.internal.Assert;
import org.hamcrest.SelfDescribing;

/**
 * Represents a tool item of type checkbox
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
@SWTBotWidget(clasz = ToolItem.class, preferredName = "toolbarRadioButton", style = @Style(name = "SWT.RADIO", value = SWT.RADIO), referenceBy = {
		ReferenceBy.MNEMONIC, ReferenceBy.TOOLTIP })
public class SWTBotToolbarRadioButton extends SWTBotToolbarButton {

	private static final long serialVersionUID = -5759309797272025239L;

	/**
	 * Constructs an new instance of this item.
	 * 
	 * @param w
	 *            the tool item.
	 * @throws WidgetNotFoundException
	 *             if the widget is <code>null</code> or widget has been
	 *             disposed.
	 */
	public SWTBotToolbarRadioButton(ToolItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	/**
	 * Constructs an new instance of this item.
	 * 
	 * @param w
	 *            the tool item.
	 * @param description
	 *            the description of the widget, this will be reported by
	 *            {@link #toString()}
	 * @throws WidgetNotFoundException
	 *             if the widget is <code>null</code> or widget has been
	 *             disposed.
	 */
	public SWTBotToolbarRadioButton(ToolItem w, SelfDescribing description)
			throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, SWT.RADIO),
				"Expecting a radio button."); //$NON-NLS-1$
	}

	/**
	 * Click on the tool item. This will toggle the tool item.
	 * 
	 * @return itself
	 */
	public SWTBotToolbarRadioButton toggle() {
		log.debug(MessageFormat.format("Clicking on {0}", this)); //$NON-NLS-1$
		assertEnabled();
		internalToggle();
		sendNotifications();
		log.debug(MessageFormat.format("Clicked on {0}", this)); //$NON-NLS-1$
		return this;
	}

	public SWTBotToolbarRadioButton click() {
		return toggle();
	}

	private void internalToggle() {
		syncExec(new VoidResult() {
			public void run() {
				widget.setSelection(!widget.getSelection());
			}
		});
	}

	/**
	 * Selects the checkbox button.
	 */
	public void select() {
		if (!isChecked())
			toggle();
	}

	/**
	 * Deselects the checkbox button.
	 */
	public void deselect() {
		if (isChecked())
			toggle();
	}

	/**
	 * @return <code>true</code> if the button is checked, <code>false</code>
	 *         otherwise.
	 */
	public boolean isChecked() {
		return syncExec(new BoolResult() {
			public Boolean run() {
				return widget.getSelection();
			}
		});
	}
}
