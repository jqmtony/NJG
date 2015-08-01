package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSplitBillEntryControllerLocalHome extends EJBLocalHome
{
    FDCSplitBillEntryControllerLocal create() throws CreateException;
}