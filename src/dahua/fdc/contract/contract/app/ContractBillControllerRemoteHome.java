package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractBillControllerRemoteHome extends EJBHome
{
    ContractBillControllerRemote create() throws CreateException, RemoteException;
}