package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettNoCostSplitControllerRemoteHome extends EJBHome
{
    SettNoCostSplitControllerRemote create() throws CreateException, RemoteException;
}