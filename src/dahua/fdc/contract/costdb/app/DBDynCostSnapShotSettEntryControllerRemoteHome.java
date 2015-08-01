package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotSettEntryControllerRemoteHome extends EJBHome
{
    DBDynCostSnapShotSettEntryControllerRemote create() throws CreateException, RemoteException;
}