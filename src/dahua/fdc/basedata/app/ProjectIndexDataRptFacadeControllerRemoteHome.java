package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjectIndexDataRptFacadeControllerRemoteHome extends EJBHome
{
    ProjectIndexDataRptFacadeControllerRemote create() throws CreateException, RemoteException;
}