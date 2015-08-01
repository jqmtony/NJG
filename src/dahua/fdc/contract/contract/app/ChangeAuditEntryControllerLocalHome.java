package com.kingdee.eas.fdc.contract.app;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface ChangeAuditEntryControllerLocalHome extends EJBLocalHome
{
    ChangeAuditEntryControllerLocal create() throws CreateException;
}