package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostAccountFacadeControllerRemoteHome extends EJBHome
{
    CostAccountFacadeControllerRemote create() throws CreateException, RemoteException;
}