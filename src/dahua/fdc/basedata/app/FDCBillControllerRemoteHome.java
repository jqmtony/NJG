package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCBillControllerRemoteHome extends EJBHome
{
    FDCBillControllerRemote create() throws CreateException, RemoteException;
}