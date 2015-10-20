package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractBillControllerLocalHome extends EJBLocalHome
{
    ContractBillControllerLocal create() throws CreateException;
}