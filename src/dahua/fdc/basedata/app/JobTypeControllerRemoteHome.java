package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface JobTypeControllerRemoteHome extends EJBHome
{
    JobTypeControllerRemote create() throws CreateException, RemoteException;
}