package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCNoCostSplitBillEntryControllerRemoteHome extends EJBHome
{
    FDCNoCostSplitBillEntryControllerRemote create() throws CreateException, RemoteException;
}