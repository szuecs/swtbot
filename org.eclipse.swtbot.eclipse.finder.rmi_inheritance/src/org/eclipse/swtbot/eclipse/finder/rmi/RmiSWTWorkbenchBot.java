/*******************************************************************************
 * Copyright (c) 2009 SWTBot Committers and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ralf Ebert www.ralfebert.de - (bug 271630) SWTBot Improved RCP / Workbench support
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.rmi;

import static org.eclipse.swtbot.eclipse.finder.rmi.matchers.WidgetMatcherFactory.withPartId;
import static org.eclipse.swtbot.eclipse.finder.rmi.matchers.WidgetMatcherFactory.withPartName;
import static org.eclipse.swtbot.eclipse.finder.rmi.matchers.WidgetMatcherFactory.withPerspectiveId;
import static org.eclipse.swtbot.eclipse.finder.rmi.matchers.WidgetMatcherFactory.withPerspectiveLabel;
import static org.eclipse.swtbot.eclipse.finder.rmi.waits.Conditions.waitForEditor;
import static org.eclipse.swtbot.eclipse.finder.rmi.waits.Conditions.waitForView;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.rmi.finders.WorkbenchContentsFinder;
import org.eclipse.swtbot.eclipse.finder.rmi.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.rmi.waits.WaitForEditor;
import org.eclipse.swtbot.eclipse.finder.rmi.waits.RmiWaitForView;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.RmiSWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.RmiSWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.RmiSWTBotView;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * RmiSWTWorkbenchBot is a {@link RmiSWTWorkbenchBot} with capabilities for
 * remoting Eclipse workbench items like views, editors and perspectives.
 */
