package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeAuditEntryControllerRemoteHome extends EJBHome
{
    ChangeAuditEntryControllerRemote create() throws CreateException, RemoteException;
}