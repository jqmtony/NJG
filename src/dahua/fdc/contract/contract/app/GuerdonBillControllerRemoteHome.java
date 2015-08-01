package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface GuerdonBillControllerRemoteHome extends EJBHome
{
    GuerdonBillControllerRemote create() throws CreateException, RemoteException;
}