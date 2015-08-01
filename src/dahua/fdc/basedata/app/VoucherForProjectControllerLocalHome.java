package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface VoucherForProjectControllerLocalHome extends EJBLocalHome
{
    VoucherForProjectControllerLocal create() throws CreateException;
}