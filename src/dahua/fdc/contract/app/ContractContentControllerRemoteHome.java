package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractContentControllerRemoteHome extends EJBHome
{
    ContractContentControllerRemote create() throws CreateException, RemoteException;
}