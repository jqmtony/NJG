package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface SettlementCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    SettlementCostSplitEntryControllerLocal create() throws CreateException;
}