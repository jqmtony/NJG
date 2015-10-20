package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PayRequestBillEntryControllerLocalHome extends EJBLocalHome
{
    PayRequestBillEntryControllerLocal create() throws CreateException;
}