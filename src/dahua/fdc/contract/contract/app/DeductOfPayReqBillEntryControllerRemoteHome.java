package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductOfPayReqBillEntryControllerRemoteHome extends EJBHome
{
    DeductOfPayReqBillEntryControllerRemote create() throws CreateException, RemoteException;
}