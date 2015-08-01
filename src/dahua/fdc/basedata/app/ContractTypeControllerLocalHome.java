package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractTypeControllerLocalHome extends EJBLocalHome
{
    ContractTypeControllerLocal create() throws CreateException;
}