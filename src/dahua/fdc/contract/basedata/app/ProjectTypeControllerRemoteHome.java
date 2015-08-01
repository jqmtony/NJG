package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectTypeControllerRemoteHome extends EJBHome
{
    ProjectTypeControllerRemote create() throws CreateException, RemoteException;
}