package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConNoCostSplitEntryControllerRemoteHome extends EJBHome
{
    ConNoCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}