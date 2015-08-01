package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConNoCostSplitControllerRemoteHome extends EJBHome
{
    ConNoCostSplitControllerRemote create() throws CreateException, RemoteException;
}