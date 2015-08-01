package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.bos.framework.*;
import java.util.List;
import java.sql.Timestamp;

public interface IFDCSQLFacade extends IBizCtrl
{
    public void executeUpdate(String sql, List params) throws BOSException;
    public IRowSet executeQuery(String sql, List params) throws BOSException;
    public Timestamp getServerTime() throws BOSException;
    public void setSQLTrace(boolean enable, String logFile) throws BOSException;
    public void executeBatch(List sqls) throws BOSException;
    public void executeBatch(String sql, List params) throws BOSException;
}