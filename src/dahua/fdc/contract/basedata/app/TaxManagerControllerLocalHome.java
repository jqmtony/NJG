package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface TaxManagerControllerLocalHome extends EJBLocalHome
{
    TaxManagerControllerLocal create() throws CreateException;
}