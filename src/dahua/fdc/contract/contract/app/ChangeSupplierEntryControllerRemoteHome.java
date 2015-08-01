package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeSupplierEntryControllerRemoteHome extends EJBHome
{
    ChangeSupplierEntryControllerRemote create() throws CreateException, RemoteException;
}