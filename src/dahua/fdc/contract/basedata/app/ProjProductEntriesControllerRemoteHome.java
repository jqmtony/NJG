package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjProductEntriesControllerRemoteHome extends EJBHome
{
    ProjProductEntriesControllerRemote create() throws CreateException, RemoteException;
}