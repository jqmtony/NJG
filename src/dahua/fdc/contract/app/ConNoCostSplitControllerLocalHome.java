package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConNoCostSplitControllerLocalHome extends EJBLocalHome
{
    ConNoCostSplitControllerLocal create() throws CreateException;
}