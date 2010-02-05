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
package org.eclipse.swtbot.eclipse.spy;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swtbot.swt.finder.resolvers.IChildrenResolver;
import org.eclipse.swtbot.swt.finder.resolvers.IParentResolver;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class EclipseSpy {

	Action					actionMonitor;

	Object					lastWidget;

	StyledText				output;

	Runnable				trackWidgets;

	private final Composite	parent;

	/**
	 * @param parent
	 * @param childrenResolver
	 * @param parentResolver
	 */
	public EclipseSpy(Composite parent, IChildrenResolver childrenResolver, IParentResolver parentResolver) {
		this.parent = parent;
		initialize(parent.getDisplay());
		trackWidgets = new EclipseWidgetTracker(this, childrenResolver, parentResolver);
	}

	private void createActionMonitor() {
		actionMonitor = new Action("Monitor", IAction.AS_CHECK_BOX) { //$NON-NLS-1$
			public void run() {
				if (actionMonitor.isChecked() && !output.isDisposed()) {
					Display display = output.getDisplay();
					display.timerExec(100, trackWidgets);
				}
			}
		};

		actionMonitor.setToolTipText("Enable/Disable monitoring of widgets"); //$NON-NLS-1$
		actionMonitor.setChecked(false);
	}

	private void createOutputText() {
		output = new StyledText(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
		output.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, true));
		output.setText("To toggle, or freeze info on a particular control, press CTRL+SHIFT: \n");
		if (isMac()){
			output.setFont(new Font(Display.getCurrent(), "Monaco", 11, SWT.NONE)); //$NON-NLS-1$
		}else
			output.setFont(new Font(Display.getCurrent(), "Courier New", 10, SWT.NONE)); //$NON-NLS-1$
	}

	private void hookAccelerator() {
		parent.getDisplay().addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if ((e.stateMask == SWT.CTRL) && (e.keyCode == SWT.SHIFT))
					if (actionMonitor.isChecked())
						actionMonitor.setChecked(false);
					else {
						actionMonitor.setChecked(true);
						actionMonitor.run();
					}
			}
		});

		// parent.getDisplay().addFilter(SWT.KeyDown, new SWTBotExecutionListener());
	}

	private static boolean isMac() {
		String swtPlatform = SWT.getPlatform();
		return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
	}

	private void initialize(Display display) {
		createOutputText();
		createActionMonitor();
		hookAccelerator();
	}
}
