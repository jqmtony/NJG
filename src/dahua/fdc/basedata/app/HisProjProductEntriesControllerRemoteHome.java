package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HisProjProductEntriesControllerRemoteHome extends EJBHome
{
    HisProjProductEntriesControllerRemote create() throws CreateException, RemoteException;
}