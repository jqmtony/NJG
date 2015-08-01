package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractBillEntryControllerRemoteHome extends EJBHome
{
    ContractBillEntryControllerRemote create() throws CreateException, RemoteException;
}