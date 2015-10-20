package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConChangeSplitEntryControllerRemoteHome extends EJBHome
{
    ConChangeSplitEntryControllerRemote create() throws CreateException, RemoteException;
}