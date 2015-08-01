package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBDynCostSnapShotControllerRemoteHome extends EJBHome
{
    DBDynCostSnapShotControllerRemote create() throws CreateException, RemoteException;
}