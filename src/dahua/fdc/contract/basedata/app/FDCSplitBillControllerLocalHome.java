package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface FDCSplitBillControllerLocalHome extends EJBLocalHome
{
    FDCSplitBillControllerLocal create() throws CreateException;
}