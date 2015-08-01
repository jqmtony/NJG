package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface GuerdonOfPayReqBillControllerLocalHome extends EJBLocalHome
{
    GuerdonOfPayReqBillControllerLocal create() throws CreateException;
}