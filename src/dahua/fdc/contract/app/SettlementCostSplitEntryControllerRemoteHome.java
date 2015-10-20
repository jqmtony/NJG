package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettlementCostSplitEntryControllerRemoteHome extends EJBHome
{
    SettlementCostSplitEntryControllerRemote create() throws CreateException, RemoteException;
}