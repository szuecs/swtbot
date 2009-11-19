package org.eclipse.swtbot.eclipse.finder.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
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
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.hamcrest.Matcher;

public interface IRmiSWTWorkbenchBot extends Remote {

	public SWTBotEditor activeEditor();

	public SWTBotPerspective activePerspective();

	public SWTBotShell activeShell() throws WidgetNotFoundException;

	public SWTBotView activeView();

	public SWTBotButton button();

	public SWTBotButton button(int index);

	public SWTBotButton button(String mnemonicText, int index);

	public SWTBotButton button(String mnemonicText);

	public SWTBotButton buttonInGroup(String inGroup, int index);

	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotButton buttonInGroup(String mnemonicText, String inGroup);

	public SWTBotButton buttonInGroup(String inGroup);

	public SWTBotButton buttonWithId(String value, int index);

	public SWTBotButton buttonWithId(String key, String value, int index);

	public SWTBotButton buttonWithId(String key, String value);

	public SWTBotButton buttonWithId(String value);

	public SWTBotButton buttonWithLabel(String label, int index);

	public SWTBotButton buttonWithLabel(String label);

	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotButton buttonWithLabelInGroup(String label, String inGroup);

	public SWTBotButton buttonWithTooltip(String tooltip, int index);

	public SWTBotButton buttonWithTooltip(String tooltip);

	public SWTBotButton buttonWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotButton buttonWithTooltipInGroup(String tooltip, String inGroup);

	public boolean captureScreenshot(String fileName);

	public SWTBotCCombo ccomboBox();

	public SWTBotCCombo ccomboBox(int index);

	public SWTBotCCombo ccomboBox(String text, int index);

	public SWTBotCCombo ccomboBox(String text);

