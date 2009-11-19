/*******************************************************************************
 * Copyright (c) 2008-2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Patel - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259837
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *     Ralf Ebert - (bug 294452) - RmiSWTBotEditor does not pass an IProgressMonitor when saving an editor
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.rmi.widgets;

import org.eclipse.swtbot.eclipse.finder.rmi.RmiSWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.rmi.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.rmi.results.Result;
import org.eclipse.swtbot.swt.finder.rmi.results.VoidResult;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.hamcrest.SelfDescribing;

/**
 * This represents an Eclipse workbench editor part.
 * 
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @author Ralf Ebert www.ralfebert.de (bug 271630)
 * @version $Id$
 */
public class RmiSWTBotEditor extends SWTBotEditor {

	/**
	 * Constructs an instance for the given editorReference.
	 * 
	 * @param editorReference
	 *            the editor reference.
	 * @param bot
	 *            the instance of {@link RmiSWTWorkbenchBot} which will be used to
	 *            drive operations on behalf of this object.
	 * @throws WidgetNotFoundException
	 *             if the widget is <code>null</code> or widget has been
	 *             disposed.
	 * @since 2.0
	 */
	public RmiSWTBotEditor(IEditorReference editorReference, RmiSWTWorkbenchBot bot)
			throws WidgetNotFoundException {
		super(editorReference, bot);
	}

	/**
	 * Constructs an instance for the given editorReference.
	 * 
	 * @param editorReference
	 *            the part reference.
	 * @param bot
	 *            the helper bot.
	 * @param description
	 *            the description of the editor part.
	 */
	public RmiSWTBotEditor(IEditorReference editorReference,
			RmiSWTWorkbenchBot bot, SelfDescribing description) {
		super(editorReference, bot, description);
	}

	public boolean isActive() {
		return bot.activeEditor().partReference == partReference;
	}

	public void setFocus() {
	}

	/**
	 * Save the editor and close it.
	 */
	public void saveAndClose() {
		save();
		close();
	}

	public void close() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				partReference.getPage().closeEditor(
						partReference.getEditor(false), false);
			}
		});
	}

	/**
	 * Save the editor.
	 */
	public void save() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				IEditorPart editor = partReference.getEditor(false);
				partReference.getPage().saveEditor(editor, false);
			}
		});
	}

	/**
	 * Shows the editor if it is visible.
	 */
	public void show() {
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				IEditorPart editor = partReference.getEditor(true);
				partReference.getPage().activate(editor);
			}
		});
	}

	/**
	 * Returns true if the editor is dirty.
	 * 
	 * @return dirty state of editor
	 */
	public boolean isDirty() {
		return UIThreadRunnable.syncExec(new Result<Boolean>() {
			public Boolean run() {
				return partReference.isDirty();
			}
		});
	}

	/**
	 * @return an extended version of the editor bot which provides methods for
	 *         text editors.
	 * @throws WidgetNotFoundException
	 *             if this is not a text editor
	 */
	public RmiSWTBotEclipseEditor toTextEditor() {
		return new RmiSWTBotEclipseEditor(this.partReference, this.bot);
	}

}
