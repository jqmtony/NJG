package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ApportionInfoBaseControllerLocalHome extends EJBLocalHome
{
    ApportionInfoBaseControllerLocal create() throws CreateException;
}