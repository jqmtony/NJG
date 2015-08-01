package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCNoCostSplitBillEntryControllerLocalHome extends EJBLocalHome
{
    FDCNoCostSplitBillEntryControllerLocal create() throws CreateException;
}