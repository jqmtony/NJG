package com.kingdee.eas.fdc.contract.app;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface ContractBillContractPlanControllerLocalHome extends EJBLocalHome
{
    ContractBillContractPlanControllerLocal create() throws CreateException;
}