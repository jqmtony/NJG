package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ClearSplitFacadeControllerRemoteHome extends EJBHome
{
    ClearSplitFacadeControllerRemote create() throws CreateException, RemoteException;
}