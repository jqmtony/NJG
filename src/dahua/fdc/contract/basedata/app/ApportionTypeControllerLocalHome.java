package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ApportionTypeControllerLocalHome extends EJBLocalHome
{
    ApportionTypeControllerLocal create() throws CreateException;
}