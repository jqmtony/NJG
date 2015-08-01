package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSplitBillEntryControllerRemoteHome extends EJBHome
{
    FDCSplitBillEntryControllerRemote create() throws CreateException, RemoteException;
}