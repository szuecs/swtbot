package org.eclipse.swtbot.eclipse.finder.rmi_delegation2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRmiSWTWorkbenchBot extends Remote {
	public void activateShellByName(String name) throws RemoteException;

	public void botSleep(long millis) throws RemoteException;

	public void clickButton(String label) throws RemoteException;

	// staticBot.button(1).click();
	public void clickButton(int num) throws RemoteException;

	public void clickMenuByName(String name) throws RemoteException;

	public void clickMenuByName(List<String> names) throws RemoteException;

	// staticBot.viewByTitle("Roster").toolbarButton("Connect").click();
	// clickToolbarButtonByNameOnViewByTitle("Roster","Connect");
	public void clickToolbarButtonByTooltipOnViewByTitle(String title,
			String tooltip) throws RemoteException;

	public void closeViewByTitle(String title) throws RemoteException;

	public boolean isButtonEnabled(String name) throws RemoteException;

	// if (staticBot.button(1).isEnabled())
	public boolean isButtonEnabled(int num) throws RemoteException;

	public void selectTreeByLabel(String label) throws RemoteException;

	public void setTextWithLabel(String label, String text)
			throws RemoteException;

	// SWTBotText text = staticBot.text("type filter text");
	// text.setText("Roster");
	public void setTextWithText(String match, String replace)
			throws RemoteException;

	// // add a Java Class
	// staticBot.toolbarDropDownButtonWithTooltip("New Java Class").menuItem(
	// "Class").click();

	// // roster view

	// SWTBotTreeItem treeitem = staticBot.tree(0).getTreeItem("Saros")
	// .select();
	// treeitem.getNode("Roster").select();

}
