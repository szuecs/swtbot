package org.eclipse.swtbot.eclipse.finder.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.rmi.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.rmi.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.rmi.finders.Finder;
import org.eclipse.swtbot.swt.finder.rmi.results.Result;
import org.eclipse.swtbot.swt.finder.rmi.results.VoidResult;
import org.eclipse.swtbot.swt.finder.rmi.waits.ICondition;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotCCombo;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotCLabel;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotDateTime;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotLink;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotSlider;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotSpinner;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotTabItem;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotToggleButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotToolbarRadioButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotToolbarToggleButton;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotTrayItem;
import org.eclipse.swtbot.swt.finder.rmi.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.rmi.widgets.TimeoutException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;

public class RmiSWTWorkbenchBot implements IRmiSWTWorkbenchBot {

	/** RMI stuff */
	private RmiSWTWorkbenchBot exportedObj;
	private IRmiSWTWorkbenchBot stub;
	protected static Registry registry;

	public static void main(String[] args) {
		new RmiSWTWorkbenchBot(new SWTWorkbenchBot()).init();
	}

	public void init() {
		System.err.println("init..");
		setup();
		shutdown();
		start();
		System.err.println("..init");
	}

	private void setup() {
		System.err.println("setup");
		try {
			exportedObj = new RmiSWTWorkbenchBot(new SWTWorkbenchBot());
			stub = (IRmiSWTWorkbenchBot) UnicastRemoteObject.exportObject(
					exportedObj, 0);

			// Bind the remote object's stub in the registry
			registry = LocateRegistry.getRegistry();
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private void start() {
		System.err.println("start");
		try {
			registry.bind("IRmiSWTWorkbenchBot", stub);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.err.println("Server ready");

	}

	private void shutdown() {
		System.err.println("shutdown hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					System.err.println("shutdown");
					if (registry != null)
						registry.unbind("IRmiSWTWorkbenchBot");
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
				}
			}
		});
	}

	/** delegation stuff */
	private SWTWorkbenchBot delegate;

