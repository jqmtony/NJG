package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductTypeControllerRemoteHome extends EJBHome
{
    DeductTypeControllerRemote create() throws CreateException, RemoteException;
}