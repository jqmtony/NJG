package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ApportionInfoBaseControllerRemoteHome extends EJBHome
{
    ApportionInfoBaseControllerRemote create() throws CreateException, RemoteException;
}