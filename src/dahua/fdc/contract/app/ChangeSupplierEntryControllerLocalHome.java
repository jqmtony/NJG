package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeSupplierEntryControllerLocalHome extends EJBLocalHome
{
    ChangeSupplierEntryControllerLocal create() throws CreateException;
}