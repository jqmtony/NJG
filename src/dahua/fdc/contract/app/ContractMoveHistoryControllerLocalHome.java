package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractMoveHistoryControllerLocalHome extends EJBLocalHome
{
    ContractMoveHistoryControllerLocal create() throws CreateException;
}