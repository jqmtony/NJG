package com.kingdee.eas.port.markesupplier.subill;

import java.io.Serializable;

public class MarketSupplierStockInfo extends AbstractMarketSupplierStockInfo implements Serializable 
{
    public MarketSupplierStockInfo()
    {
        super();
    }
    protected MarketSupplierStockInfo(String pkField)
    {
        super(pkField);
    }
}