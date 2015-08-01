package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ContractCodingTypeControllerLocalHome extends EJBLocalHome
{
    ContractCodingTypeControllerLocal create() throws CreateException;
}