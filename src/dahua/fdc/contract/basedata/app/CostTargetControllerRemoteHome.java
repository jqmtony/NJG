package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostTargetControllerRemoteHome extends EJBHome
{
    CostTargetControllerRemote create() throws CreateException, RemoteException;
}