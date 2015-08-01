package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCNoCostSplitBillControllerRemoteHome extends EJBHome
{
    FDCNoCostSplitBillControllerRemote create() throws CreateException, RemoteException;
}