package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjCostEntriesControllerRemoteHome extends EJBHome
{
    ProjCostEntriesControllerRemote create() throws CreateException, RemoteException;
}