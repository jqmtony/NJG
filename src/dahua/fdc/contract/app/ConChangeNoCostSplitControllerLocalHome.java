package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ConChangeNoCostSplitControllerLocalHome extends EJBLocalHome
{
    ConChangeNoCostSplitControllerLocal create() throws CreateException;
}