package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSQLFacadeControllerRemoteHome extends EJBHome
{
    FDCSQLFacadeControllerRemote create() throws CreateException, RemoteException;
}