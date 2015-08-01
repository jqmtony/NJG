package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface UnitDataManagerControllerRemoteHome extends EJBHome
{
    UnitDataManagerControllerRemote create() throws CreateException, RemoteException;
}