	public SWTBotCCombo ccomboBoxInGroup(String inGroup, int index);

	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup, int index);

	public SWTBotCCombo ccomboBoxInGroup(String text, String inGroup);

	public SWTBotCCombo ccomboBoxInGroup(String inGroup);

	public SWTBotCCombo ccomboBoxWithId(String value, int index);

	public SWTBotCCombo ccomboBoxWithId(String key, String value, int index);

	public SWTBotCCombo ccomboBoxWithId(String key, String value);

	public SWTBotCCombo ccomboBoxWithId(String value);

	public SWTBotCCombo ccomboBoxWithLabel(String label, int index);

	public SWTBotCCombo ccomboBoxWithLabel(String label);

	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotCCombo ccomboBoxWithLabelInGroup(String label, String inGroup);

	public SWTBotCheckBox checkBox();

	public SWTBotCheckBox checkBox(int index);

	public SWTBotCheckBox checkBox(String mnemonicText, int index);

	public SWTBotCheckBox checkBox(String mnemonicText);

	public SWTBotCheckBox checkBoxInGroup(String inGroup, int index);

	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotCheckBox checkBoxInGroup(String mnemonicText, String inGroup);

	public SWTBotCheckBox checkBoxInGroup(String inGroup);

	public SWTBotCheckBox checkBoxWithId(String value, int index);

	public SWTBotCheckBox checkBoxWithId(String key, String value, int index);

	public SWTBotCheckBox checkBoxWithId(String key, String value);

	public SWTBotCheckBox checkBoxWithId(String value);

	public SWTBotCheckBox checkBoxWithLabel(String label, int index);

	public SWTBotCheckBox checkBoxWithLabel(String label);

	public SWTBotCheckBox checkBoxWithLabelInGroup(String label,
			String inGroup, int index);

	public SWTBotCheckBox checkBoxWithLabelInGroup(String label, String inGroup);

	public SWTBotCheckBox checkBoxWithTooltip(String tooltip, int index);

	public SWTBotCheckBox checkBoxWithTooltip(String tooltip);

	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotCheckBox checkBoxWithTooltipInGroup(String tooltip,
			String inGroup);

	public SWTBotCLabel clabel();

	public SWTBotCLabel clabel(int index);

	public SWTBotCLabel clabel(String mnemonicText, int index);

	public SWTBotCLabel clabel(String mnemonicText);

	public SWTBotCLabel clabelInGroup(String inGroup, int index);

	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotCLabel clabelInGroup(String mnemonicText, String inGroup);

	public SWTBotCLabel clabelInGroup(String inGroup);

	public SWTBotCLabel clabelWithId(String value, int index);

	public SWTBotCLabel clabelWithId(String key, String value, int index);

	public SWTBotCLabel clabelWithId(String key, String value);

	public SWTBotCLabel clabelWithId(String value);

	public SWTBotCombo comboBox();

	public SWTBotCombo comboBox(int index);

	public SWTBotCombo comboBox(String text, int index);

	public SWTBotCombo comboBox(String text);

	public SWTBotCombo comboBoxInGroup(String inGroup, int index);

	public SWTBotCombo comboBoxInGroup(String text, String inGroup, int index);

	public SWTBotCombo comboBoxInGroup(String text, String inGroup);

	public SWTBotCombo comboBoxInGroup(String inGroup);

	public SWTBotCombo comboBoxWithId(String value, int index);

	public SWTBotCombo comboBoxWithId(String key, String value, int index);

	public SWTBotCombo comboBoxWithId(String key, String value);

	public SWTBotCombo comboBoxWithId(String value);

	public SWTBotCombo comboBoxWithLabel(String label, int index);

	public SWTBotCombo comboBoxWithLabel(String label);

	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotCombo comboBoxWithLabelInGroup(String label, String inGroup);

	public SWTBotCTabItem cTabItem();

	public SWTBotCTabItem cTabItem(int index);

	public SWTBotCTabItem cTabItem(String mnemonicText, int index);

	public SWTBotCTabItem cTabItem(String mnemonicText);

	public SWTBotCTabItem cTabItemInGroup(String inGroup, int index);

	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotCTabItem cTabItemInGroup(String mnemonicText, String inGroup);

	public SWTBotCTabItem cTabItemInGroup(String inGroup);

	public SWTBotCTabItem cTabItemWithId(String value, int index);

	public SWTBotCTabItem cTabItemWithId(String key, String value, int index);

	public SWTBotCTabItem cTabItemWithId(String key, String value);

	public SWTBotCTabItem cTabItemWithId(String value);

	public SWTBotDateTime dateTime();

	public SWTBotDateTime dateTime(int index);

	public SWTBotDateTime dateTimeInGroup(String inGroup, int index);

	public SWTBotDateTime dateTimeInGroup(String inGroup);

	public SWTBotDateTime dateTimeWithId(String value, int index);

	public SWTBotDateTime dateTimeWithId(String key, String value, int index);

	public SWTBotDateTime dateTimeWithId(String key, String value);

	public SWTBotDateTime dateTimeWithId(String value);

	public SWTBotDateTime dateTimeWithLabel(String label, int index);

	public SWTBotDateTime dateTimeWithLabel(String label);

	public SWTBotDateTime dateTimeWithLabelInGroup(String label,
			String inGroup, int index);

	public SWTBotDateTime dateTimeWithLabelInGroup(String label, String inGroup);

	public SWTBotEditor editor(Matcher<IEditorReference> matcher);

	public SWTBotEditor editorById(String id);

	public SWTBotEditor editorByTitle(String fileName);

	public LinkedList<? extends SWTBotEditor> editors();

	public LinkedList<SWTBotEditor> editors(Matcher<?> matcher);

	public Display getDisplay();

	public Finder getFinder();

	public Control getFocusedWidget();

	public SWTBotLabel label();

	public SWTBotLabel label(int index);

	public SWTBotLabel label(String mnemonicText, int index);

	public SWTBotLabel label(String mnemonicText);

	public SWTBotLabel labelInGroup(String inGroup, int index);

	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotLabel labelInGroup(String mnemonicText, String inGroup);

	public SWTBotLabel labelInGroup(String inGroup);

	public SWTBotLabel labelWithId(String value, int index);

	public SWTBotLabel labelWithId(String key, String value, int index);

	public SWTBotLabel labelWithId(String key, String value);

	public SWTBotLabel labelWithId(String value);

	public SWTBotLink link();

	public SWTBotLink link(int index);

	public SWTBotLink link(String mnemonicText, int index);

	public SWTBotLink link(String mnemonicText);

	public SWTBotLink linkInGroup(String inGroup, int index);

	public SWTBotLink linkInGroup(String mnemonicText, String inGroup, int index);

	public SWTBotLink linkInGroup(String mnemonicText, String inGroup);

	public SWTBotLink linkInGroup(String inGroup);

	public SWTBotLink linkWithId(String value, int index);

	public SWTBotLink linkWithId(String key, String value, int index);

	public SWTBotLink linkWithId(String key, String value);

	public SWTBotLink linkWithId(String value);

	public SWTBotList list();

	public SWTBotList list(int index);

	public SWTBotList listInGroup(String inGroup, int index);

	public SWTBotList listInGroup(String inGroup);

	public SWTBotList listWithId(String value, int index);

	public SWTBotList listWithId(String key, String value, int index);

	public SWTBotList listWithId(String key, String value);

	public SWTBotList listWithId(String value);

	public SWTBotList listWithLabel(String label, int index);

	public SWTBotList listWithLabel(String label);

	public SWTBotList listWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotList listWithLabelInGroup(String label, String inGroup);

	public SWTBotMenu menu(String text, int index);

	public SWTBotMenu menu(String text);

	public SWTBotMenu menu(SWTBotShell shell, Matcher<MenuItem> matcher,
			int index);

	public SWTBotMenu menuWithId(String value, int index);

	public SWTBotMenu menuWithId(String key, String value, int index);

	public SWTBotMenu menuWithId(String key, String value);

	public SWTBotMenu menuWithId(String value);

	public <T> T performWithTimeout(Result<T> runnable, long timeout);

	public void performWithTimeout(VoidResult runnable, long timeout);

	public SWTBotPerspective perspective(Matcher<?> matcher);

	public SWTBotPerspective perspectiveById(String id);

	public SWTBotPerspective perspectiveByLabel(String label);

	public List<SWTBotPerspective> perspectives();

	public List<SWTBotPerspective> perspectives(Matcher<?> matcher);

	public SWTBotRadio radio();

	public SWTBotRadio radio(int index);

	public SWTBotRadio radio(String mnemonicText, int index);

	public SWTBotRadio radio(String mnemonicText);

	public SWTBotRadio radioInGroup(String inGroup, int index);

	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotRadio radioInGroup(String mnemonicText, String inGroup);

	public SWTBotRadio radioInGroup(String inGroup);

	public SWTBotRadio radioWithId(String value, int index);

	public SWTBotRadio radioWithId(String key, String value, int index);

	public SWTBotRadio radioWithId(String key, String value);

	public SWTBotRadio radioWithId(String value);

	public SWTBotRadio radioWithLabel(String label, int index);

	public SWTBotRadio radioWithLabel(String label);

	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotRadio radioWithLabelInGroup(String label, String inGroup);

	public SWTBotRadio radioWithTooltip(String tooltip, int index);

	public SWTBotRadio radioWithTooltip(String tooltip);

	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup,
			int index);

	public SWTBotRadio radioWithTooltipInGroup(String tooltip, String inGroup);

	public SWTBotShell shell(String text, int index);

	public SWTBotShell shell(String text, Shell parent, int index);

	public SWTBotShell shell(String text, Shell parent);

	public SWTBotShell shell(String text);

	public SWTBotShell[] shells();

	public List<Shell> shells(String text, Shell parent);

	public List<Shell> shells(String text);

	public SWTBotShell shellWithId(String value, int index);

	public SWTBotShell shellWithId(String key, String value, int index);

	public SWTBotShell shellWithId(String key, String value);

	public SWTBotShell shellWithId(String value);

	public SWTBotSlider slider();

	public SWTBotSlider slider(int index);

	public SWTBotSlider slider(String text, int index);

	public SWTBotSlider slider(String text);

	public SWTBotSlider sliderInGroup(String inGroup, int index);

	public SWTBotSlider sliderInGroup(String text, String inGroup, int index);

	public SWTBotSlider sliderInGroup(String text, String inGroup);

	public SWTBotSlider sliderInGroup(String inGroup);

	public SWTBotSlider sliderWithId(String value, int index);

	public SWTBotSlider sliderWithId(String key, String value, int index);

	public SWTBotSlider sliderWithId(String key, String value);

	public SWTBotSlider sliderWithId(String value);

	public SWTBotSlider sliderWithLabel(String label, int index);

	public SWTBotSlider sliderWithLabel(String label);

	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotSlider sliderWithLabelInGroup(String label, String inGroup);

	public SWTBotSlider sliderWithTooltip(String tooltip, int index);

	public SWTBotSlider sliderWithTooltip(String tooltip);

	public SWTBotSlider sliderWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotSlider sliderWithTooltipInGroup(String tooltip, String inGroup);

	public SWTBotSpinner spinner();

	public SWTBotSpinner spinner(int index);

	public SWTBotSpinner spinner(String text, int index);

	public SWTBotSpinner spinner(String text);

	public SWTBotSpinner spinnerInGroup(String inGroup, int index);

	public SWTBotSpinner spinnerInGroup(String text, String inGroup, int index);

	public SWTBotSpinner spinnerInGroup(String text, String inGroup);

	public SWTBotSpinner spinnerInGroup(String inGroup);

	public SWTBotSpinner spinnerWithId(String value, int index);

	public SWTBotSpinner spinnerWithId(String key, String value, int index);

	public SWTBotSpinner spinnerWithId(String key, String value);

	public SWTBotSpinner spinnerWithId(String value);

	public SWTBotSpinner spinnerWithLabel(String label, int index);

	public SWTBotSpinner spinnerWithLabel(String label);

	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotSpinner spinnerWithLabelInGroup(String label, String inGroup);

	public SWTBotSpinner spinnerWithTooltip(String tooltip, int index);

	public SWTBotSpinner spinnerWithTooltip(String tooltip);

	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotSpinner spinnerWithTooltipInGroup(String tooltip,
			String inGroup);

	public SWTBotStyledText styledText();

	public SWTBotStyledText styledText(int index);

	public SWTBotStyledText styledText(String text, int index);

	public SWTBotStyledText styledText(String text);

	public SWTBotStyledText styledTextInGroup(String inGroup, int index);

	public SWTBotStyledText styledTextInGroup(String text, String inGroup,
			int index);

	public SWTBotStyledText styledTextInGroup(String text, String inGroup);

	public SWTBotStyledText styledTextInGroup(String inGroup);

	public SWTBotStyledText styledTextWithId(String value, int index);

	public SWTBotStyledText styledTextWithId(String key, String value, int index);

	public SWTBotStyledText styledTextWithId(String key, String value);

	public SWTBotStyledText styledTextWithId(String value);

	public SWTBotStyledText styledTextWithLabel(String label, int index);

	public SWTBotStyledText styledTextWithLabel(String label);

	public SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup, int index);

	public SWTBotStyledText styledTextWithLabelInGroup(String label,
			String inGroup);

	public SWTBotTabItem tabItem();

	public SWTBotTabItem tabItem(int index);

	public SWTBotTabItem tabItem(String mnemonicText, int index);

	public SWTBotTabItem tabItem(String mnemonicText);

	public SWTBotTabItem tabItemInGroup(String inGroup, int index);

	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup,
			int index);

	public SWTBotTabItem tabItemInGroup(String mnemonicText, String inGroup);

	public SWTBotTabItem tabItemInGroup(String inGroup);

	public SWTBotTabItem tabItemWithId(String value, int index);

	public SWTBotTabItem tabItemWithId(String key, String value, int index);

	public SWTBotTabItem tabItemWithId(String key, String value);

	public SWTBotTabItem tabItemWithId(String value);

	public SWTBotTable table();

	public SWTBotTable table(int index);

	public SWTBotTable tableInGroup(String inGroup, int index);

	public SWTBotTable tableInGroup(String inGroup);

	public SWTBotTable tableWithId(String value, int index);

	public SWTBotTable tableWithId(String key, String value, int index);

	public SWTBotTable tableWithId(String key, String value);

	public SWTBotTable tableWithId(String value);

	public SWTBotTable tableWithLabel(String label, int index);

	public SWTBotTable tableWithLabel(String label);

	public SWTBotTable tableWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotTable tableWithLabelInGroup(String label, String inGroup);

	public SWTBotText text();

	public SWTBotText text(int index);

	public SWTBotText text(String text, int index);

	public SWTBotText text(String text);

	public SWTBotText textInGroup(String inGroup, int index);

	public SWTBotText textInGroup(String text, String inGroup, int index);

	public SWTBotText textInGroup(String text, String inGroup);

	public SWTBotText textInGroup(String inGroup);

	public SWTBotText textWithId(String value, int index);

	public SWTBotText textWithId(String key, String value, int index);

	public SWTBotText textWithId(String key, String value);

	public SWTBotText textWithId(String value);

	public SWTBotText textWithLabel(String label, int index);

	public SWTBotText textWithLabel(String label);

	public SWTBotText textWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotText textWithLabelInGroup(String label, String inGroup);

	public SWTBotText textWithTooltip(String tooltip, int index);

	public SWTBotText textWithTooltip(String tooltip);

	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup,
			int index);

	public SWTBotText textWithTooltipInGroup(String tooltip, String inGroup);

	public SWTBotToggleButton toggleButton();

	public SWTBotToggleButton toggleButton(int index);

	public SWTBotToggleButton toggleButton(String mnemonicText, int index);

	public SWTBotToggleButton toggleButton(String mnemonicText);

	public SWTBotToggleButton toggleButtonInGroup(String inGroup, int index);

	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup, int index);

	public SWTBotToggleButton toggleButtonInGroup(String mnemonicText,
			String inGroup);

	public SWTBotToggleButton toggleButtonInGroup(String inGroup);

	public SWTBotToggleButton toggleButtonWithId(String value, int index);

	public SWTBotToggleButton toggleButtonWithId(String key, String value,
			int index);

	public SWTBotToggleButton toggleButtonWithId(String key, String value);

	public SWTBotToggleButton toggleButtonWithId(String value);

	public SWTBotToggleButton toggleButtonWithLabel(String label, int index);

	public SWTBotToggleButton toggleButtonWithLabel(String label);

	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup, int index);

	public SWTBotToggleButton toggleButtonWithLabelInGroup(String label,
			String inGroup);

	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip, int index);

	public SWTBotToggleButton toggleButtonWithTooltip(String tooltip);

	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotToggleButton toggleButtonWithTooltipInGroup(String tooltip,
			String inGroup);

	public SWTBotToolbarButton toolbarButton();

	public SWTBotToolbarButton toolbarButton(int index);

	public SWTBotToolbarButton toolbarButton(String mnemonicText, int index);

	public SWTBotToolbarButton toolbarButton(String mnemonicText);

	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup, int index);

	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup, int index);

	public SWTBotToolbarButton toolbarButtonInGroup(String mnemonicText,
			String inGroup);

	public SWTBotToolbarButton toolbarButtonInGroup(String inGroup);

	public SWTBotToolbarButton toolbarButtonWithId(String value, int index);

	public SWTBotToolbarButton toolbarButtonWithId(String key, String value,
			int index);

	public SWTBotToolbarButton toolbarButtonWithId(String key, String value);

	public SWTBotToolbarButton toolbarButtonWithId(String value);

	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip,
			int index);

	public SWTBotToolbarButton toolbarButtonWithTooltip(String tooltip);

	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup, int index);

	public SWTBotToolbarButton toolbarButtonWithTooltipInGroup(String tooltip,
			String inGroup);

	public SWTBotToolbarDropDownButton toolbarDropDownButton();

	public SWTBotToolbarDropDownButton toolbarDropDownButton(int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButton(
			String mnemonicText, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButton(String mnemonicText);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String mnemonicText, String inGroup);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonInGroup(
			String inGroup);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(
			String value, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String key,
			String value);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithId(String value);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltip(
			String tooltip);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index);

	public SWTBotToolbarDropDownButton toolbarDropDownButtonWithTooltipInGroup(
			String tooltip, String inGroup);

	public SWTBotToolbarRadioButton toolbarRadioButton();

	public SWTBotToolbarRadioButton toolbarRadioButton(int index);

	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText,
			int index);

	public SWTBotToolbarRadioButton toolbarRadioButton(String mnemonicText);

	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup,
			int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup, int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(
			String mnemonicText, String inGroup);

	public SWTBotToolbarRadioButton toolbarRadioButtonInGroup(String inGroup);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value,
			int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value, int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String key,
			String value);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithId(String value);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(
			String tooltip, int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltip(String tooltip);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index);

	public SWTBotToolbarRadioButton toolbarRadioButtonWithTooltipInGroup(
			String tooltip, String inGroup);

	public SWTBotToolbarToggleButton toolbarToggleButton();

	public SWTBotToolbarToggleButton toolbarToggleButton(int index);

	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText,
			int index);

	public SWTBotToolbarToggleButton toolbarToggleButton(String mnemonicText);

	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup,
			int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup, int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(
			String mnemonicText, String inGroup);

	public SWTBotToolbarToggleButton toolbarToggleButtonInGroup(String inGroup);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value,
			int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value, int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String key,
			String value);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithId(String value);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip, int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltip(
			String tooltip);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup, int index);

	public SWTBotToolbarToggleButton toolbarToggleButtonWithTooltipInGroup(
			String tooltip, String inGroup);

	public SWTBotTrayItem trayItem();

	public SWTBotTrayItem trayItem(int index);

	public List<SWTBotTrayItem> trayItems();

	public List<SWTBotTrayItem> trayItems(Matcher<?> matcher);

	public SWTBotTrayItem trayItemWithTooltip(String tooltip, int index);

	public SWTBotTrayItem trayItemWithTooltip(String tooltip);

	public SWTBotTree tree();

	public SWTBotTree tree(int index);

	public SWTBotTree treeInGroup(String inGroup, int index);

	public SWTBotTree treeInGroup(String inGroup);

	public SWTBotTree treeWithId(String value, int index);

	public SWTBotTree treeWithId(String key, String value, int index);

	public SWTBotTree treeWithId(String key, String value);

	public SWTBotTree treeWithId(String value);

	public SWTBotTree treeWithLabel(String label, int index);

	public SWTBotTree treeWithLabel(String label);

	public SWTBotTree treeWithLabelInGroup(String label, String inGroup,
			int index);

	public SWTBotTree treeWithLabelInGroup(String label, String inGroup);

	public SWTBotView view(Matcher<IViewReference> matcher);

	public SWTBotView viewById(String id);

	public SWTBotView viewByTitle(String title);

	public List<SWTBotView> views();

	public List<SWTBotView> views(Matcher<?> matcher);

	public void waitUntil(ICondition condition, long timeout, long interval)
			throws RemoteException;

	public void waitUntil(ICondition condition, long timeout)
			throws RemoteException;

	public void waitUntil(ICondition condition) throws RemoteException;

	public void waitUntilWidgetAppears(ICondition waitForWidget);

	public void waitWhile(ICondition condition, long timeout, long interval)
			throws RemoteException;

	public void waitWhile(ICondition condition, long timeout)
			throws RemoteException;

	public void waitWhile(ICondition condition) throws RemoteException;

	public <T extends Widget> T widget(Matcher<T> matcher, int index);

	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget,
			int index);

	public <T extends Widget> T widget(Matcher<T> matcher, Widget parentWidget);

	public <T extends Widget> T widget(Matcher<T> matcher);

	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher,
			Widget parentWidget);

	public <T extends Widget> List<? extends T> widgets(Matcher<T> matcher);

}