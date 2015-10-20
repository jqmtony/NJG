package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductOfPayReqBillControllerRemoteHome extends EJBHome
{
    DeductOfPayReqBillControllerRemote create() throws CreateException, RemoteException;
}