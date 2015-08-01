package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectStatusControllerRemoteHome extends EJBHome
{
    ProjectStatusControllerRemote create() throws CreateException, RemoteException;
}