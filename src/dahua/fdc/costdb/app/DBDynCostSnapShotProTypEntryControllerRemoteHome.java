package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotProTypEntryControllerRemoteHome extends EJBHome
{
    DBDynCostSnapShotProTypEntryControllerRemote create() throws CreateException, RemoteException;
}