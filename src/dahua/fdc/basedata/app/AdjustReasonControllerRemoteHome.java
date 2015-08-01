package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface AdjustReasonControllerRemoteHome extends EJBHome
{
    AdjustReasonControllerRemote create() throws CreateException, RemoteException;
}