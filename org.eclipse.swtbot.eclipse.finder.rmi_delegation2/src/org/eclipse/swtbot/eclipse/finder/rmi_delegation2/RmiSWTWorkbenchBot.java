package org.eclipse.swtbot.eclipse.finder.rmi_delegation2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.rmi_delegation2.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.finder.rmi_delegation2.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.rmi_delegation2.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.Finder;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotDateTime;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLink;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotList;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotSlider;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotSpinner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToggleButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarDropDownButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarRadioButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarToggleButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTrayItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class RmiSWTWorkbenchBot implements IRmiSWTWorkbenchBot {
	/** RMI stuff */
	// private transient RmiSWTWorkbenchBot exportedObj;
	public IRmiSWTWorkbenchBot stub;
	protected transient Registry registry;

	@BeforeClass
	public static void startRmi() {
		System.err.println("startRmi");
		SWTWorkbenchBot swtwbb = new SWTWorkbenchBot();
		System.err.println("swtwbb");
		new RmiSWTWorkbenchBot(swtwbb).init();
		System.err.println("init");
	}

	// public static void main(String[] args) throws Exception {
	// System.err.println("main"); // RmiSWTWorkbenchBot
	// SWTWorkbenchBot swtwbb = new SWTWorkbenchBot();
	// System.err.println("swtwbb");
	// new RmiSWTWorkbenchBot(swtwbb).init();
	// System.err.println("init");
	// }

	public RmiSWTWorkbenchBot(SWTWorkbenchBot delegate) {
		super();
		System.err.println("Constructor");
		assert delegate != null : "delegated SWTWorkbenchBot is null";
		this.delegate = delegate;
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
			// exportedObj = this;
			// get exported stub
			stub = (IRmiSWTWorkbenchBot) UnicastRemoteObject.exportObject(this,
					0);
			// set local registry
			registry = LocateRegistry.getRegistry();
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private void start() {
		System.err.println("start");
		try {
			// Bind the remote object's stub in the registry
			registry.bind("RmiSWTWorkbenchBot", stub);
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

	/**
	 * transient delegation
	 * 
	 * These represent local calls in one SWTBot instance.
	 */
	private transient SWTWorkbenchBot delegate;

	private SWTBotEditor activeEditor() {
		return delegate.activeEditor();
	}

	private SWTBotPerspective activePerspective() {
		return delegate.activePerspective();
	}

	private SWTBotShell activeShell() throws WidgetNotFoundException {
		return delegate.activeShell();
	}

	private SWTBotView activeView() {
		return delegate.activeView();
	}

	private SWTBotButton button() {
		return delegate.button();
	}

	private SWTBotButton button(int index) {
		return delegate.button(index);
	}

	private SWTBotButton button(String mnemonicText, int index) {
		return delegate.button(mnemonicText, index);
	}

	private SWTBotButton button(String mnemonicText) {
		return delegate.button(mnemonicText);
	}

	private SWTBotButton buttonInGroup(String inGroup, int index) {
		return delegate.buttonInGroup(inGroup, index);
	}

	private SWTBotButton buttonInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.buttonInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotButton buttonInGroup(String mnemonicText, String inGroup) {
		return delegate.buttonInGroup(mnemonicText, inGroup);
	}

	private SWTBotButton buttonInGroup(String inGroup) {
		return delegate.buttonInGroup(inGroup);
	}

	private SWTBotButton buttonWithId(String value, int index) {
		return delegate.buttonWithId(value, index);
	}

	private SWTBotButton buttonWithId(String key, String value, int index) {
		return delegate.buttonWithId(key, value, index);
	}

	private SWTBotButton buttonWithId(String key, String value) {
		return delegate.buttonWithId(key, value);
	}

	private SWTBotButton buttonWithId(String value) {
		return delegate.buttonWithId(value);
	}

	private SWTBotButton buttonWithLabel(String label, int index) {
		return delegate.buttonWithLabel(label, index);
	}

	private SWTBotButton buttonWithLabel(String label) {
		return delegate.buttonWithLabel(label);
	}

	private SWTBotButton buttonWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.buttonWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotButton buttonWithLabelInGroup(String label, String inGroup) {
		return delegate.buttonWithLabelInGroup(label, inGroup);
	}

	private SWTBotButton buttonWithTooltip(String tooltip, int index) {
		return delegate.buttonWithTooltip(tooltip, index);
	}

	private SWTBotButton buttonWithTooltip(String tooltip) {
		return delegate.buttonWithTooltip(tooltip);
	}

	private SWTBotButton buttonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.buttonWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.buttonWithTooltipInGroup(tooltip, inGroup);
	}

	private boolean captureScreenshot(String fileName) {
		return delegate.captureScreenshot(fileName);
	}

	private SWTBotCCombo ccomboBox() {
		return delegate.ccomboBox();
	}

	private SWTBotCCombo ccomboBox(int index) {
		return delegate.ccomboBox(index);
	}

	private SWTBotCCombo ccomboBox(String text, int index) {
		return delegate.ccomboBox(text, index);
	}

	private SWTBotCCombo ccomboBox(String text) {
		return delegate.ccomboBox(text);
	}

	private SWTBotCCombo ccomboBoxInGroup(String inGroup, int index) {
		return delegate.ccomboBoxInGroup(inGroup, index);
	}

	private SWTBotCCombo ccomboBoxInGroup(String text, String inGroup, int index) {
		return delegate.ccomboBoxInGroup(text, inGroup, index);
	}

	private SWTBotCCombo ccomboBoxInGroup(String text, String inGroup) {
		return delegate.ccomboBoxInGroup(text, inGroup);
	}

	private SWTBotCCombo ccomboBoxInGroup(String inGroup) {
		return delegate.ccomboBoxInGroup(inGroup);
	}

	private SWTBotCCombo ccomboBoxWithId(String value, int index) {
		return delegate.ccomboBoxWithId(value, index);
	}

	private SWTBotCCombo ccomboBoxWithId(String key, String value, int index) {
		return delegate.ccomboBoxWithId(key, value, index);
	}

	private SWTBotCCombo ccomboBoxWithId(String key, String value) {
		return delegate.ccomboBoxWithId(key, value);
	}

	private SWTBotCCombo ccomboBoxWithId(String value) {
		return delegate.ccomboBoxWithId(value);
	}

	private SWTBotCCombo ccomboBoxWithLabel(String label, int index) {
		return delegate.ccomboBoxWithLabel(label, index);
	}

	private SWTBotCCombo ccomboBoxWithLabel(String label) {
		return delegate.ccomboBoxWithLabel(label);
	}

	private SWTBotCCombo ccomboBoxWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.ccomboBoxWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.ccomboBoxWithLabelInGroup(label, inGroup);
	}

	private SWTBotCheckBox checkBox() {
		return delegate.checkBox();
	}

	private SWTBotCheckBox checkBox(int index) {
		return delegate.checkBox(index);
	}

	private SWTBotCheckBox checkBox(String mnemonicText, int index) {
		return delegate.checkBox(mnemonicText, index);
	}

	private SWTBotCheckBox checkBox(String mnemonicText) {
		return delegate.checkBox(mnemonicText);
	}

	private SWTBotCheckBox checkBoxInGroup(String inGroup, int index) {
		return delegate.checkBoxInGroup(inGroup, index);
	}

	private SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.checkBoxInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup) {
		return delegate.checkBoxInGroup(mnemonicText, inGroup);
	}

	private SWTBotCheckBox checkBoxInGroup(String inGroup) {
		return delegate.checkBoxInGroup(inGroup);
	}

	private SWTBotCheckBox checkBoxWithId(String value, int index) {
		return delegate.checkBoxWithId(value, index);
	}

	private SWTBotCheckBox checkBoxWithId(String key, String value, int index) {
		return delegate.checkBoxWithId(key, value, index);
	}

	private SWTBotCheckBox checkBoxWithId(String key, String value) {
		return delegate.checkBoxWithId(key, value);
	}

	private SWTBotCheckBox checkBoxWithId(String value) {
		return delegate.checkBoxWithId(value);
	}

	private SWTBotCheckBox checkBoxWithLabel(String label, int index) {
		return delegate.checkBoxWithLabel(label, index);
	}

	private SWTBotCheckBox checkBoxWithLabel(String label) {
		return delegate.checkBoxWithLabel(label);
	}

	private SWTBotCheckBox checkBoxWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.checkBoxWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotCheckBox checkBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.checkBoxWithLabelInGroup(label, inGroup);
	}

	private SWTBotCheckBox checkBoxWithTooltip(String tooltip, int index) {
		return delegate.checkBoxWithTooltip(tooltip, index);
	}

	private SWTBotCheckBox checkBoxWithTooltip(String tooltip) {
		return delegate.checkBoxWithTooltip(tooltip);
	}

	private SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.checkBoxWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.checkBoxWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotCLabel clabel() {
		return delegate.clabel();
	}

	private SWTBotCLabel clabel(int index) {
		return delegate.clabel(index);
	}

	private SWTBotCLabel clabel(String mnemonicText, int index) {
		return delegate.clabel(mnemonicText, index);
	}

	private SWTBotCLabel clabel(String mnemonicText) {
		return delegate.clabel(mnemonicText);
	}

	private SWTBotCLabel clabelInGroup(String inGroup, int index) {
		return delegate.clabelInGroup(inGroup, index);
	}

	private SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.clabelInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup) {
		return delegate.clabelInGroup(mnemonicText, inGroup);
	}

	private SWTBotCLabel clabelInGroup(String inGroup) {
		return delegate.clabelInGroup(inGroup);
	}

	private SWTBotCLabel clabelWithId(String value, int index) {
		return delegate.clabelWithId(value, index);
	}

	private SWTBotCLabel clabelWithId(String key, String value, int index) {
		return delegate.clabelWithId(key, value, index);
	}

	private SWTBotCLabel clabelWithId(String key, String value) {
		return delegate.clabelWithId(key, value);
	}

	private SWTBotCLabel clabelWithId(String value) {
		return delegate.clabelWithId(value);
	}

	private SWTBotCombo comboBox() {
		return delegate.comboBox();
	}

	private SWTBotCombo comboBox(int index) {
		return delegate.comboBox(index);
	}

	private SWTBotCombo comboBox(String text, int index) {
		return delegate.comboBox(text, index);
	}

	private SWTBotCombo comboBox(String text) {
		return delegate.comboBox(text);
	}

	private SWTBotCombo comboBoxInGroup(String inGroup, int index) {
		return delegate.comboBoxInGroup(inGroup, index);
	}

	private SWTBotCombo comboBoxInGroup(String text, String inGroup, int index) {
		return delegate.comboBoxInGroup(text, inGroup, index);
	}

	private SWTBotCombo comboBoxInGroup(String text, String inGroup) {
		return delegate.comboBoxInGroup(text, inGroup);
	}

	private SWTBotCombo comboBoxInGroup(String inGroup) {
		return delegate.comboBoxInGroup(inGroup);
	}

	private SWTBotCombo comboBoxWithId(String value, int index) {
		return delegate.comboBoxWithId(value, index);
	}

	private SWTBotCombo comboBoxWithId(String key, String value, int index) {
		return delegate.comboBoxWithId(key, value, index);
	}

	private SWTBotCombo comboBoxWithId(String key, String value) {
		return delegate.comboBoxWithId(key, value);
	}

	private SWTBotCombo comboBoxWithId(String value) {
		return delegate.comboBoxWithId(value);
	}

	private SWTBotCombo comboBoxWithLabel(String label, int index) {
		return delegate.comboBoxWithLabel(label, index);
	}

	private SWTBotCombo comboBoxWithLabel(String label) {
		return delegate.comboBoxWithLabel(label);
	}

	private SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.comboBoxWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup) {
		return delegate.comboBoxWithLabelInGroup(label, inGroup);
	}

	private SWTBotCTabItem cTabItem() {
		return delegate.cTabItem();
	}

	private SWTBotCTabItem cTabItem(int index) {
		return delegate.cTabItem(index);
	}

	private SWTBotCTabItem cTabItem(String mnemonicText, int index) {
		return delegate.cTabItem(mnemonicText, index);
	}

	private SWTBotCTabItem cTabItem(String mnemonicText) {
		return delegate.cTabItem(mnemonicText);
	}

	private SWTBotCTabItem cTabItemInGroup(String inGroup, int index) {
		return delegate.cTabItemInGroup(inGroup, index);
	}

	private SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.cTabItemInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup) {
		return delegate.cTabItemInGroup(mnemonicText, inGroup);
	}

	private SWTBotCTabItem cTabItemInGroup(String inGroup) {
		return delegate.cTabItemInGroup(inGroup);
	}

	private SWTBotCTabItem cTabItemWithId(String value, int index) {
		return delegate.cTabItemWithId(value, index);
	}

	private SWTBotCTabItem cTabItemWithId(String key, String value, int index) {
		return delegate.cTabItemWithId(key, value, index);
	}

	private SWTBotCTabItem cTabItemWithId(String key, String value) {
		return delegate.cTabItemWithId(key, value);
	}

	private SWTBotCTabItem cTabItemWithId(String value) {
		return delegate.cTabItemWithId(value);
	}

	private SWTBotDateTime dateTime() {
		return delegate.dateTime();
	}

	private SWTBotDateTime dateTime(int index) {
		return delegate.dateTime(index);
	}

	private SWTBotDateTime dateTimeInGroup(String inGroup, int index) {
		return delegate.dateTimeInGroup(inGroup, index);
	}

	private SWTBotDateTime dateTimeInGroup(String inGroup) {
		return delegate.dateTimeInGroup(inGroup);
	}

	private SWTBotDateTime dateTimeWithId(String value, int index) {
		return delegate.dateTimeWithId(value, index);
	}

	private SWTBotDateTime dateTimeWithId(String key, String value, int index) {
		return delegate.dateTimeWithId(key, value, index);
	}

	private SWTBotDateTime dateTimeWithId(String key, String value) {
		return delegate.dateTimeWithId(key, value);
	}

	private SWTBotDateTime dateTimeWithId(String value) {
		return delegate.dateTimeWithId(value);
	}

	private SWTBotDateTime dateTimeWithLabel(String label, int index) {
		return delegate.dateTimeWithLabel(label, index);
	}

	private SWTBotDateTime dateTimeWithLabel(String label) {
		return delegate.dateTimeWithLabel(label);
	}

	private SWTBotDateTime dateTimeWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.dateTimeWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotDateTime dateTimeWithLabelInGroup(String label, String inGroup) {
		return delegate.dateTimeWithLabelInGroup(label, inGroup);
	}

	private SWTBotEditor editor(Matcher<IEditorReference> matcher) {
		return delegate.editor(matcher);
	}

	private SWTBotEditor editorById(String id) {
		return delegate.editorById(id);
	}

	private SWTBotEditor editorByTitle(String fileName) {
		return delegate.editorByTitle(fileName);
	}

	private List<? extends SWTBotEditor> editors() {
		return delegate.editors();
	}

	private List<SWTBotEditor> editors(Matcher<?> matcher) {
		return delegate.editors(matcher);
	}

	private Display getDisplay() {
		return delegate.getDisplay();
	}

	private Finder getFinder() {
		return delegate.getFinder();
	}

	private Control getFocusedWidget() {
		return delegate.getFocusedWidget();
	}

	private SWTBotLabel label() {
		return delegate.label();
	}

	private SWTBotLabel label(int index) {
		return delegate.label(index);
	}

	private SWTBotLabel label(String mnemonicText, int index) {
		return delegate.label(mnemonicText, index);
	}

	private SWTBotLabel label(String mnemonicText) {
		return delegate.label(mnemonicText);
	}

	private SWTBotLabel labelInGroup(String inGroup, int index) {
		return delegate.labelInGroup(inGroup, index);
	}

	private SWTBotLabel labelInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.labelInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotLabel labelInGroup(String mnemonicText, String inGroup) {
		return delegate.labelInGroup(mnemonicText, inGroup);
	}

	private SWTBotLabel labelInGroup(String inGroup) {
		return delegate.labelInGroup(inGroup);
	}

	private SWTBotLabel labelWithId(String value, int index) {
		return delegate.labelWithId(value, index);
	}

	private SWTBotLabel labelWithId(String key, String value, int index) {
		return delegate.labelWithId(key, value, index);
	}

	private SWTBotLabel labelWithId(String key, String value) {
		return delegate.labelWithId(key, value);
	}

	private SWTBotLabel labelWithId(String value) {
		return delegate.labelWithId(value);
	}

	private SWTBotLink link() {
		return delegate.link();
	}

	private SWTBotLink link(int index) {
		return delegate.link(index);
	}

	private SWTBotLink link(String mnemonicText, int index) {
		return delegate.link(mnemonicText, index);
	}

	private SWTBotLink link(String mnemonicText) {
		return delegate.link(mnemonicText);
	}

	private SWTBotLink linkInGroup(String inGroup, int index) {
		return delegate.linkInGroup(inGroup, index);
	}

	private SWTBotLink linkInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.linkInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotLink linkInGroup(String mnemonicText, String inGroup) {
		return delegate.linkInGroup(mnemonicText, inGroup);
	}

	private SWTBotLink linkInGroup(String inGroup) {
		return delegate.linkInGroup(inGroup);
	}

	private SWTBotLink linkWithId(String value, int index) {
		return delegate.linkWithId(value, index);
	}

	private SWTBotLink linkWithId(String key, String value, int index) {
		return delegate.linkWithId(key, value, index);
	}

	private SWTBotLink linkWithId(String key, String value) {
		return delegate.linkWithId(key, value);
	}

	private SWTBotLink linkWithId(String value) {
		return delegate.linkWithId(value);
	}

	private SWTBotList list() {
		return delegate.list();
	}

	private SWTBotList list(int index) {
		return delegate.list(index);
	}

	private SWTBotList listInGroup(String inGroup, int index) {
		return delegate.listInGroup(inGroup, index);
	}

	private SWTBotList listInGroup(String inGroup) {
		return delegate.listInGroup(inGroup);
	}

	private SWTBotList listWithId(String value, int index) {
		return delegate.listWithId(value, index);
	}

	private SWTBotList listWithId(String key, String value, int index) {
		return delegate.listWithId(key, value, index);
	}

	private SWTBotList listWithId(String key, String value) {
		return delegate.listWithId(key, value);
	}

	private SWTBotList listWithId(String value) {
		return delegate.listWithId(value);
	}

	private SWTBotList listWithLabel(String label, int index) {
		return delegate.listWithLabel(label, index);
	}

	private SWTBotList listWithLabel(String label) {
		return delegate.listWithLabel(label);
	}

	private SWTBotList listWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.listWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotList listWithLabelInGroup(String label, String inGroup) {
		return delegate.listWithLabelInGroup(label, inGroup);
	}

	private SWTBotMenu menu(String text, int index) {
		return delegate.menu(text, index);
	}

	private SWTBotMenu menu(String text) {
		return delegate.menu(text);
	}

	private SWTBotMenu menu(SWTBotShell shell, Matcher<MenuItem> matcher,
			int index) {
		return delegate.menu(shell, matcher, index);
	}

	private SWTBotMenu menuWithId(String value, int index) {
		return delegate.menuWithId(value, index);
	}

	private SWTBotMenu menuWithId(String key, String value, int index) {
		return delegate.menuWithId(key, value, index);
	}

	private SWTBotMenu menuWithId(String key, String value) {
		return delegate.menuWithId(key, value);
	}

	private SWTBotMenu menuWithId(String value) {
		return delegate.menuWithId(value);
	}

	private <T> T performWithTimeout(Result<T> runnable, long timeout) {
		return delegate.performWithTimeout(runnable, timeout);
	}

	private void performWithTimeout(VoidResult runnable, long timeout) {
		delegate.performWithTimeout(runnable, timeout);
	}

	private SWTBotPerspective perspective(Matcher<?> matcher) {
		return delegate.perspective(matcher);
	}

	private SWTBotPerspective perspectiveById(String id) {
		return delegate.perspectiveById(id);
	}

	private SWTBotPerspective perspectiveByLabel(String label) {
		return delegate.perspectiveByLabel(label);
	}

	private List<SWTBotPerspective> perspectives() {
		return delegate.perspectives();
	}

	private List<SWTBotPerspective> perspectives(Matcher<?> matcher) {
		return delegate.perspectives(matcher);
	}

	private SWTBotRadio radio() {
		return delegate.radio();
	}

	private SWTBotRadio radio(int index) {
		return delegate.radio(index);
	}

	private SWTBotRadio radio(String mnemonicText, int index) {
		return delegate.radio(mnemonicText, index);
	}

	private SWTBotRadio radio(String mnemonicText) {
		return delegate.radio(mnemonicText);
	}

	private SWTBotRadio radioInGroup(String inGroup, int index) {
		return delegate.radioInGroup(inGroup, index);
	}

	private SWTBotRadio radioInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.radioInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotRadio radioInGroup(String mnemonicText, String inGroup) {
		return delegate.radioInGroup(mnemonicText, inGroup);
	}

	private SWTBotRadio radioInGroup(String inGroup) {
		return delegate.radioInGroup(inGroup);
	}

	private SWTBotRadio radioWithId(String value, int index) {
		return delegate.radioWithId(value, index);
	}

	private SWTBotRadio radioWithId(String key, String value, int index) {
		return delegate.radioWithId(key, value, index);
	}

	private SWTBotRadio radioWithId(String key, String value) {
		return delegate.radioWithId(key, value);
	}

	private SWTBotRadio radioWithId(String value) {
		return delegate.radioWithId(value);
	}

	private SWTBotRadio radioWithLabel(String label, int index) {
		return delegate.radioWithLabel(label, index);
	}

	private SWTBotRadio radioWithLabel(String label) {
		return delegate.radioWithLabel(label);
	}

	private SWTBotRadio radioWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.radioWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotRadio radioWithLabelInGroup(String label, String inGroup) {
		return delegate.radioWithLabelInGroup(label, inGroup);
	}

	private SWTBotRadio radioWithTooltip(String tooltip, int index) {
		return delegate.radioWithTooltip(tooltip, index);
	}

	private SWTBotRadio radioWithTooltip(String tooltip) {
		return delegate.radioWithTooltip(tooltip);
	}

	private SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup,
			int index) {
		return delegate.radioWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.radioWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotShell shell(String text, int index) {
		return delegate.shell(text, index);
	}

	private SWTBotShell shell(String text, Shell parent, int index) {
		return delegate.shell(text, parent, index);
	}

	private SWTBotShell shell(String text, Shell parent) {
		return delegate.shell(text, parent);
	}

	private SWTBotShell shell(String text) {
		return delegate.shell(text);
	}

	private SWTBotShell[] shells() {
		return delegate.shells();
	}

	private List<Shell> shells(String text, Shell parent) {
		return delegate.shells(text, parent);
	}

	private List<Shell> shells(String text) {
		return delegate.shells(text);
	}

	private SWTBotShell shellWithId(String value, int index) {
		return delegate.shellWithId(value, index);
	}

	private SWTBotShell shellWithId(String key, String value, int index) {
		return delegate.shellWithId(key, value, index);
	}

	private SWTBotShell shellWithId(String key, String value) {
		return delegate.shellWithId(key, value);
	}

	private SWTBotShell shellWithId(String value) {
		return delegate.shellWithId(value);
	}

	private void sleep(long millis) {
		delegate.sleep(millis);
	}

	private SWTBotSlider slider() {
		return delegate.slider();
	}

	private SWTBotSlider slider(int index) {
		return delegate.slider(index);
	}

	private SWTBotSlider slider(String text, int index) {
		return delegate.slider(text, index);
	}

	private SWTBotSlider slider(String text) {
		return delegate.slider(text);
	}

	private SWTBotSlider sliderInGroup(String inGroup, int index) {
		return delegate.sliderInGroup(inGroup, index);
	}

	private SWTBotSlider sliderInGroup(String text, String inGroup, int index) {
		return delegate.sliderInGroup(text, inGroup, index);
	}

	private SWTBotSlider sliderInGroup(String text, String inGroup) {
		return delegate.sliderInGroup(text, inGroup);
	}

	private SWTBotSlider sliderInGroup(String inGroup) {
		return delegate.sliderInGroup(inGroup);
	}

	private SWTBotSlider sliderWithId(String value, int index) {
		return delegate.sliderWithId(value, index);
	}

	private SWTBotSlider sliderWithId(String key, String value, int index) {
		return delegate.sliderWithId(key, value, index);
	}

	private SWTBotSlider sliderWithId(String key, String value) {
		return delegate.sliderWithId(key, value);
	}

	private SWTBotSlider sliderWithId(String value) {
		return delegate.sliderWithId(value);
	}

	private SWTBotSlider sliderWithLabel(String label, int index) {
		return delegate.sliderWithLabel(label, index);
	}

	private SWTBotSlider sliderWithLabel(String label) {
		return delegate.sliderWithLabel(label);
	}

	private SWTBotSlider sliderWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.sliderWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotSlider sliderWithLabelInGroup(String label, String inGroup) {
		return delegate.sliderWithLabelInGroup(label, inGroup);
	}

	private SWTBotSlider sliderWithTooltip(String tooltip, int index) {
		return delegate.sliderWithTooltip(tooltip, index);
	}

	private SWTBotSlider sliderWithTooltip(String tooltip) {
		return delegate.sliderWithTooltip(tooltip);
	}

	private SWTBotSlider sliderWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.sliderWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotSlider sliderWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.sliderWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotSpinner spinner() {
		return delegate.spinner();
	}

	private SWTBotSpinner spinner(int index) {
		return delegate.spinner(index);
	}

	private SWTBotSpinner spinner(String text, int index) {
		return delegate.spinner(text, index);
	}

	private SWTBotSpinner spinner(String text) {
		return delegate.spinner(text);
	}

	private SWTBotSpinner spinnerInGroup(String inGroup, int index) {
		return delegate.spinnerInGroup(inGroup, index);
	}

	private SWTBotSpinner spinnerInGroup(String text, String inGroup, int index) {
		return delegate.spinnerInGroup(text, inGroup, index);
	}

	private SWTBotSpinner spinnerInGroup(String text, String inGroup) {
		return delegate.spinnerInGroup(text, inGroup);
	}

	private SWTBotSpinner spinnerInGroup(String inGroup) {
		return delegate.spinnerInGroup(inGroup);
	}

	private SWTBotSpinner spinnerWithId(String value, int index) {
		return delegate.spinnerWithId(value, index);
	}

	private SWTBotSpinner spinnerWithId(String key, String value, int index) {
		return delegate.spinnerWithId(key, value, index);
	}

	private SWTBotSpinner spinnerWithId(String key, String value) {
		return delegate.spinnerWithId(key, value);
	}

	private SWTBotSpinner spinnerWithId(String value) {
		return delegate.spinnerWithId(value);
	}

	private SWTBotSpinner spinnerWithLabel(String label, int index) {
		return delegate.spinnerWithLabel(label, index);
	}

	private SWTBotSpinner spinnerWithLabel(String label) {
		return delegate.spinnerWithLabel(label);
	}

	private SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.spinnerWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup) {
		return delegate.spinnerWithLabelInGroup(label, inGroup);
	}

	private SWTBotSpinner spinnerWithTooltip(String tooltip, int index) {
		return delegate.spinnerWithTooltip(tooltip, index);
	}

	private SWTBotSpinner spinnerWithTooltip(String tooltip) {
		return delegate.spinnerWithTooltip(tooltip);
	}

	private SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.spinnerWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.spinnerWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotStyledText styledText() {
		return delegate.styledText();
	}

	private SWTBotStyledText styledText(int index) {
		return delegate.styledText(index);
	}

	private SWTBotStyledText styledText(String text, int index) {
		return delegate.styledText(text, index);
	}

	private SWTBotStyledText styledText(String text) {
		return delegate.styledText(text);
	}

	private SWTBotStyledText styledTextInGroup(String inGroup, int index) {
		return delegate.styledTextInGroup(inGroup, index);
	}

	private SWTBotStyledText styledTextInGroup(String text, String inGroup,
			int index) {
		return delegate.styledTextInGroup(text, inGroup, index);
	}

	private SWTBotStyledText styledTextInGroup(String text, String inGroup) {
		return delegate.styledTextInGroup(text, inGroup);
	}

	private SWTBotStyledText styledTextInGroup(String inGroup) {
		return delegate.styledTextInGroup(inGroup);
	}

	private SWTBotStyledText styledTextWithId(String value, int index) {
		return delegate.styledTextWithId(value, index);
	}

	private SWTBotStyledText styledTextWithId(String key, String value,
			int index) {
		return delegate.styledTextWithId(key, value, index);
	}

	private SWTBotStyledText styledTextWithId(String key, String value) {
		return delegate.styledTextWithId(key, value);
	}

	private SWTBotStyledText styledTextWithId(String value) {
		return delegate.styledTextWithId(value);
	}

	private SWTBotStyledText styledTextWithLabel(String label, int index) {
		return delegate.styledTextWithLabel(label, index);
	}

	private SWTBotStyledText styledTextWithLabel(String label) {
		return delegate.styledTextWithLabel(label);
	}

	private SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.styledTextWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup) {
		return delegate.styledTextWithLabelInGroup(label, inGroup);
	}

	private SWTBotTabItem tabItem() {
		return delegate.tabItem();
	}

	private SWTBotTabItem tabItem(int index) {
		return delegate.tabItem(index);
	}

	private SWTBotTabItem tabItem(String mnemonicText, int index) {
		return delegate.tabItem(mnemonicText, index);
	}

	private SWTBotTabItem tabItem(String mnemonicText) {
		return delegate.tabItem(mnemonicText);
	}

	private SWTBotTabItem tabItemInGroup(String inGroup, int index) {
		return delegate.tabItemInGroup(inGroup, index);
	}

	private SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup,
			int index) {
		return delegate.tabItemInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup) {
		return delegate.tabItemInGroup(mnemonicText, inGroup);
	}

	private SWTBotTabItem tabItemInGroup(String inGroup) {
		return delegate.tabItemInGroup(inGroup);
	}

	private SWTBotTabItem tabItemWithId(String value, int index) {
		return delegate.tabItemWithId(value, index);
	}

	private SWTBotTabItem tabItemWithId(String key, String value, int index) {
		return delegate.tabItemWithId(key, value, index);
	}

	private SWTBotTabItem tabItemWithId(String key, String value) {
		return delegate.tabItemWithId(key, value);
	}

	private SWTBotTabItem tabItemWithId(String value) {
		return delegate.tabItemWithId(value);
	}

	private SWTBotTable table() {
		return delegate.table();
	}

	private SWTBotTable table(int index) {
		return delegate.table(index);
	}

	private SWTBotTable tableInGroup(String inGroup, int index) {
		return delegate.tableInGroup(inGroup, index);
	}

	private SWTBotTable tableInGroup(String inGroup) {
		return delegate.tableInGroup(inGroup);
	}

	private SWTBotTable tableWithId(String value, int index) {
		return delegate.tableWithId(value, index);
	}

	private SWTBotTable tableWithId(String key, String value, int index) {
		return delegate.tableWithId(key, value, index);
	}

	private SWTBotTable tableWithId(String key, String value) {
		return delegate.tableWithId(key, value);
	}

	private SWTBotTable tableWithId(String value) {
		return delegate.tableWithId(value);
	}

	private SWTBotTable tableWithLabel(String label, int index) {
		return delegate.tableWithLabel(label, index);
	}

	private SWTBotTable tableWithLabel(String label) {
		return delegate.tableWithLabel(label);
	}

	private SWTBotTable tableWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.tableWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotTable tableWithLabelInGroup(String label, String inGroup) {
		return delegate.tableWithLabelInGroup(label, inGroup);
	}

	private SWTBotText text() {
		return delegate.text();
	}

	private SWTBotText text(int index) {
		return delegate.text(index);
	}

	private SWTBotText text(String text, int index) {
		return delegate.text(text, index);
	}

	private SWTBotText text(String text) {
		return delegate.text(text);
	}

	private SWTBotText textInGroup(String inGroup, int index) {
		return delegate.textInGroup(inGroup, index);
	}

	private SWTBotText textInGroup(String text, String inGroup, int index) {
		return delegate.textInGroup(text, inGroup, index);
	}

	private SWTBotText textInGroup(String text, String inGroup) {
		return delegate.textInGroup(text, inGroup);
	}

	private SWTBotText textInGroup(String inGroup) {
		return delegate.textInGroup(inGroup);
	}

	private SWTBotText textWithId(String value, int index) {
		return delegate.textWithId(value, index);
	}

	private SWTBotText textWithId(String key, String value, int index) {
		return delegate.textWithId(key, value, index);
	}

	private SWTBotText textWithId(String key, String value) {
		return delegate.textWithId(key, value);
	}

	private SWTBotText textWithId(String value) {
		return delegate.textWithId(value);
	}

	private SWTBotText textWithLabel(String label, int index) {
		return delegate.textWithLabel(label, index);
	}

	private SWTBotText textWithLabel(String label) {
		return delegate.textWithLabel(label);
	}

	private SWTBotText textWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.textWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotText textWithLabelInGroup(String label, String inGroup) {
		return delegate.textWithLabelInGroup(label, inGroup);
	}

	private SWTBotText textWithTooltip(String tooltip, int index) {
		return delegate.textWithTooltip(tooltip, index);
	}

	private SWTBotText textWithTooltip(String tooltip) {
		return delegate.textWithTooltip(tooltip);
	}

	private SWTBotText textWithTooltipInGroup(String tooltip, String inGroup,
			int index) {
		return delegate.textWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotText textWithTooltipInGroup(String tooltip, String inGroup) {
		return delegate.textWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotToggleButton toggleButton() {
		return delegate.toggleButton();
	}

	private SWTBotToggleButton toggleButton(int index) {
		return delegate.toggleButton(index);
	}

	private SWTBotToggleButton toggleButton(String mnemonicText, int index) {
		return delegate.toggleButton(mnemonicText, index);
	}

	private SWTBotToggleButton toggleButton(String mnemonicText) {
		return delegate.toggleButton(mnemonicText);
	}

	private SWTBotToggleButton toggleButtonInGroup(String inGroup, int index) {
		return delegate.toggleButtonInGroup(inGroup, index);
	}

	private SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup, int index) {
		return delegate.toggleButtonInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup) {
		return delegate.toggleButtonInGroup(mnemonicText, inGroup);
	}

	private SWTBotToggleButton toggleButtonInGroup(String inGroup) {
		return delegate.toggleButtonInGroup(inGroup);
	}

	private SWTBotToggleButton toggleButtonWithId(String value, int index) {
		return delegate.toggleButtonWithId(value, index);
	}

	private SWTBotToggleButton toggleButtonWithId(String key, String value,
			int index) {
		return delegate.toggleButtonWithId(key, value, index);
	}

	private SWTBotToggleButton toggleButtonWithId(String key, String value) {
		return delegate.toggleButtonWithId(key, value);
	}

	private SWTBotToggleButton toggleButtonWithId(String value) {
		return delegate.toggleButtonWithId(value);
	}

	private SWTBotToggleButton toggleButtonWithLabel(String label, int index) {
		return delegate.toggleButtonWithLabel(label, index);
	}

	private SWTBotToggleButton toggleButtonWithLabel(String label) {
		return delegate.toggleButtonWithLabel(label);
	}

	private SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup, int index) {
		return delegate.toggleButtonWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup) {
		return delegate.toggleButtonWithLabelInGroup(label, inGroup);
	}

	private SWTBotToggleButton toggleButtonWithTooltip(String tooltip, int index) {
		return delegate.toggleButtonWithTooltip(tooltip, index);
	}

	private SWTBotToggleButton toggleButtonWithTooltip(String tooltip) {
		return delegate.toggleButtonWithTooltip(tooltip);
	}

	private SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate.toggleButtonWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.toggleButtonWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotToolbarButton toolbarButton() {
		return delegate.toolbarButton();
	}

	private SWTBotToolbarButton toolbarButton(int index) {
		return delegate.toolbarButton(index);
	}

	private SWTBotToolbarButton toolbarButton(String mnemonicText, int index) {
		return delegate.toolbarButton(mnemonicText, index);
	}

	private SWTBotToolbarButton toolbarButton(String mnemonicText) {
		return delegate.toolbarButton(mnemonicText);
	}

	private SWTBotToolbarButton toolbarButtonInGroup(String inGroup, int index) {
		return delegate.toolbarButtonInGroup(inGroup, index);
	}

	private SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup, int index) {
		return delegate.toolbarButtonInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup) {
		return delegate.toolbarButtonInGroup(mnemonicText, inGroup);
	}

	private SWTBotToolbarButton toolbarButtonInGroup(String inGroup) {
		return delegate.toolbarButtonInGroup(inGroup);
	}

	private SWTBotToolbarButton toolbarButtonWithId(String value, int index) {
		return delegate.toolbarButtonWithId(value, index);
	}

	private SWTBotToolbarButton toolbarButtonWithId(String key, String value,
			int index) {
		return delegate.toolbarButtonWithId(key, value, index);
	}

	private SWTBotToolbarButton toolbarButtonWithId(String key, String value) {
		return delegate.toolbarButtonWithId(key, value);
	}

	private SWTBotToolbarButton toolbarButtonWithId(String value) {
		return delegate.toolbarButtonWithId(value);
	}

	private SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip,
			int index) {
		return delegate.toolbarButtonWithTooltip(tooltip, index);
	}

	private SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip) {
		return delegate.toolbarButtonWithTooltip(tooltip);
	}

	private SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index) {
		return delegate
				.toolbarButtonWithTooltipInGroup(tooltip, inGroup, index);
	}

	private SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup) {
		return delegate.toolbarButtonWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButton() {
		return delegate.toolbarDropDownButton();
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButton(int index) {
		return delegate.toolbarDropDownButton(index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButton(
			String mnemonicText, int index) {
		return delegate.toolbarDropDownButton(mnemonicText, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButton(
			String mnemonicText) {
		return delegate.toolbarDropDownButton(mnemonicText);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup, int index) {
		return delegate.toolbarDropDownButtonInGroup(inGroup, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate.toolbarDropDownButtonInGroup(mnemonicText, inGroup,
				index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarDropDownButtonInGroup(mnemonicText, inGroup);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup) {
		return delegate.toolbarDropDownButtonInGroup(inGroup);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(
			String value, int index) {
		return delegate.toolbarDropDownButtonWithId(value, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarDropDownButtonWithId(key, value, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value) {
		return delegate.toolbarDropDownButtonWithId(key, value);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String value) {
		return delegate.toolbarDropDownButtonWithId(value);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarDropDownButtonWithTooltip(tooltip, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip) {
		return delegate.toolbarDropDownButtonWithTooltip(tooltip);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarDropDownButtonWithTooltipInGroup(tooltip,
				inGroup, index);
	}

	private SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarDropDownButtonWithTooltipInGroup(tooltip,
				inGroup);
	}

	private SWTBotToolbarRadioButton toolbarRadioButton() {
		return delegate.toolbarRadioButton();
	}

	private SWTBotToolbarRadioButton toolbarRadioButton(int index) {
		return delegate.toolbarRadioButton(index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText,
			int index) {
		return delegate.toolbarRadioButton(mnemonicText, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText) {
		return delegate.toolbarRadioButton(mnemonicText);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup,
			int index) {
		return delegate.toolbarRadioButtonInGroup(inGroup, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate.toolbarRadioButtonInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarRadioButtonInGroup(mnemonicText, inGroup);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup) {
		return delegate.toolbarRadioButtonInGroup(inGroup);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value,
			int index) {
		return delegate.toolbarRadioButtonWithId(value, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarRadioButtonWithId(key, value, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value) {
		return delegate.toolbarRadioButtonWithId(key, value);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value) {
		return delegate.toolbarRadioButtonWithId(value);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarRadioButtonWithTooltip(tooltip, index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(
			String tooltip) {
		return delegate.toolbarRadioButtonWithTooltip(tooltip);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarRadioButtonWithTooltipInGroup(tooltip, inGroup,
				index);
	}

	private SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarRadioButtonWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotToolbarToggleButton toolbarToggleButton() {
		return delegate.toolbarToggleButton();
	}

	private SWTBotToolbarToggleButton toolbarToggleButton(int index) {
		return delegate.toolbarToggleButton(index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText,
			int index) {
		return delegate.toolbarToggleButton(mnemonicText, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText) {
		return delegate.toolbarToggleButton(mnemonicText);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String inGroup, int index) {
		return delegate.toolbarToggleButtonInGroup(inGroup, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup, int index) {
		return delegate
				.toolbarToggleButtonInGroup(mnemonicText, inGroup, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup) {
		return delegate.toolbarToggleButtonInGroup(mnemonicText, inGroup);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup) {
		return delegate.toolbarToggleButtonInGroup(inGroup);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value,
			int index) {
		return delegate.toolbarToggleButtonWithId(value, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value, int index) {
		return delegate.toolbarToggleButtonWithId(key, value, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value) {
		return delegate.toolbarToggleButtonWithId(key, value);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value) {
		return delegate.toolbarToggleButtonWithId(value);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip, int index) {
		return delegate.toolbarToggleButtonWithTooltip(tooltip, index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip) {
		return delegate.toolbarToggleButtonWithTooltip(tooltip);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index) {
		return delegate.toolbarToggleButtonWithTooltipInGroup(tooltip, inGroup,
				index);
	}

	private SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup) {
		return delegate.toolbarToggleButtonWithTooltipInGroup(tooltip, inGroup);
	}

	private SWTBotTrayItem trayItem() {
		return delegate.trayItem();
	}

	private SWTBotTrayItem trayItem(int index) {
		return delegate.trayItem(index);
	}

	private List<SWTBotTrayItem> trayItems() {
		return delegate.trayItems();
	}

	private List<SWTBotTrayItem> trayItems(Matcher<?> matcher) {
		return delegate.trayItems(matcher);
	}

	private SWTBotTrayItem trayItemWithTooltip(String tooltip, int index) {
		return delegate.trayItemWithTooltip(tooltip, index);
	}

	private SWTBotTrayItem trayItemWithTooltip(String tooltip) {
		return delegate.trayItemWithTooltip(tooltip);
	}

	private SWTBotTree tree() {
		return delegate.tree();
	}

	private SWTBotTree tree(int index) {
		return delegate.tree(index);
	}

	private SWTBotTree treeInGroup(String inGroup, int index) {
		return delegate.treeInGroup(inGroup, index);
	}

	private SWTBotTree treeInGroup(String inGroup) {
		return delegate.treeInGroup(inGroup);
	}

	private SWTBotTree treeWithId(String value, int index) {
		return delegate.treeWithId(value, index);
	}

	private SWTBotTree treeWithId(String key, String value, int index) {
		return delegate.treeWithId(key, value, index);
	}

	private SWTBotTree treeWithId(String key, String value) {
		return delegate.treeWithId(key, value);
	}

	private SWTBotTree treeWithId(String value) {
		return delegate.treeWithId(value);
	}

	private SWTBotTree treeWithLabel(String label, int index) {
		return delegate.treeWithLabel(label, index);
	}

	private SWTBotTree treeWithLabel(String label) {
		return delegate.treeWithLabel(label);
	}

	private SWTBotTree treeWithLabelInGroup(String label, String inGroup,
			int index) {
		return delegate.treeWithLabelInGroup(label, inGroup, index);
	}

	private SWTBotTree treeWithLabelInGroup(String label, String inGroup) {
		return delegate.treeWithLabelInGroup(label, inGroup);
	}

	private SWTBotView view(Matcher<IViewReference> matcher) {
		return delegate.view(matcher);
	}

	private SWTBotView viewById(String id) {
		return delegate.viewById(id);
	}

	private SWTBotView viewByTitle(String title) {
		return delegate.viewByTitle(title);
	}

	private List<SWTBotView> views() {
		return delegate.views();
	}

	private List<SWTBotView> views(Matcher<?> matcher) {
		return delegate.views(matcher);
	}

	private void waitUntil(ICondition condition, long timeout, long interval)
			throws TimeoutException {
		delegate.waitUntil(condition, timeout, interval);
	}

	private void waitUntil(ICondition condition, long timeout)
			throws TimeoutException {
		delegate.waitUntil(condition, timeout);
	}

	private void waitUntil(ICondition condition) throws TimeoutException {
		delegate.waitUntil(condition);
	}

	private void waitUntilWidgetAppears(ICondition waitForWidget) {
		delegate.waitUntilWidgetAppears(waitForWidget);
	}

	private void waitWhile(ICondition condition, long timeout, long interval)
			throws TimeoutException {
		delegate.waitWhile(condition, timeout, interval);
	}

	private void waitWhile(ICondition condition, long timeout)
			throws TimeoutException {
		delegate.waitWhile(condition, timeout);
	}

	private void waitWhile(ICondition condition) throws TimeoutException {
		delegate.waitWhile(condition);
	}

	private <T extends Widget> T widget(Matcher<T> matcher, int index) {
		return delegate.widget(matcher, index);
	}

	private <T extends Widget> T widget(Matcher<T> matcher,
			Widget parentWidget, int index) {
		return delegate.widget(matcher, parentWidget, index);
	}

	private <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget) {
		return delegate.widget(matcher, parentWidget);
	}

	private <T extends Widget> T widget(Matcher<T> matcher) {
		return delegate.widget(matcher);
	}

	private <T extends Widget> List<? extends T> widgets(Matcher<T> matcher,
			Widget parentWidget) {
		return delegate.widgets(matcher, parentWidget);
	}

	private <T extends Widget> List<? extends T> widgets(Matcher<T> matcher) {
		return delegate.widgets(matcher);
	}

	/** Util **/
	public static class CallableResult<T> {
		public T result;
		public Exception e;
	}

	public static Runnable wrapSafe(final Runnable runnable) {

		return new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch (RuntimeException e) {
					e.printStackTrace();
				} catch (Error e) {
					throw e;
				}
			}
		};
	}

	public static void runSafeSWTSync(final Runnable runnable) {
		Display.getDefault().syncExec(wrapSafe(runnable));
	}

	public static <T> T runSWTSync(final Callable<T> callable) throws Exception {

		final CallableResult<T> result = new CallableResult<T>();

		runSafeSWTSync(new Runnable() {
			public void run() {
				try {
					result.result = callable.call();
				} catch (Exception e) {
					result.e = e;
				}
			}
		});

		if (result.e != null)
			throw result.e;
		return result.result;
	}

	/** RMI exported Methods */

	public void closeViewByTitle(String title) {
		System.err.println("closeViewByTitle");
		delegate.viewByTitle(title).close();
	}

	public void clickButton(String label) {
		System.err.println("clickButton");
		delegate.buttonWithLabel(label).click();
	}

	public void clickMenuByName(String name) throws RemoteException {
		System.err.println("clickMenuByName(String name)");
		delegate.menu(name).click();
	}

	public void clickMenuByName(List<String> names) throws RemoteException {
		System.err.println("clickMenuByName(List<String> names)");
		SWTBotMenu menu = null;
		for (String name : names) {
			menu = (menu == null) ? delegate.menu(name) : menu(name);
		}
		menu.click();
	}

	public void activateShellByName(String name) throws RemoteException {
		System.err.println("activateShellByName(String name)");
		delegate.shell(name).activate();
	}

	public void botSleep(long millis) throws RemoteException {
		System.err.println("botSleep(long millis)");
		delegate.sleep(millis);
	}

	public void clickButton(int num) throws RemoteException {
		System.err.println("clickButton(int num)");
		delegate.button(num).click();
	}

	public void clickToolbarButtonByTooltipOnViewByTitle(String title,
			String tooltip) throws RemoteException {
		System.err.println("clickToolbarButtonByNameOnViewByTitle("
				+ "String title, String buttonName)");
		delegate.viewByTitle(title).toolbarButton(tooltip);
	}

	public boolean isButtonEnabled(String name) throws RemoteException {
		System.err.println("isButtonEnabled(String name)");
		return delegate.button(name).isEnabled();
	}

	public boolean isButtonEnabled(int num) throws RemoteException {
		System.err.println("isButtonEnabled(int num)");
		return delegate.button(num).isEnabled();
	}

	// staticBot.shell("New Project").activate();
	// staticBot.tree().select("Java Project");
	public void selectTreeByLabel(String label) throws RemoteException {
		System.err.println("selectTreeByLabel(String label)");
		delegate.tree().select(label);
	}

	public void setTextWithLabel(String label, String text)
			throws RemoteException {
		System.err.println("setTextWithLabel(String label, String text)");
		delegate.textWithLabel(label).setText(text);
	}

	public void setTextWithText(String match, String replace)
			throws RemoteException {
		System.err.println("setTextWithLabel(String label, String text)");
		delegate.text(match).setText(replace);
	}

}
