package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface RightManagerControllerRemoteHome extends EJBHome
{
    RightManagerControllerRemote create() throws CreateException, RemoteException;
}