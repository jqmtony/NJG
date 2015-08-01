package com.kingdee.eas.fdc.basedata.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CostTargetControllerLocalHome extends EJBLocalHome
{
    CostTargetControllerLocal create() throws CreateException;
}