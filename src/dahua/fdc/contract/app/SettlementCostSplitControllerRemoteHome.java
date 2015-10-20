package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettlementCostSplitControllerRemoteHome extends EJBHome
{
    SettlementCostSplitControllerRemote create() throws CreateException, RemoteException;
}