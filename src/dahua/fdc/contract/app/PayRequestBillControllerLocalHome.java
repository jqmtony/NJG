package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayRequestBillControllerLocalHome extends EJBLocalHome
{
    PayRequestBillControllerLocal create() throws CreateException;
}