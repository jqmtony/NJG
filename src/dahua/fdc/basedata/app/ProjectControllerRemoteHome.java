package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectControllerRemoteHome extends EJBHome
{
    ProjectControllerRemote create() throws CreateException, RemoteException;
}