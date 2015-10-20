package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CopySupplierEntryControllerLocalHome extends EJBLocalHome
{
    CopySupplierEntryControllerLocal create() throws CreateException;
}