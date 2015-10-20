package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConChangeNoCostSplitEntryControllerRemoteHome extends EJBHome
{
    ConChangeNoCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}