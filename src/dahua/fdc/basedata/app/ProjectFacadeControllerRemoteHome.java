package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectFacadeControllerRemoteHome extends EJBHome
{
    ProjectFacadeControllerRemote create() throws CreateException, RemoteException;
}