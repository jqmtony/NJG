package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface LandDeveloperControllerRemoteHome extends EJBHome
{
    LandDeveloperControllerRemote create() throws CreateException, RemoteException;
}