public class RmiSWTWorkbenchBot extends
		org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot {

	private final WorkbenchContentsFinder workbenchContentsFinder;

	/**
	 * Constructs a workbench bot
	 */
	public RmiSWTWorkbenchBot() {
		workbenchContentsFinder = new WorkbenchContentsFinder();
	}

	/**
	 * Returns the perspective matching the given matcher
	 * 
	 * @param matcher
	 *            the matcher used to find the perspective
	 * @return a perspective matching the matcher
	 * @throws WidgetNotFoundException
	 *             if the perspective is not found
	 */
	public RmiSWTBotPerspective perspective(Matcher<?> matcher) {
		List<IPerspectiveDescriptor> perspectives = workbenchContentsFinder
				.findPerspectives(matcher);
		return new RmiSWTBotPerspective(perspectives.get(0), this);
	}

	/**
	 * Shortcut for perspective(withPerspectiveLabel(label))
	 * 
	 * @param label
	 *            the "human readable" label for the perspective
	 * @return a perspective with the specified <code>label</code>
	 * @see #perspective(Matcher)
	 * @see WidgetMatcherFactory#withPerspectiveLabel(Matcher)
	 */
	public RmiSWTBotPerspective perspectiveByLabel(String label) {
		return perspective(withPerspectiveLabel(label));
	}

	/**
	 * Shortcut for perspective(perspectiveById(label))
	 * 
	 * @param id
	 *            the perspective id
	 * @return a perspective with the specified <code>label</code>
	 * @see #perspective(Matcher)
	 * @see WidgetMatcherFactory#withPerspectiveId(Matcher)
	 */
	public RmiSWTBotPerspective perspectiveById(String id) {
		return perspective(withPerspectiveId(id));
	}

	/**
	 * @param matcher
	 *            Matcher for IPerspectiveDescriptor
	 * @return all available matching perspectives
	 */
	public List<RmiSWTBotPerspective> perspectives(Matcher<?> matcher) {
		List<IPerspectiveDescriptor> perspectives = workbenchContentsFinder
				.findPerspectives(matcher);

		List<RmiSWTBotPerspective> perspectiveBots = new ArrayList<RmiSWTBotPerspective>();
		for (IPerspectiveDescriptor perspectiveDescriptor : perspectives)
			perspectiveBots.add(new RmiSWTBotPerspective(perspectiveDescriptor,
					this));

		return perspectiveBots;
	}

	/**
	 * @return all available perspectives
	 */
	public List<RmiSWTBotPerspective> perspectives() {
		return perspectives(Matchers.anything());
	}

	/**
	 * Waits for a view matching the given matcher to appear in the active
	 * workbench page and returns it
	 * 
	 * @param matcher
	 *            the matcher used to match views
	 * @return views that match the matcher
	 * @throws WidgetNotFoundException
	 *             if the view is not found
	 */
	public RmiSWTBotView view(Matcher<IViewReference> matcher) {
		RmiWaitForView waitForView = waitForView(matcher);
		waitUntilWidgetAppears(waitForView);
		return new RmiSWTBotView(waitForView.get(0), this);
	}

	/**
	 * Shortcut for view(withPartName(title))
	 * 
	 * @param title
	 *            the "human readable" title
	 * @return the view with the specified title
	 * @see WidgetMatcherFactory#withPartName(Matcher)
	 */
	public RmiSWTBotView viewByTitle(String title) {
		Matcher<IViewReference> withPartName = withPartName(title);
		return view(withPartName);
	}

	/**
	 * Shortcut for view(withPartId(id))
	 * 
	 * @param id
	 *            the view id
	 * @return the view with the specified id
	 * @see WidgetMatcherFactory#withPartId(String)
	 */
	public RmiSWTBotView viewById(String id) {
		Matcher<IViewReference> withPartId = withPartId(id);
		return view(withPartId);
	}

	/**
	 * Returns all views which are opened currently (no waiting!) which match
	 * the given matcher
	 * 
	 * @param matcher
	 *            the matcher used to find views
	 * @return the list of all matching views
	 */
	public List<RmiSWTBotView> views(Matcher<?> matcher) {
		List<IViewReference> views = workbenchContentsFinder.findViews(matcher);

		List<RmiSWTBotView> viewBots = new ArrayList<RmiSWTBotView>();
		for (IViewReference viewReference : views)
			viewBots.add(new RmiSWTBotView(viewReference, this));
		return viewBots;
	}

	/**
	 * @return all views which are opened currently
	 */
	public List<RmiSWTBotView> views() {
		return views(Matchers.anything());
	}

	/**
	 * Returns the active workbench view part
	 * 
	 * @return the active view, if any
	 * @throws WidgetNotFoundException
	 *             if there is no active view
	 */
	public RmiSWTBotView activeView() {
		IViewReference view = workbenchContentsFinder.findActiveView();
		if (view == null)
			throw new WidgetNotFoundException("There is no active view"); //$NON-NLS-1$
		return new RmiSWTBotView(view, this);
	}

	/**
	 * Waits for a editor matching the given matcher to appear in the active
	 * workbench page and returns it
	 * 
	 * @param matcher
	 *            the matcher used to find the editor
	 * @return an editor that matches the matcher
	 * @throws WidgetNotFoundException
	 *             if the editor is not found
	 */
	public RmiSWTBotEditor editor(Matcher<IEditorReference> matcher) {
		WaitForEditor waitForEditor = waitForEditor(matcher);
		waitUntilWidgetAppears(waitForEditor);
		return new RmiSWTBotEditor(waitForEditor.get(0), this);
	}

	/**
	 * @return all editors which are opened currently (no waiting!) which match
	 *         the given matcher
	 * @param matcher
	 *            the matcher used to find all editors
	 */
	public List<RmiSWTBotEditor> editors(Matcher<?> matcher) {
		List<IEditorReference> editors = workbenchContentsFinder
				.findEditors(matcher);

		List<RmiSWTBotEditor> editorBots = new ArrayList<RmiSWTBotEditor>();
		for (IEditorReference editorReference : editors)
			editorBots.add(new RmiSWTBotEditor(editorReference, this));

		return editorBots;
	}

	/**
	 * @return all editors which are opened currently
	 */
	public List<? extends RmiSWTBotEditor> editors() {
		return editors(Matchers.anything());
	}

	/**
	 * Shortcut for editor(withPartName(title))
	 * 
	 * @param fileName
	 *            the the filename on the editor tab
	 * @return the editor with the specified title
	 * @see #editor(Matcher)
	 */
	public RmiSWTBotEditor editorByTitle(String fileName) {
		Matcher<IEditorReference> withPartName = withPartName(fileName);
		return editor(withPartName);
	}

	/**
	 * Shortcut for editor(withPartId(id))
	 * 
	 * @param id
	 *            the the id on the editor tab
	 * @return the editor with the specified title
	 * @see #editor(Matcher)
	 */
	public RmiSWTBotEditor editorById(String id) {
		Matcher<IEditorReference> withPartId = withPartId(id);
		return editor(withPartId);
	}

	/**
	 * Returns the active workbench editor part
	 * 
	 * @return the active editor, if any
	 * @throws WidgetNotFoundException
	 *             if there is no active view
	 */
	public RmiSWTBotEditor activeEditor() {
		IEditorReference editor = workbenchContentsFinder.findActiveEditor();
		if (editor == null)
			throw new WidgetNotFoundException("There is no active editor"); //$NON-NLS-1$
		return new RmiSWTBotEditor(editor, this);
	}

	/**
	 * @return the active perspective in the active workbench page
	 */
	public RmiSWTBotPerspective activePerspective() {
		IPerspectiveDescriptor perspective = workbenchContentsFinder
				.findActivePerspective();
		if (perspective == null)
			throw new WidgetNotFoundException("There is no active perspective"); //$NON-NLS-1$
		return new RmiSWTBotPerspective(perspective, this);
	}
}
