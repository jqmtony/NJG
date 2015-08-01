package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CurProjectControllerRemoteHome extends EJBHome
{
    CurProjectControllerRemote create() throws CreateException, RemoteException;
}