package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractCostSplitEntryControllerRemoteHome extends EJBHome
{
    ContractCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}