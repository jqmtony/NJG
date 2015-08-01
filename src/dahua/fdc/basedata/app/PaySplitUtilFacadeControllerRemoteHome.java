package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaySplitUtilFacadeControllerRemoteHome extends EJBHome
{
    PaySplitUtilFacadeControllerRemote create() throws CreateException, RemoteException;
}