package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSplitBillControllerRemoteHome extends EJBHome
{
    FDCSplitBillControllerRemote create() throws CreateException, RemoteException;
}