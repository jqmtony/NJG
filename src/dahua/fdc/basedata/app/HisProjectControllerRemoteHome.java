package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface HisProjectControllerRemoteHome extends EJBHome
{
    HisProjectControllerRemote create() throws CreateException, RemoteException;
}