package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ProductTypeControllerLocalHome extends EJBLocalHome
{
    ProductTypeControllerLocal create() throws CreateException;
}