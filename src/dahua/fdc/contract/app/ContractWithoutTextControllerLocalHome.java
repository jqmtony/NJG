package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractWithoutTextControllerLocalHome extends EJBLocalHome
{
    ContractWithoutTextControllerLocal create() throws CreateException;
}