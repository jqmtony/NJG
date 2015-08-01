package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CompensationBillControllerLocalHome extends EJBLocalHome
{
    CompensationBillControllerLocal create() throws CreateException;
}