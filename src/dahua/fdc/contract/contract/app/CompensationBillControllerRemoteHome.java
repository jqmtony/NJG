package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CompensationBillControllerRemoteHome extends EJBHome
{
    CompensationBillControllerRemote create() throws CreateException, RemoteException;
}