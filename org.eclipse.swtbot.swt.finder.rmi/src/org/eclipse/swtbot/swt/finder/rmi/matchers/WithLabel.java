/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.rmi.matchers;

import static org.hamcrest.Matchers.anything;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.rmi.SWTBot;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Tells if a particular widget has a label with the specified text.
 * <p>
 * <b>NOTE:</b> This will <em>drill upwards</em> in the widget hierarchy in an attempt to find the label for a widget.
 * </p>
 * 
 * @see WithMnemonic
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 * @since 2.0
 */
public class WithLabel<T extends Widget> extends AbstractMatcher<T> {

	/**
	 * The mnemonic text matcher instance to use.
	 */
	private final WithMnemonic<Widget>	mnemonicTextMatcher;
	private final Composite				parent;

	/**
	 * Matches a widget that has the specified Label.
	 * 
	 * @param labelText the label.
	 * @param parent
	 */
	WithLabel(String labelText, Composite parent) {
		mnemonicTextMatcher = new WithMnemonic<Widget>(labelText);
		this.parent = parent;
	}

	protected boolean doMatch(Object obj) {
		List<? extends Widget> allWidgets = new SWTBot().widgets(Matchers.<Widget>anything(), parent(obj));

		int widgetIndex = allWidgets.indexOf(obj);

		ListIterator<? extends Widget> listIterator = allWidgets.listIterator(widgetIndex);

		while (listIterator.hasPrevious()) {
			Widget previousWidget = listIterator.previous();
			if ((isLabel(previousWidget)) && mnemonicTextMatcher.matches(previousWidget))
				return true;
		}

		return false;
	}

	private Composite parent(Object obj) {
		return parent != null ? parent : ((Control) obj).getShell();
	}

	private boolean isLabel(Widget widget) {
		return widget instanceof Label || widget instanceof CLabel;
	}

	public void describeTo(Description description) {
		description.appendText("with label (").appendDescriptionOf(mnemonicTextMatcher).appendText(")"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Matches a widget that has the specified labelText.
	 * 
	 * @param labelText the label.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends Widget> Matcher<T> withLabel(String labelText) {
		return new WithLabel<T>(labelText, null);
	}

	/**
	 * Matches a widget that has the specified labelText within the given parent.
	 * 
	 * @param labelText the label.
	 * @param parent the parent widget to which the matcher is scoped.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static <T extends Widget> Matcher<T> withLabel(String labelText, Composite parent) {
		return new WithLabel<T>(labelText, parent);
	}

}