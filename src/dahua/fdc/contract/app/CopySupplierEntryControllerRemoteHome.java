package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CopySupplierEntryControllerRemoteHome extends EJBHome
{
    CopySupplierEntryControllerRemote create() throws CreateException, RemoteException;
}