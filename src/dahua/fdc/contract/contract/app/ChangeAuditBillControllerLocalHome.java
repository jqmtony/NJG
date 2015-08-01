package com.kingdee.eas.fdc.contract.app;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ChangeAuditBillControllerLocalHome extends EJBLocalHome
{
    ChangeAuditBillControllerLocal create() throws CreateException;
}