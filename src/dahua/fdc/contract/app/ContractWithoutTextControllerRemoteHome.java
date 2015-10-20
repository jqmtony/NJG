package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractWithoutTextControllerRemoteHome extends EJBHome
{
    ContractWithoutTextControllerRemote create() throws CreateException, RemoteException;
}