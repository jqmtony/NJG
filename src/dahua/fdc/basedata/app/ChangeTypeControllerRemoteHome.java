package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeTypeControllerRemoteHome extends EJBHome
{
    ChangeTypeControllerRemote create() throws CreateException, RemoteException;
}