package com.kingdee.eas.fdc.costdb.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FdcMaterialControllerRemoteHome extends EJBHome
{
    FdcMaterialControllerRemote create() throws CreateException, RemoteException;
}