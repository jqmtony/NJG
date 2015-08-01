package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DirectorControllerRemoteHome extends EJBHome
{
    DirectorControllerRemote create() throws CreateException, RemoteException;
}