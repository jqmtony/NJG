package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichInvoiceRequestEntryFactory
{
    private RichInvoiceRequestEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4EA17A6") ,com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4EA17A6") ,com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4EA17A6"));
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequestEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4EA17A6"));
    }
}