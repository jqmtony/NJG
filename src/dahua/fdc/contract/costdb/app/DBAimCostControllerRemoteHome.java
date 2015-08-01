package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DBAimCostControllerRemoteHome extends EJBHome
{
    DBAimCostControllerRemote create() throws CreateException, RemoteException;
}