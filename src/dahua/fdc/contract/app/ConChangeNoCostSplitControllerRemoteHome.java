package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConChangeNoCostSplitControllerRemoteHome extends EJBHome
{
    ConChangeNoCostSplitControllerRemote create() throws CreateException, RemoteException;
}