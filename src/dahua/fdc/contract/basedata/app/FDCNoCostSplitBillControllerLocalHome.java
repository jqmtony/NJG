package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCNoCostSplitBillControllerLocalHome extends EJBLocalHome
{
    FDCNoCostSplitBillControllerLocal create() throws CreateException;
}