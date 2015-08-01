package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface BeforeAccountViewControllerRemoteHome extends EJBHome
{
    BeforeAccountViewControllerRemote create() throws CreateException, RemoteException;
}