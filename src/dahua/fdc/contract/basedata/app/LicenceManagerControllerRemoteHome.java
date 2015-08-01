package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LicenceManagerControllerRemoteHome extends EJBHome
{
    LicenceManagerControllerRemote create() throws CreateException, RemoteException;
}