package org.eclipse.swtbot.eclipse.finder.rmi_delegation2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiSWTWorkbenchBot extends Remote {
	public void pushButton(String name) throws RemoteException;;

	public void closeViewByTitle(String title) throws RemoteException;;

}
