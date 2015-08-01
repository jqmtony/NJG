package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostAccountAssignControllerRemoteHome extends EJBHome
{
    CostAccountAssignControllerRemote create() throws CreateException, RemoteException;
}