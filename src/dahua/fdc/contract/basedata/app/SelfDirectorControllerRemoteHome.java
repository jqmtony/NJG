package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SelfDirectorControllerRemoteHome extends EJBHome
{
    SelfDirectorControllerRemote create() throws CreateException, RemoteException;
}