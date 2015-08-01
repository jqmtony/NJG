package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBCostEntryControllerRemoteHome extends EJBHome
{
    DBCostEntryControllerRemote create() throws CreateException, RemoteException;
}