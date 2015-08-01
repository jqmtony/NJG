package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeAuditBillControllerRemoteHome extends EJBHome
{
    ChangeAuditBillControllerRemote create() throws CreateException, RemoteException;
}