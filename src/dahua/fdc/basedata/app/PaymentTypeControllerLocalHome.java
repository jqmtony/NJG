package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PaymentTypeControllerLocalHome extends EJBLocalHome
{
    PaymentTypeControllerLocal create() throws CreateException;
}