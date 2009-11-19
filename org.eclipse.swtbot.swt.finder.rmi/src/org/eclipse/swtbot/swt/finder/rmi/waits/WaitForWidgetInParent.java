/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.rmi.waits;

import java.util.List;

import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @see Conditions
 * @version $Id$
 * @since 2.0
 */
class WaitForWidgetInParent<T extends Widget> extends WaitForObjectCondition<T> {

	private final Widget	parent;

	WaitForWidgetInParent(Matcher<T> matcher, Widget parent) {
		super(matcher);
		this.parent = parent;
	}

	public String getFailureMessage() {
		return "Could not find widget matching: " + matcher; //$NON-NLS-1$
	}

	protected List<T> findMatches() {
		return bot.getFinder().findControls(parent, matcher, true);
	}

}
