package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class HousingFactory
{
    private HousingFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IHousing getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IHousing)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("07717B76") ,com.kingdee.eas.fdc.gcftbiaoa.IHousing.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IHousing getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IHousing)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("07717B76") ,com.kingdee.eas.fdc.gcftbiaoa.IHousing.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IHousing getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IHousing)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("07717B76"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IHousing getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IHousing)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("07717B76"));
    }
}