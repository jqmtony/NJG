package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractChangeEntryControllerRemoteHome extends EJBHome
{
    ContractChangeEntryControllerRemote create() throws CreateException, RemoteException;
}