package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractCostSplitEntryControllerLocalHome extends EJBLocalHome
{
    ContractCostSplitEntryControllerLocal create() throws CreateException;
}