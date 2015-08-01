package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductAccountEntrysFactory
{
    private DeductAccountEntrysFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IDeductAccountEntrys getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductAccountEntrys)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E5665E99") ,com.kingdee.eas.fdc.basedata.IDeductAccountEntrys.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IDeductAccountEntrys getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductAccountEntrys)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E5665E99") ,com.kingdee.eas.fdc.basedata.IDeductAccountEntrys.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IDeductAccountEntrys getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductAccountEntrys)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E5665E99"));
    }
    public static com.kingdee.eas.fdc.basedata.IDeductAccountEntrys getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IDeductAccountEntrys)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E5665E99"));
    }
}