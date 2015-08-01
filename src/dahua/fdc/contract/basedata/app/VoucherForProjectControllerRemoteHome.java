package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface VoucherForProjectControllerRemoteHome extends EJBHome
{
    VoucherForProjectControllerRemote create() throws CreateException, RemoteException;
}