	public RmiSWTWorkbenchBot(SWTWorkbenchBot delegate) {
		super();
		assert delegate != null : "delegated SWTWorkbenchBot is null";
		this.delegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#activeEditor()
	 */
	public SWTBotEditor activeEditor() {
		return delegate.activeEditor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#activePerspective
	 * ()
	 */
	public SWTBotPerspective activePerspective() {
		return delegate.activePerspective();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#activeShell()
	 */
	public SWTBotShell activeShell() throws WidgetNotFoundException {
		return delegate.activeShell();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#activeView()
	 */
	public SWTBotView activeView() {
		return delegate.activeView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#button()
	 */
	public SWTBotButton button() {
		return delegate.button();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#button(int)
	 */
	public SWTBotButton button(int index) {
		return delegate.button(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#button(java
	 * .lang.String, int)
	 */
	public SWTBotButton button(String mnemonicText, int index) {
		return delegate.button(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#button(java
	 * .lang.String)
	 */
	public SWTBotButton button(String mnemonicText) {
		return delegate.button(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotButton buttonInGroup(String inGroup, int index) {
		return delegate.buttonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.buttonInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup) {
		return delegate.buttonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonInGroup
	 * (java.lang.String)
	 */
	public SWTBotButton buttonInGroup(String inGroup) {
		return delegate.buttonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithId
	 * (java.lang.String, int)
	 */
	public SWTBotButton buttonWithId(String value, int index) {
		return delegate.buttonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotButton buttonWithId(String key, String value, int index) {
		return delegate.buttonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotButton buttonWithId(String key, String value) {
		return delegate.buttonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithId
	 * (java.lang.String)
	 */
	public SWTBotButton buttonWithId(String value) {
		return delegate.buttonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotButton buttonWithLabel(String label, int index) {
		return delegate.buttonWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithLabel
	 * (java.lang.String)
	 */
	public SWTBotButton buttonWithLabel(String label) {
		return delegate.buttonWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * buttonWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.buttonWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * buttonWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup) {
		return delegate.buttonWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotButton buttonWithTooltip(String tooltip, int index) {
		return delegate.buttonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#buttonWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotButton buttonWithTooltip(String tooltip) {
		return delegate.buttonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * buttonWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotButton buttonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.buttonWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * buttonWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.buttonWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#captureScreenshot
	 * (java.lang.String)
	 */
	public boolean captureScreenshot(String fileName) {
		return delegate.captureScreenshot(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBox()
	 */
	public SWTBotCCombo ccomboBox() {
		return delegate.ccomboBox();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBox(int)
	 */
	public SWTBotCCombo ccomboBox(int index) {
		return delegate.ccomboBox(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBox(java
	 * .lang.String, int)
	 */
	public SWTBotCCombo ccomboBox(String text, int index) {
		return delegate.ccomboBox(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBox(java
	 * .lang.String)
	 */
	public SWTBotCCombo ccomboBox(String text) {
		return delegate.ccomboBox(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxInGroup(String inGroup, int index) {
		return delegate.ccomboBoxInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup, int index) {
		return delegate.ccomboBoxInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup) {
		return delegate.ccomboBoxInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxInGroup
	 * (java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxInGroup(String inGroup) {
		return delegate.ccomboBoxInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithId
	 * (java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxWithId(String value, int index) {
		return delegate.ccomboBoxWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxWithId(String key, String value, int index) {
		return delegate.ccomboBoxWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxWithId(String key, String value) {
		return delegate.ccomboBoxWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithId
	 * (java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxWithId(String value) {
		return delegate.ccomboBoxWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxWithLabel(String label, int index) {
		return delegate.ccomboBoxWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#ccomboBoxWithLabel
	 * (java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxWithLabel(String label) {
		return delegate.ccomboBoxWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * ccomboBoxWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.ccomboBoxWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * ccomboBoxWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.ccomboBoxWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBox()
	 */
	public SWTBotCheckBox checkBox() {
		return delegate.checkBox();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBox(int)
	 */
	public SWTBotCheckBox checkBox(int index) {
		return delegate.checkBox(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBox(java
	 * .lang.String, int)
	 */
	public SWTBotCheckBox checkBox(String mnemonicText, int index) {
		return delegate.checkBox(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBox(java
	 * .lang.String)
	 */
	public SWTBotCheckBox checkBox(String mnemonicText) {
		return delegate.checkBox(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxInGroup(String inGroup, int index) {
		return delegate.checkBoxInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.checkBoxInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup) {
		return delegate.checkBoxInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxInGroup
	 * (java.lang.String)
	 */
	public SWTBotCheckBox checkBoxInGroup(String inGroup) {
		return delegate.checkBoxInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithId
	 * (java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithId(String value, int index) {
		return delegate.checkBoxWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithId(String key, String value, int index) {
		return delegate.checkBoxWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithId(String key, String value) {
		return delegate.checkBoxWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithId
	 * (java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithId(String value) {
		return delegate.checkBoxWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithLabel(String label, int index) {
		return delegate.checkBoxWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithLabel
	 * (java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithLabel(String label) {
		return delegate.checkBoxWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * checkBoxWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.checkBoxWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * checkBoxWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.checkBoxWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithTooltip(String tooltip, int index) {
		return delegate.checkBoxWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#checkBoxWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithTooltip(String tooltip) {
		return delegate.checkBoxWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * checkBoxWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.checkBoxWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * checkBoxWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.checkBoxWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabel()
	 */
	public SWTBotCLabel clabel() {
		return delegate.clabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabel(int)
	 */
	public SWTBotCLabel clabel(int index) {
		return delegate.clabel(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabel(java
	 * .lang.String, int)
	 */
	public SWTBotCLabel clabel(String mnemonicText, int index) {
		return delegate.clabel(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabel(java
	 * .lang.String)
	 */
	public SWTBotCLabel clabel(String mnemonicText) {
		return delegate.clabel(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotCLabel clabelInGroup(String inGroup, int index) {
		return delegate.clabelInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.clabelInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup) {
		return delegate.clabelInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelInGroup
	 * (java.lang.String)
	 */
	public SWTBotCLabel clabelInGroup(String inGroup) {
		return delegate.clabelInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelWithId
	 * (java.lang.String, int)
	 */
	public SWTBotCLabel clabelWithId(String value, int index) {
		return delegate.clabelWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCLabel clabelWithId(String key, String value, int index) {
		return delegate.clabelWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCLabel clabelWithId(String key, String value) {
		return delegate.clabelWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#clabelWithId
	 * (java.lang.String)
	 */
	public SWTBotCLabel clabelWithId(String value) {
		return delegate.clabelWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBox()
	 */
	public SWTBotCombo comboBox() {
		return delegate.comboBox();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBox(int)
	 */
	public SWTBotCombo comboBox(int index) {
		return delegate.comboBox(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBox(java
	 * .lang.String, int)
	 */
	public SWTBotCombo comboBox(String text, int index) {
		return delegate.comboBox(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBox(java
	 * .lang.String)
	 */
	public SWTBotCombo comboBox(String text) {
		return delegate.comboBox(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotCombo comboBoxInGroup(String inGroup, int index) {
		return delegate.comboBoxInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCombo comboBoxInGroup(String text, String inGroup, int index) {
		return delegate.comboBoxInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCombo comboBoxInGroup(String text, String inGroup) {
		return delegate.comboBoxInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxInGroup
	 * (java.lang.String)
	 */
	public SWTBotCombo comboBoxInGroup(String inGroup) {
		return delegate.comboBoxInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithId
	 * (java.lang.String, int)
	 */
	public SWTBotCombo comboBoxWithId(String value, int index) {
		return delegate.comboBoxWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCombo comboBoxWithId(String key, String value, int index) {
		return delegate.comboBoxWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCombo comboBoxWithId(String key, String value) {
		return delegate.comboBoxWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithId
	 * (java.lang.String)
	 */
	public SWTBotCombo comboBoxWithId(String value) {
		return delegate.comboBoxWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotCombo comboBoxWithLabel(String label, int index) {
		return delegate.comboBoxWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#comboBoxWithLabel
	 * (java.lang.String)
	 */
	public SWTBotCombo comboBoxWithLabel(String label) {
		return delegate.comboBoxWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * comboBoxWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.comboBoxWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * comboBoxWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.comboBoxWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItem()
	 */
	public SWTBotCTabItem cTabItem() {
		return delegate.cTabItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItem(int)
	 */
	public SWTBotCTabItem cTabItem(int index) {
		return delegate.cTabItem(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItem(java
	 * .lang.String, int)
	 */
	public SWTBotCTabItem cTabItem(String mnemonicText, int index) {
		return delegate.cTabItem(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItem(java
	 * .lang.String)
	 */
	public SWTBotCTabItem cTabItem(String mnemonicText) {
		return delegate.cTabItem(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotCTabItem cTabItemInGroup(String inGroup, int index) {
		return delegate.cTabItemInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.cTabItemInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup) {
		return delegate.cTabItemInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemInGroup
	 * (java.lang.String)
	 */
	public SWTBotCTabItem cTabItemInGroup(String inGroup) {
		return delegate.cTabItemInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemWithId
	 * (java.lang.String, int)
	 */
	public SWTBotCTabItem cTabItemWithId(String value, int index) {
		return delegate.cTabItemWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotCTabItem cTabItemWithId(String key, String value, int index) {
		return delegate.cTabItemWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotCTabItem cTabItemWithId(String key, String value) {
		return delegate.cTabItemWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#cTabItemWithId
	 * (java.lang.String)
	 */
	public SWTBotCTabItem cTabItemWithId(String value) {
		return delegate.cTabItemWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTime()
	 */
	public SWTBotDateTime dateTime() {
		return delegate.dateTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTime(int)
	 */
	public SWTBotDateTime dateTime(int index) {
		return delegate.dateTime(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotDateTime dateTimeInGroup(String inGroup, int index) {
		return delegate.dateTimeInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeInGroup
	 * (java.lang.String)
	 */
	public SWTBotDateTime dateTimeInGroup(String inGroup) {
		return delegate.dateTimeInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithId
	 * (java.lang.String, int)
	 */
	public SWTBotDateTime dateTimeWithId(String value, int index) {
		return delegate.dateTimeWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotDateTime dateTimeWithId(String key, String value, int index) {
		return delegate.dateTimeWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotDateTime dateTimeWithId(String key, String value) {
		return delegate.dateTimeWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithId
	 * (java.lang.String)
	 */
	public SWTBotDateTime dateTimeWithId(String value) {
		return delegate.dateTimeWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotDateTime dateTimeWithLabel(String label, int index) {
		return delegate.dateTimeWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#dateTimeWithLabel
	 * (java.lang.String)
	 */
	public SWTBotDateTime dateTimeWithLabel(String label) {
		return delegate.dateTimeWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * dateTimeWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotDateTime dateTimeWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.dateTimeWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * dateTimeWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotDateTime dateTimeWithLabelInGroup(String label, String inGroup) {
		return delegate.dateTimeWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#editor(org.
	 * hamcrest.Matcher)
	 */
	public SWTBotEditor editor(Matcher<IEditorReference> matcher) {
		return delegate.editor(matcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#editorById(
	 * java.lang.String)
	 */
	public SWTBotEditor editorById(String id) {
		return delegate.editorById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#editorByTitle
	 * (java.lang.String)
	 */
	public SWTBotEditor editorByTitle(String fileName) {
		return delegate.editorByTitle(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#editors()
	 */
	public LinkedList<? extends SWTBotEditor> editors() {
		List<? extends SWTBotEditor> list = delegate.editors();
		LinkedList<? extends SWTBotEditor> ll = new LinkedList(list);
		return ll;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#editors(org
	 * .hamcrest.Matcher)
	 */
	public LinkedList<SWTBotEditor> editors(Matcher<?> matcher) {
		List<SWTBotEditor> list = delegate.editors(matcher);
		return new LinkedList<SWTBotEditor>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#getDisplay()
	 */
	public Display getDisplay() {
		if (true)
			throw new UnsupportedOperationException(
					"Display not defined as Remote (SWT) yet.");

		return delegate.getDisplay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#getFinder()
	 */
	public Finder getFinder() {
		return delegate.getFinder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#getFocusedWidget
	 * ()
	 */
	public Control getFocusedWidget() {
		if (true)
			throw new UnsupportedOperationException(
					"Control not defined as Remote (SWT) yet.");

		return delegate.getFocusedWidget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#label()
	 */
	public SWTBotLabel label() {
		return delegate.label();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#label(int)
	 */
	public SWTBotLabel label(int index) {
		return delegate.label(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#label(java.
	 * lang.String, int)
	 */
	public SWTBotLabel label(String mnemonicText, int index) {
		return delegate.label(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#label(java.
	 * lang.String)
	 */
	public SWTBotLabel label(String mnemonicText) {
		return delegate.label(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotLabel labelInGroup(String inGroup, int index) {
		return delegate.labelInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.labelInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup) {
		return delegate.labelInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelInGroup
	 * (java.lang.String)
	 */
	public SWTBotLabel labelInGroup(String inGroup) {
		return delegate.labelInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelWithId
	 * (java.lang.String, int)
	 */
	public SWTBotLabel labelWithId(String value, int index) {
		return delegate.labelWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotLabel labelWithId(String key, String value, int index) {
		return delegate.labelWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotLabel labelWithId(String key, String value) {
		return delegate.labelWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#labelWithId
	 * (java.lang.String)
	 */
	public SWTBotLabel labelWithId(String value) {
		return delegate.labelWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#link()
	 */
	public SWTBotLink link() {
		return delegate.link();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#link(int)
	 */
	public SWTBotLink link(int index) {
		return delegate.link(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#link(java.lang
	 * .String, int)
	 */
	public SWTBotLink link(String mnemonicText, int index) {
		return delegate.link(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#link(java.lang
	 * .String)
	 */
	public SWTBotLink link(String mnemonicText) {
		return delegate.link(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotLink linkInGroup(String inGroup, int index) {
		return delegate.linkInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotLink linkInGroup(String mnemonicText, String inGroup, int index) {
		return delegate.linkInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotLink linkInGroup(String mnemonicText, String inGroup) {
		return delegate.linkInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkInGroup
	 * (java.lang.String)
	 */
	public SWTBotLink linkInGroup(String inGroup) {
		return delegate.linkInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkWithId(
	 * java.lang.String, int)
	 */
	public SWTBotLink linkWithId(String value, int index) {
		return delegate.linkWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkWithId(
	 * java.lang.String, java.lang.String, int)
	 */
	public SWTBotLink linkWithId(String key, String value, int index) {
		return delegate.linkWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkWithId(
	 * java.lang.String, java.lang.String)
	 */
	public SWTBotLink linkWithId(String key, String value) {
		return delegate.linkWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#linkWithId(
	 * java.lang.String)
	 */
	public SWTBotLink linkWithId(String value) {
		return delegate.linkWithId(value);
	}

	public SWTBotList list() {
		return delegate.list();
	}

	public SWTBotList list(int index) {
		return delegate.list(index);
	}

	public SWTBotList listInGroup(String inGroup, int index) {
		return delegate.listInGroup(inGroup, index);
	}

	public SWTBotList listInGroup(String inGroup) {
		return delegate.listInGroup(inGroup);
	}

	public SWTBotList listWithId(String value, int index) {
		return delegate.listWithId(value, index);
	}

	public SWTBotList listWithId(String key, String value, int index) {
		return delegate.listWithId(key, value, index);
	}

	public SWTBotList listWithId(String key, String value) {
		return delegate.listWithId(key, value);
	}

	public SWTBotList listWithId(String value) {
		return delegate.listWithId(value);
	}

	public SWTBotList listWithLabel(String label, int index) {
		return delegate.listWithLabel(label, index);
	}

	public SWTBotList listWithLabel(String label) {
		return delegate.listWithLabel(label);
	}

	public SWTBotList listWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.listWithLabelInGroup(label, inGroup, index);
	}

	public SWTBotList listWithLabelInGroup(String label, String inGroup) {
		return delegate.listWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menu(java.lang
	 * .String, int)
	 */
	public SWTBotMenu menu(String text, int index) {
		return delegate.menu(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menu(java.lang
	 * .String)
	 */
	public SWTBotMenu menu(String text) {
		return delegate.menu(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menu(org.eclipse
	 * .swtbot.swt.finder.widgets.SWTBotShell, org.hamcrest.Matcher, int)
	 */
	public SWTBotMenu menu(SWTBotShell shell, Matcher<MenuItem> matcher,
			int index) {
		return delegate.menu(shell, matcher, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menuWithId(
	 * java.lang.String, int)
	 */
	public SWTBotMenu menuWithId(String value, int index) {
		return delegate.menuWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menuWithId(
	 * java.lang.String, java.lang.String, int)
	 */
	public SWTBotMenu menuWithId(String key, String value, int index) {
		return delegate.menuWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menuWithId(
	 * java.lang.String, java.lang.String)
	 */
	public SWTBotMenu menuWithId(String key, String value) {
		return delegate.menuWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#menuWithId(
	 * java.lang.String)
	 */
	public SWTBotMenu menuWithId(String value) {
		return delegate.menuWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#performWithTimeout
	 * (org.eclipse.swtbot.swt.finder.results.Result, long)
	 */
	public <T> T performWithTimeout(Result<T> runnable, long timeout) {
		if (true)
			throw new UnsupportedOperationException(
					"T performWithTimeout(Result<T>,long) is not supported yet.");

		return delegate.performWithTimeout(runnable, timeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#performWithTimeout
	 * (org.eclipse.swtbot.swt.finder.results.VoidResult, long)
	 */
	public void performWithTimeout(VoidResult runnable, long timeout) {
		delegate.performWithTimeout(runnable, timeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#perspective
	 * (org.hamcrest.Matcher)
	 */
	public SWTBotPerspective perspective(Matcher<?> matcher) {
		return delegate.perspective(matcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#perspectiveById
	 * (java.lang.String)
	 */
	public SWTBotPerspective perspectiveById(String id) {
		return delegate.perspectiveById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#perspectiveByLabel
	 * (java.lang.String)
	 */
	public SWTBotPerspective perspectiveByLabel(String label) {
		return delegate.perspectiveByLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#perspectives()
	 */
	public List<SWTBotPerspective> perspectives() {
		List<SWTBotPerspective> list = delegate.perspectives();
		return new LinkedList<SWTBotPerspective>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#perspectives
	 * (org.hamcrest.Matcher)
	 */
	public List<SWTBotPerspective> perspectives(Matcher<?> matcher) {
		List<SWTBotPerspective> list = delegate.perspectives(matcher);
		return new LinkedList<SWTBotPerspective>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radio()
	 */
	public SWTBotRadio radio() {
		return delegate.radio();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radio(int)
	 */
	public SWTBotRadio radio(int index) {
		return delegate.radio(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radio(java.
	 * lang.String, int)
	 */
	public SWTBotRadio radio(String mnemonicText, int index) {
		return delegate.radio(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radio(java.
	 * lang.String)
	 */
	public SWTBotRadio radio(String mnemonicText) {
		return delegate.radio(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotRadio radioInGroup(String inGroup, int index) {
		return delegate.radioInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.radioInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup) {
		return delegate.radioInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioInGroup
	 * (java.lang.String)
	 */
	public SWTBotRadio radioInGroup(String inGroup) {
		return delegate.radioInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithId
	 * (java.lang.String, int)
	 */
	public SWTBotRadio radioWithId(String value, int index) {
		return delegate.radioWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotRadio radioWithId(String key, String value, int index) {
		return delegate.radioWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotRadio radioWithId(String key, String value) {
		return delegate.radioWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithId
	 * (java.lang.String)
	 */
	public SWTBotRadio radioWithId(String value) {
		return delegate.radioWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotRadio radioWithLabel(String label, int index) {
		return delegate.radioWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithLabel
	 * (java.lang.String)
	 */
	public SWTBotRadio radioWithLabel(String label) {
		return delegate.radioWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * radioWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.radioWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * radioWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup) {
		return delegate.radioWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotRadio radioWithTooltip(String tooltip, int index) {
		return delegate.radioWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#radioWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotRadio radioWithTooltip(String tooltip) {
		return delegate.radioWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * radioWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup,
			int index) {
		return delegate.radioWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * radioWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.radioWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shell(java.
	 * lang.String, int)
	 */
	public SWTBotShell shell(String text, int index) {
		return delegate.shell(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shell(java.
	 * lang.String, org.eclipse.swt.widgets.Shell, int)
	 */
	public SWTBotShell shell(String text, Shell parent, int index) {
		return delegate.shell(text, parent, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shell(java.
	 * lang.String, org.eclipse.swt.widgets.Shell)
	 */
	public SWTBotShell shell(String text, Shell parent) {
		return delegate.shell(text, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shell(java.
	 * lang.String)
	 */
	public SWTBotShell shell(String text) {
		return delegate.shell(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shells()
	 */
	public SWTBotShell[] shells() {
		return delegate.shells();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shells(java
	 * .lang.String, org.eclipse.swt.widgets.Shell)
	 */
	public List<Shell> shells(String text, Shell parent) {
		List<Shell> list = delegate.shells(text, parent);
		return new LinkedList<Shell>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shells(java
	 * .lang.String)
	 */
	public List<Shell> shells(String text) {
		List<Shell> list = delegate.shells(text);
		return new LinkedList<Shell>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shellWithId
	 * (java.lang.String, int)
	 */
	public SWTBotShell shellWithId(String value, int index) {
		return delegate.shellWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shellWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotShell shellWithId(String key, String value, int index) {
		return delegate.shellWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shellWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotShell shellWithId(String key, String value) {
		return delegate.shellWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#shellWithId
	 * (java.lang.String)
	 */
	public SWTBotShell shellWithId(String value) {
		return delegate.shellWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#slider()
	 */
	public SWTBotSlider slider() {
		return delegate.slider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#slider(int)
	 */
	public SWTBotSlider slider(int index) {
		return delegate.slider(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#slider(java
	 * .lang.String, int)
	 */
	public SWTBotSlider slider(String text, int index) {
		return delegate.slider(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#slider(java
	 * .lang.String)
	 */
	public SWTBotSlider slider(String text) {
		return delegate.slider(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotSlider sliderInGroup(String inGroup, int index) {
		return delegate.sliderInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotSlider sliderInGroup(String text, String inGroup, int index) {
		return delegate.sliderInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotSlider sliderInGroup(String text, String inGroup) {
		return delegate.sliderInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderInGroup
	 * (java.lang.String)
	 */
	public SWTBotSlider sliderInGroup(String inGroup) {
		return delegate.sliderInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithId
	 * (java.lang.String, int)
	 */
	public SWTBotSlider sliderWithId(String value, int index) {
		return delegate.sliderWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotSlider sliderWithId(String key, String value, int index) {
		return delegate.sliderWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotSlider sliderWithId(String key, String value) {
		return delegate.sliderWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithId
	 * (java.lang.String)
	 */
	public SWTBotSlider sliderWithId(String value) {
		return delegate.sliderWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotSlider sliderWithLabel(String label, int index) {
		return delegate.sliderWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithLabel
	 * (java.lang.String)
	 */
	public SWTBotSlider sliderWithLabel(String label) {
		return delegate.sliderWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * sliderWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.sliderWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * sliderWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup) {
		return delegate.sliderWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotSlider sliderWithTooltip(String tooltip, int index) {
		return delegate.sliderWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#sliderWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotSlider sliderWithTooltip(String tooltip) {
		return delegate.sliderWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * sliderWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotSlider sliderWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.sliderWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * sliderWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotSlider sliderWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.sliderWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinner()
	 */
	public SWTBotSpinner spinner() {
		return delegate.spinner();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinner(int)
	 */
	public SWTBotSpinner spinner(int index) {
		return delegate.spinner(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinner(java
	 * .lang.String, int)
	 */
	public SWTBotSpinner spinner(String text, int index) {
		return delegate.spinner(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinner(java
	 * .lang.String)
	 */
	public SWTBotSpinner spinner(String text) {
		return delegate.spinner(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotSpinner spinnerInGroup(String inGroup, int index) {
		return delegate.spinnerInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotSpinner spinnerInGroup(String text, String inGroup, int index) {
		return delegate.spinnerInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotSpinner spinnerInGroup(String text, String inGroup) {
		return delegate.spinnerInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerInGroup
	 * (java.lang.String)
	 */
	public SWTBotSpinner spinnerInGroup(String inGroup) {
		return delegate.spinnerInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithId
	 * (java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithId(String value, int index) {
		return delegate.spinnerWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithId(String key, String value, int index) {
		return delegate.spinnerWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotSpinner spinnerWithId(String key, String value) {
		return delegate.spinnerWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithId
	 * (java.lang.String)
	 */
	public SWTBotSpinner spinnerWithId(String value) {
		return delegate.spinnerWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithLabel(String label, int index) {
		return delegate.spinnerWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithLabel
	 * (java.lang.String)
	 */
	public SWTBotSpinner spinnerWithLabel(String label) {
		return delegate.spinnerWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * spinnerWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.spinnerWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * spinnerWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup) {
		return delegate.spinnerWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithTooltip(String tooltip, int index) {
		return delegate.spinnerWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#spinnerWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotSpinner spinnerWithTooltip(String tooltip) {
		return delegate.spinnerWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * spinnerWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.spinnerWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * spinnerWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.spinnerWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledText()
	 */
	public SWTBotStyledText styledText() {
		return delegate.styledText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledText(int)
	 */
	public SWTBotStyledText styledText(int index) {
		return delegate.styledText(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledText(
	 * java.lang.String, int)
	 */
	public SWTBotStyledText styledText(String text, int index) {
		return delegate.styledText(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledText(
	 * java.lang.String)
	 */
	public SWTBotStyledText styledText(String text) {
		return delegate.styledText(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotStyledText styledTextInGroup(String inGroup, int index) {
		return delegate.styledTextInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotStyledText styledTextInGroup(String text, String inGroup,
			int index) {
		return delegate.styledTextInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotStyledText styledTextInGroup(String text, String inGroup) {
		return delegate.styledTextInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextInGroup
	 * (java.lang.String)
	 */
	public SWTBotStyledText styledTextInGroup(String inGroup) {
		return delegate.styledTextInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithId
	 * (java.lang.String, int)
	 */
	public SWTBotStyledText styledTextWithId(String value, int index) {
		return delegate.styledTextWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotStyledText styledTextWithId(String key, String value, int index) {
		return delegate.styledTextWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotStyledText styledTextWithId(String key, String value) {
		return delegate.styledTextWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithId
	 * (java.lang.String)
	 */
	public SWTBotStyledText styledTextWithId(String value) {
		return delegate.styledTextWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotStyledText styledTextWithLabel(String label, int index) {
		return delegate.styledTextWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#styledTextWithLabel
	 * (java.lang.String)
	 */
	public SWTBotStyledText styledTextWithLabel(String label) {
		return delegate.styledTextWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * styledTextWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.styledTextWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * styledTextWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup) {
		return delegate.styledTextWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItem()
	 */
	public SWTBotTabItem tabItem() {
		return delegate.tabItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItem(int)
	 */
	public SWTBotTabItem tabItem(int index) {
		return delegate.tabItem(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItem(java
	 * .lang.String, int)
	 */
	public SWTBotTabItem tabItem(String mnemonicText, int index) {
		return delegate.tabItem(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItem(java
	 * .lang.String)
	 */
	public SWTBotTabItem tabItem(String mnemonicText) {
		return delegate.tabItem(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotTabItem tabItemInGroup(String inGroup, int index) {
		return delegate.tabItemInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.tabItemInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup) {
		return delegate.tabItemInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemInGroup
	 * (java.lang.String)
	 */
	public SWTBotTabItem tabItemInGroup(String inGroup) {
		return delegate.tabItemInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemWithId
	 * (java.lang.String, int)
	 */
	public SWTBotTabItem tabItemWithId(String value, int index) {
		return delegate.tabItemWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotTabItem tabItemWithId(String key, String value, int index) {
		return delegate.tabItemWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotTabItem tabItemWithId(String key, String value) {
		return delegate.tabItemWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tabItemWithId
	 * (java.lang.String)
	 */
	public SWTBotTabItem tabItemWithId(String value) {
		return delegate.tabItemWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#table()
	 */
	public SWTBotTable table() {
		return delegate.table();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#table(int)
	 */
	public SWTBotTable table(int index) {
		return delegate.table(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotTable tableInGroup(String inGroup, int index) {
		return delegate.tableInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableInGroup
	 * (java.lang.String)
	 */
	public SWTBotTable tableInGroup(String inGroup) {
		return delegate.tableInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithId
	 * (java.lang.String, int)
	 */
	public SWTBotTable tableWithId(String value, int index) {
		return delegate.tableWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotTable tableWithId(String key, String value, int index) {
		return delegate.tableWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotTable tableWithId(String key, String value) {
		return delegate.tableWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithId
	 * (java.lang.String)
	 */
	public SWTBotTable tableWithId(String value) {
		return delegate.tableWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotTable tableWithLabel(String label, int index) {
		return delegate.tableWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tableWithLabel
	 * (java.lang.String)
	 */
	public SWTBotTable tableWithLabel(String label) {
		return delegate.tableWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * tableWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotTable tableWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.tableWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * tableWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotTable tableWithLabelInGroup(String label, String inGroup) {
		return delegate.tableWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#text()
	 */
	public SWTBotText text() {
		return delegate.text();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#text(int)
	 */
	public SWTBotText text(int index) {
		return delegate.text(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#text(java.lang
	 * .String, int)
	 */
	public SWTBotText text(String text, int index) {
		return delegate.text(text, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#text(java.lang
	 * .String)
	 */
	public SWTBotText text(String text) {
		return delegate.text(text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotText textInGroup(String inGroup, int index) {
		return delegate.textInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotText textInGroup(String text, String inGroup, int index) {
		return delegate.textInGroup(text, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotText textInGroup(String text, String inGroup) {
		return delegate.textInGroup(text, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textInGroup
	 * (java.lang.String)
	 */
	public SWTBotText textInGroup(String inGroup) {
		return delegate.textInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithId(
	 * java.lang.String, int)
	 */
	public SWTBotText textWithId(String value, int index) {
		return delegate.textWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithId(
	 * java.lang.String, java.lang.String, int)
	 */
	public SWTBotText textWithId(String key, String value, int index) {
		return delegate.textWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithId(
	 * java.lang.String, java.lang.String)
	 */
	public SWTBotText textWithId(String key, String value) {
		return delegate.textWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithId(
	 * java.lang.String)
	 */
	public SWTBotText textWithId(String value) {
		return delegate.textWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotText textWithLabel(String label, int index) {
		return delegate.textWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithLabel
	 * (java.lang.String)
	 */
	public SWTBotText textWithLabel(String label) {
		return delegate.textWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * textWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotText textWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.textWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * textWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotText textWithLabelInGroup(String label, String inGroup) {
		return delegate.textWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotText textWithTooltip(String tooltip, int index) {
		return delegate.textWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#textWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotText textWithTooltip(String tooltip) {
		return delegate.textWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * textWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup,
			int index) {
		return delegate.textWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * textWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.textWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButton()
	 */
	public SWTBotToggleButton toggleButton() {
		return delegate.toggleButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButton
	 * (int)
	 */
	public SWTBotToggleButton toggleButton(int index) {
		return delegate.toggleButton(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButton
	 * (java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButton(String mnemonicText, int index) {
		return delegate.toggleButton(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButton
	 * (java.lang.String)
	 */
	public SWTBotToggleButton toggleButton(String mnemonicText) {
		return delegate.toggleButton(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonInGroup(String inGroup, int index) {
		return delegate.toggleButtonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonInGroup
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup, int index) {
		return delegate.toggleButtonInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonInGroup
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup) {
		return delegate.toggleButtonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonInGroup
	 * (java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonInGroup(String inGroup) {
		return delegate.toggleButtonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonWithId
	 * (java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithId(String value, int index) {
		return delegate.toggleButtonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithId(String key, String value,
			int index) {
		return delegate.toggleButtonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithId(String key, String value) {
		return delegate.toggleButtonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toggleButtonWithId
	 * (java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithId(String value) {
		return delegate.toggleButtonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithLabel(java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithLabel(String label, int index) {
		return delegate.toggleButtonWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithLabel(java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithLabel(String label) {
		return delegate.toggleButtonWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.toggleButtonWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup) {
		return delegate.toggleButtonWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithTooltip(java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip, int index) {
		return delegate.toggleButtonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithTooltip(java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip) {
		return delegate.toggleButtonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.toggleButtonWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toggleButtonWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.toggleButtonWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButton()
	 */
	public SWTBotToolbarButton toolbarButton() {
		return delegate.toolbarButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButton
	 * (int)
	 */
	public SWTBotToolbarButton toolbarButton(int index) {
		return delegate.toolbarButton(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButton
	 * (java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButton(String mnemonicText, int index) {
		return delegate.toolbarButton(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButton
	 * (java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButton(String mnemonicText) {
		return delegate.toolbarButton(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonInGroup(java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup, int index) {
		return delegate.toolbarButtonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup, int index) {
		return delegate.toolbarButtonInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup) {
		return delegate.toolbarButtonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonInGroup(java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup) {
		return delegate.toolbarButtonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButtonWithId
	 * (java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String value, int index) {
		return delegate.toolbarButtonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButtonWithId
	 * (java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String key, String value,
			int index) {
		return delegate.toolbarButtonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButtonWithId
	 * (java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String key, String value) {
		return delegate.toolbarButtonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarButtonWithId
	 * (java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonWithId(String value) {
		return delegate.toolbarButtonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonWithTooltip(java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip,
			int index) {
		return delegate.toolbarButtonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonWithTooltip(java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip) {
		return delegate.toolbarButtonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonWithTooltipInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate
				.toolbarButtonWithTooltipInGroup(tooltip, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarButtonWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.toolbarButtonWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButton()
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton() {
		return delegate.toolbarDropDownButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButton(int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton(int index) {
		return delegate.toolbarDropDownButton(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButton(java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton(
			String mnemonicText, int index) {
		return delegate.toolbarDropDownButton(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButton(java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButton(String mnemonicText) {
		return delegate.toolbarDropDownButton(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonInGroup(java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup, int index) {
		return delegate.toolbarDropDownButtonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate.toolbarDropDownButtonInGroup(mnemonicText, inGroup,
				index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarDropDownButtonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonInGroup(java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup) {
		return delegate.toolbarDropDownButtonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithId(java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(
			String value, int index) {
		return delegate.toolbarDropDownButtonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithId(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarDropDownButtonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithId(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value) {
		return delegate.toolbarDropDownButtonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithId(java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String value) {
		return delegate.toolbarDropDownButtonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithTooltip(java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarDropDownButtonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithTooltip(java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip) {
		return delegate.toolbarDropDownButtonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithTooltipInGroup(java.lang.String,
	 * java.lang.String, int)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarDropDownButtonWithTooltipInGroup(tooltip,
				inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarDropDownButtonWithTooltipInGroup(java.lang.String,
	 * java.lang.String)
	 */
	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarDropDownButtonWithTooltipInGroup(tooltip,
				inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarRadioButton
	 * ()
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton() {
		return delegate.toolbarRadioButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarRadioButton
	 * (int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton(int index) {
		return delegate.toolbarRadioButton(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarRadioButton
	 * (java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText,
			int index) {
		return delegate.toolbarRadioButton(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarRadioButton
	 * (java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText) {
		return delegate.toolbarRadioButton(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonInGroup(java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup,
			int index) {
		return delegate.toolbarRadioButtonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate.toolbarRadioButtonInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarRadioButtonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonInGroup(java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup) {
		return delegate.toolbarRadioButtonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithId(java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value,
			int index) {
		return delegate.toolbarRadioButtonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithId(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarRadioButtonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithId(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value) {
		return delegate.toolbarRadioButtonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithId(java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value) {
		return delegate.toolbarRadioButtonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithTooltip(java.lang.String, int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarRadioButtonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithTooltip(java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(String tooltip) {
		return delegate.toolbarRadioButtonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithTooltipInGroup(java.lang.String, java.lang.String,
	 * int)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarRadioButtonWithTooltipInGroup(tooltip, inGroup,
				index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarRadioButtonWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarRadioButtonWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarToggleButton
	 * ()
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton() {
		return delegate.toolbarToggleButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarToggleButton
	 * (int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton(int index) {
		return delegate.toolbarToggleButton(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarToggleButton
	 * (java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText,
			int index) {
		return delegate.toolbarToggleButton(mnemonicText, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#toolbarToggleButton
	 * (java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText) {
		return delegate.toolbarToggleButton(mnemonicText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonInGroup(java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup,
			int index) {
		return delegate.toolbarToggleButtonInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate
				.toolbarToggleButtonInGroup(mnemonicText, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarToggleButtonInGroup(mnemonicText, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonInGroup(java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup) {
		return delegate.toolbarToggleButtonInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithId(java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value,
			int index) {
		return delegate.toolbarToggleButtonWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithId(java.lang.String, java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarToggleButtonWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithId(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value) {
		return delegate.toolbarToggleButtonWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithId(java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value) {
		return delegate.toolbarToggleButtonWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithTooltip(java.lang.String, int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarToggleButtonWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithTooltip(java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip) {
		return delegate.toolbarToggleButtonWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithTooltipInGroup(java.lang.String, java.lang.String,
	 * int)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarToggleButtonWithTooltipInGroup(tooltip, inGroup,
				index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * toolbarToggleButtonWithTooltipInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarToggleButtonWithTooltipInGroup(tooltip, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItem()
	 */
	public SWTBotTrayItem trayItem() {
		return delegate.trayItem();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItem(int)
	 */
	public SWTBotTrayItem trayItem(int index) {
		return delegate.trayItem(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItems()
	 */
	public List<SWTBotTrayItem> trayItems() {
		List<SWTBotTrayItem> list = delegate.trayItems();
		return new LinkedList<SWTBotTrayItem>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItems(org
	 * .hamcrest.Matcher)
	 */
	public List<SWTBotTrayItem> trayItems(Matcher<?> matcher) {
		List<SWTBotTrayItem> list = delegate.trayItems(matcher);
		return new LinkedList<SWTBotTrayItem>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItemWithTooltip
	 * (java.lang.String, int)
	 */
	public SWTBotTrayItem trayItemWithTooltip(String tooltip, int index) {
		return delegate.trayItemWithTooltip(tooltip, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#trayItemWithTooltip
	 * (java.lang.String)
	 */
	public SWTBotTrayItem trayItemWithTooltip(String tooltip) {
		return delegate.trayItemWithTooltip(tooltip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tree()
	 */
	public SWTBotTree tree() {
		return delegate.tree();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#tree(int)
	 */
	public SWTBotTree tree(int index) {
		return delegate.tree(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeInGroup
	 * (java.lang.String, int)
	 */
	public SWTBotTree treeInGroup(String inGroup, int index) {
		return delegate.treeInGroup(inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeInGroup
	 * (java.lang.String)
	 */
	public SWTBotTree treeInGroup(String inGroup) {
		return delegate.treeInGroup(inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithId(
	 * java.lang.String, int)
	 */
	public SWTBotTree treeWithId(String value, int index) {
		return delegate.treeWithId(value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithId(
	 * java.lang.String, java.lang.String, int)
	 */
	public SWTBotTree treeWithId(String key, String value, int index) {
		return delegate.treeWithId(key, value, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithId(
	 * java.lang.String, java.lang.String)
	 */
	public SWTBotTree treeWithId(String key, String value) {
		return delegate.treeWithId(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithId(
	 * java.lang.String)
	 */
	public SWTBotTree treeWithId(String value) {
		return delegate.treeWithId(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithLabel
	 * (java.lang.String, int)
	 */
	public SWTBotTree treeWithLabel(String label, int index) {
		return delegate.treeWithLabel(label, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#treeWithLabel
	 * (java.lang.String)
	 */
	public SWTBotTree treeWithLabel(String label) {
		return delegate.treeWithLabel(label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * treeWithLabelInGroup(java.lang.String, java.lang.String, int)
	 */
	public SWTBotTree treeWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.treeWithLabelInGroup(label, inGroup, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * treeWithLabelInGroup(java.lang.String, java.lang.String)
	 */
	public SWTBotTree treeWithLabelInGroup(String label, String inGroup) {
		return delegate.treeWithLabelInGroup(label, inGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#view(org.hamcrest
	 * .Matcher)
	 */
	public SWTBotView view(Matcher<IViewReference> matcher) {
		return delegate.view(matcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#viewById(java
	 * .lang.String)
	 */
	public SWTBotView viewById(String id) {
		return delegate.viewById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#viewByTitle
	 * (java.lang.String)
	 */
	public SWTBotView viewByTitle(String title) {
		return delegate.viewByTitle(title);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#views()
	 */
	public List<SWTBotView> views() {
		List<SWTBotView> list = delegate.views();
		return new LinkedList<SWTBotView>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#views(org.hamcrest
	 * .Matcher)
	 */
	public List<SWTBotView> views(Matcher<?> matcher) {
		List<SWTBotView> list = delegate.views(matcher);
		return new LinkedList<SWTBotView>(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitUntil(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition, long, long)
	 */
	public void waitUntil(ICondition condition, long timeout, long interval)
			throws RemoteException {
		try {
			delegate.waitUntil(condition, timeout, interval);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitUntil(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition, long)
	 */
	public void waitUntil(ICondition condition, long timeout)
			throws RemoteException {
		try {
			delegate.waitUntil(condition, timeout);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitUntil(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	public void waitUntil(ICondition condition) throws RemoteException {
		try {
			delegate.waitUntil(condition);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#
	 * waitUntilWidgetAppears(org.eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	public void waitUntilWidgetAppears(ICondition waitForWidget) {
		delegate.waitUntilWidgetAppears(waitForWidget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitWhile(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition, long, long)
	 */
	public void waitWhile(ICondition condition, long timeout, long interval)
			throws RemoteException {
		try {
			delegate.waitWhile(condition, timeout, interval);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitWhile(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition, long)
	 */
	public void waitWhile(ICondition condition, long timeout)
			throws RemoteException {
		try {
			delegate.waitWhile(condition, timeout);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#waitWhile(org
	 * .eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	public void waitWhile(ICondition condition) throws RemoteException {
		try {
			delegate.waitWhile(condition);
		} catch (TimeoutException e) {
			throw new RemoteException("wrapped", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widget(org.
	 * hamcrest.Matcher, int)
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, int index) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widget(matcher, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widget(org.
	 * hamcrest.Matcher, org.eclipse.swt.widgets.Widget, int)
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget,
			int index) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widget(matcher, parentWidget, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widget(org.
	 * hamcrest.Matcher, org.eclipse.swt.widgets.Widget)
	 */
	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widget(matcher, parentWidget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widget(org.
	 * hamcrest.Matcher)
	 */
	public <T extends Widget> T widget(Matcher<T> matcher) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widget(matcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widgets(org
	 * .hamcrest.Matcher, org.eclipse.swt.widgets.Widget)
	 */
	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher,
			Widget parentWidget) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widgets(matcher, parentWidget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swtbot.eclipse.finder.rmi.IRmiSWTWorkbenchBot#widgets(org
	 * .hamcrest.Matcher)
	 */
	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher) {
		if (true)
			throw new UnsupportedOperationException(
					"Widget not defined as Remote (SWT) yet.");

		return delegate.widgets(matcher);
	}

}
