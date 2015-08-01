package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCDataBaseControllerRemoteHome extends EJBHome
{
    FDCDataBaseControllerRemote create() throws CreateException, RemoteException;
}