package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettNoCostSplitEntryControllerRemoteHome extends EJBHome
{
    SettNoCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}