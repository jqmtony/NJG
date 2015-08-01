package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndexMemoFactory
{
    private IndexMemoFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IIndexMemo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIndexMemo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("65E4927A") ,com.kingdee.eas.fdc.basedata.IIndexMemo.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IIndexMemo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIndexMemo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("65E4927A") ,com.kingdee.eas.fdc.basedata.IIndexMemo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IIndexMemo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIndexMemo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("65E4927A"));
    }
    public static com.kingdee.eas.fdc.basedata.IIndexMemo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IIndexMemo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("65E4927A"));
    }
}