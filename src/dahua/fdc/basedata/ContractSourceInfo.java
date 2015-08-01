package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;


public class ContractSourceInfo extends AbstractContractSourceInfo implements Serializable 
{
    public ContractSourceInfo()
    {
        super();
    }
    protected ContractSourceInfo(String pkField)
    {
        super(pkField);
    }
    public static final String TRUST_VALUE = "bEoryAEeEADgAAjgwKgQs5qx1z8=";
    public static final String INVITE_VALUE = "bEoryAEeEADgAAjnwKgQs5qx1z8=";
    public static final String DISCUSS_VALUE = "bEoryAEeEADgAAjqwKgQs5qx1z8=";
    public static final String COOP_VALUE = "bEoryAEeEADgAAl2wKgQs5qx1z8=";
    
    public static final ContractSourceInfo TRUST = new ContractSourceInfo(TRUST_VALUE);
    public static final ContractSourceInfo INVITE = new ContractSourceInfo(INVITE_VALUE);
    public static final ContractSourceInfo DISCUSS = new ContractSourceInfo(DISCUSS_VALUE);
    public static final ContractSourceInfo COOP = new ContractSourceInfo(COOP_VALUE);
    
    
}