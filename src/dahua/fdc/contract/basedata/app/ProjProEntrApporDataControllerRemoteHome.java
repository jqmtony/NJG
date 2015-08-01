package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProjProEntrApporDataControllerRemoteHome extends EJBHome
{
    ProjProEntrApporDataControllerRemote create() throws CreateException, RemoteException;
}