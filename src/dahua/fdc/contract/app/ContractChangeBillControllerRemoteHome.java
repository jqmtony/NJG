package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractChangeBillControllerRemoteHome extends EJBHome
{
    ContractChangeBillControllerRemote create() throws CreateException, RemoteException;
}