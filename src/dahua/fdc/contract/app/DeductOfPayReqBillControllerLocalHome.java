package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface DeductOfPayReqBillControllerLocalHome extends EJBLocalHome
{
    DeductOfPayReqBillControllerLocal create() throws CreateException;
}