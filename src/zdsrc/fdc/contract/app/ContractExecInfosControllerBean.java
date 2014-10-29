/*jadclipse*/package com.kingdee.eas.fdc.contract.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.*;
import com.kingdee.eas.fdc.finance.IWorkLoadConfirmBill;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fi.cas.*;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;
public class ContractExecInfosControllerBean extends AbstractContractExecInfosControllerBean
{
            public ContractExecInfosControllerBean()
            {
            }
            protected void _updateContract(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {

/*  65*/        if(contractId.equals(""))
/*  66*/            return;

/*  68*/        boolean isContract = isContract(contractId);






/*  75*/        if("audit".equals(type))
                {/*  76*/            ContractExecInfosInfo info = new ContractExecInfosInfo();
/*  77*/            if(isContract)
                    {/*  78*/                ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));

/*  80*/                info.setContractBill(conBill);
/*  81*/                info.setCurProject(conBill.getCurProject());
/*  82*/                info.setPeriod(conBill.getPeriod());
/*  83*/                info.setCU(conBill.getCU());
/*  84*/                info.setOrgUnit(conBill.getOrgUnit());
/*  85*/                info.setHasSettled(conBill.isHasSettled());

/*  87*/                info.setCostAmt(conBill.getAmount());

/*  89*/                info.setCostAmtOri(conBill.getOriginalAmount());
/*  90*/                info.setIsNoText(false);
/*  91*/                _addnew(ctx, info);



/*  95*/                _updateChange(ctx, "audit", contractId);
                    } else
                    {/*  97*/                ContractWithoutTextInfo conWithoutText = ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));

/*  99*/                info.setConWithoutText(conWithoutText);
/* 100*/                info.setCurProject(conWithoutText.getCurProject());

/* 102*/                info.setCostAmt(conWithoutText.getAmount());

/* 104*/                info.setCostAmtOri(conWithoutText.getOriginalAmount());
/* 105*/                info.setCompletedAmt(conWithoutText.getAmount());
/* 106*/                info.setInvoicedAmt(conWithoutText.getInvoiceAmt());
/* 107*/                info.setPeriod(conWithoutText.getPeriod());
/* 108*/                info.setCU(conWithoutText.getCU());
/* 109*/                info.setOrgUnit(conWithoutText.getOrgUnit());
/* 110*/                info.setIsNoText(true);
/* 111*/                _addnew(ctx, info);
                    }
                } else
/* 114*/        if("unAudit".equals(type))
                {




/* 120*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 121*/            if(isContract)
/* 122*/                builder.appendSql("delete from T_CON_ContractExecInfos where FContractBillId=?");

/* 124*/            else/* 124*/                builder.appendSql("delete from T_CON_ContractExecInfos where FConWithoutTextID=?");
/* 125*/            builder.addParam(contractId);
/* 126*/            builder.execute();
                }
            }
            protected void _updateChange(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {





/* 137*/        if(FDCHelper.isEmpty(contractId))
/* 138*/            return;






/* 145*/        if("audit".equals(type) || "unAudit".equals(type))
                {

/* 148*/            IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 149*/            EntityViewInfo view = new EntityViewInfo();
/* 150*/            view.getSelector().add("id");
/* 151*/            view.getSelector().add("hasSettled");
/* 152*/            view.getSelector().add("costAmt");
/* 153*/            view.getSelector().add("costAmtOri");
/* 154*/            view.getSelector().add("changeAmt");
/* 155*/            view.getSelector().add("changeAmtOri");
/* 156*/            FilterInfo filter = new FilterInfo();
/* 157*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 158*/            view.setFilter(filter);

/* 160*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 161*/            ContractExecInfosInfo info = null;
/* 162*/            if(coll != null && coll.size() == 1)
/* 163*/                info = coll.get(0);




/* 168*/            BigDecimal temp = FDCHelper.ZERO;
/* 169*/            BigDecimal tempOri = FDCHelper.ZERO;
/* 170*/            if(info == null)
/* 171*/                return;

/* 173*/            ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));


/* 176*/            temp = conBill.getAmount();
/* 177*/            tempOri = conBill.getOriginalAmount();

/* 179*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 180*/            builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");




/* 185*/            builder.appendParam("FContractBillID", contractId);
/* 186*/            builder.appendSql(" and (");
/* 187*/            builder.appendParam("FState", "4AUDITTED");
/* 188*/            builder.appendSql(" or ");
/* 189*/            builder.appendParam("FState", "8VISA");
/* 190*/            builder.appendSql(" or ");
/* 191*/            builder.appendParam("FState", "7ANNOUNCE");
/* 192*/            builder.appendSql(" )");
/* 193*/            IRowSet rs = builder.executeQuery();
/* 194*/            BigDecimal changeAmt = FDCHelper.ZERO;
/* 195*/            BigDecimal changeAmtOri = FDCHelper.ZERO;

/* 197*/            try
                    {
/* <-MISALIGNED-> */ /* 197*/                while(rs.next()) 
                        {
/* <-MISALIGNED-> */ /* 198*/                    changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 199*/                    changeAmtOri = rs.getBigDecimal("changeAmtOri");
                        }
                    }
/* <-MISALIGNED-> */ /* 201*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 202*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 203*/                logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 204*/                throw new BOSException(e);
                    }
/* <-MISALIGNED-> */ /* 206*/            info.setCostAmt(FDCHelper.add(temp, changeAmt));
/* <-MISALIGNED-> */ /* 207*/            info.setCostAmtOri(FDCHelper.add(tempOri, changeAmtOri));
/* <-MISALIGNED-> */ /* 208*/            info.setChangeAmt(changeAmt);
/* <-MISALIGNED-> */ /* 209*/            info.setChangeAmtOri(changeAmtOri);
/* <-MISALIGNED-> */ /* 210*/            SelectorItemCollection selector = new SelectorItemCollection();
/* <-MISALIGNED-> */ /* 211*/            selector.add("changeAmt");
/* <-MISALIGNED-> */ /* 212*/            selector.add("changeAmtOri");
/* <-MISALIGNED-> */ /* 213*/            selector.add("costAmt");
/* <-MISALIGNED-> */ /* 214*/            selector.add("costAmtOri");
/* <-MISALIGNED-> */ /* 215*/            if(!info.isHasSettled())
                    {
/* <-MISALIGNED-> */ /* 216*/                BigDecimal completePrjAmt = getProcessAmt(ctx, contractId);
/* <-MISALIGNED-> */ /* 217*/                info.setNotCompletedAmt(FDCHelper.subtract(FDCHelper.add(temp, changeAmt), completePrjAmt));
/* <-MISALIGNED-> */ /* 218*/                selector.add("notCompletedAmt");
                    }
/* <-MISALIGNED-> */ /* 220*/            _updatePartial(ctx, info, selector);
/* <-MISALIGNED-> */ /* 221*/            if(info.isHasSettled())
/* <-MISALIGNED-> */ /* 223*/                _updateSettle(ctx, "audit", contractId);
                }
/* <-MISALIGNED-> */ /* 228*/        _updatePayment(ctx, "pay", contractId);
            }
            protected void _updateSettle(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {


/* 237*/        if(contractId.equals(""))
/* 238*/            return;










/* 249*/        ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));

/* 251*/        BigDecimal temp = FDCHelper.ZERO;
/* 252*/        BigDecimal tempOri = FDCHelper.ZERO;
/* 253*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 254*/        IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 255*/        EntityViewInfo view = new EntityViewInfo();
/* 256*/        view.getSelector().add("id");
/* 257*/        view.getSelector().add("costAmt");
/* 258*/        view.getSelector().add("changeAmt");
/* 259*/        view.getSelector().add("costAmtOri");
/* 260*/        view.getSelector().add("changeAmtOri");
/* 261*/        view.getSelector().add("completedAmt");
/* 262*/        view.getSelector().add("notCompletedAmt");
/* 263*/        FilterInfo filter = new FilterInfo();
/* 264*/        filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 265*/        view.setFilter(filter);

/* 267*/        ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 268*/        ContractExecInfosInfo info = null;
/* 269*/        if(coll != null && coll.size() == 1)
/* 270*/            info = coll.get(0);

/* 272*/        if(info == null)
/* 273*/            return;

/* 275*/        if("audit".equals(type))
                {/* 276*/            if(conBill.isHasSettled())
                    {/* 277*/                temp = conBill.getSettleAmt();
/* 278*/                tempOri = getSettleAmtOri(ctx, contractId);
/* 279*/                info.setCostAmt(temp);
/* 280*/                info.setCostAmtOri(tempOri);
/* 281*/                info.setSettleAmt(temp);
/* 282*/                info.setSettleAmtOri(tempOri);
                    } else
                    {
/* 285*/                FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 286*/                builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");




/* 291*/                builder.appendParam("FContractBillID", contractId);
/* 292*/                builder.appendSql(" and (");
/* 293*/                builder.appendParam("FState", "4AUDITTED");
/* 294*/                builder.appendSql(" or ");
/* 295*/                builder.appendParam("FState", "8VISA");
/* 296*/                builder.appendSql(" or ");
/* 297*/                builder.appendParam("FState", "7ANNOUNCE");
/* 298*/                builder.appendSql(" )");
/* 299*/                IRowSet rs = builder.executeQuery();
/* 300*/                BigDecimal changeAmt = FDCHelper.ZERO;
/* 301*/                BigDecimal changeAmtOri = FDCHelper.ZERO;

/* 303*/                try
                        {
/* <-MISALIGNED-> */ /* 303*/                    while(rs.next()) 
                            {
/* <-MISALIGNED-> */ /* 304*/                        changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 305*/                        changeAmtOri = rs.getBigDecimal("changeAmtOri");
                            }
                        }
/* <-MISALIGNED-> */ /* 307*/                catch(SQLException e)
                        {
/* <-MISALIGNED-> */ /* 308*/                    e.printStackTrace();
/* <-MISALIGNED-> */ /* 309*/                    logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 310*/                    throw new BOSException(e);
                        }
/* <-MISALIGNED-> */ /* 313*/                temp = FDCHelper.add(conBill.getAmount(), changeAmt);
/* <-MISALIGNED-> */ /* 314*/                tempOri = FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri);
/* <-MISALIGNED-> */ /* 315*/                info.setCostAmt(temp);
/* <-MISALIGNED-> */ /* 316*/                info.setCostAmtOri(tempOri);
                    }
/* <-MISALIGNED-> */ /* 319*/            info.setHasSettled(conBill.isHasSettled());
/* <-MISALIGNED-> */ /* 320*/            BigDecimal processAmt = getProcessAmt(ctx, contractId);
/* <-MISALIGNED-> */ /* 321*/            info.setCompletedAmt(processAmt);
/* <-MISALIGNED-> */ /* 323*/            info.setNotCompletedAmt(FDCHelper.subtract(temp, processAmt));
/* <-MISALIGNED-> */ /* 324*/            selector.add("costAmt");
/* <-MISALIGNED-> */ /* 325*/            selector.add("costAmtOri");
/* <-MISALIGNED-> */ /* 326*/            selector.add("hasSettled");
/* <-MISALIGNED-> */ /* 327*/            selector.add("settleAmt");
/* <-MISALIGNED-> */ /* 328*/            selector.add("settleAmtOri");/* 330*/            selector.add("completedAmt");
/* 331*/            selector.add("notCompletedAmt");
/* 332*/            _updatePartial(ctx, info, selector);
                } else/* 333*/        if("unAudit".equals(type))
                {
/* 335*/            info.setSettleAmt(null);
/* 336*/            info.setSettleAmtOri(null);
/* 337*/            info.setHasSettled(false);












/* 350*/            temp = getProcessAmt(ctx, contractId);

/* 352*/            info.setCompletedAmt(temp);
/* 353*/            BigDecimal changeAmt = getChangeAmt(ctx, contractId);
/* 354*/            BigDecimal changeAmtOri = getChangeAmtOri(ctx, contractId);

/* 356*/            if(changeAmt == null || changeAmt.compareTo(FDCHelper.ZERO) == 0)
                    {/* 357*/                info.setChangeAmt(null);
/* 358*/                info.setChangeAmtOri(null);
/* 359*/                info.setCostAmt(conBill.getAmount());
/* 360*/                info.setCostAmtOri(conBill.getOriginalAmount());
                    } else
                    {/* 362*/                info.setChangeAmt(changeAmt);
/* 363*/                info.setChangeAmtOri(changeAmtOri);
/* 364*/                info.setCostAmt(FDCHelper.add(conBill.getAmount(), changeAmt));
/* 365*/                info.setCostAmtOri(FDCHelper.add(conBill.getOriginalAmount(), changeAmtOri));
                    }

/* 368*/            info.setNotCompletedAmt(FDCHelper.subtract(info.getCostAmt(), info.getCompletedAmt()));
/* 369*/            selector.add("costAmt");
/* 370*/            selector.add("costAmtOri");
/* 371*/            selector.add("hasSettled");
/* 372*/            selector.add("settleAmt");
/* 373*/            selector.add("settleAmtOri");
/* 374*/            selector.add("completedAmt");
/* 375*/            selector.add("notCompletedAmt");
/* 376*/            _updatePartial(ctx, info, selector);
                }
            }
            protected void _updatePayment(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {







/* 389*/        if(contractId.equals(""))
/* 390*/            return;











/* 402*/        boolean isCon = isContract(contractId);
/* 403*/        IContractExecInfos ice = ContractExecInfosFactory.getLocalInstance(ctx);
/* 404*/        EntityViewInfo view = new EntityViewInfo();
/* 405*/        SelectorItemCollection selector = new SelectorItemCollection();
/* 406*/        if(isCon)
                {/* 407*/            view.getSelector().add("id");
/* 408*/            view.getSelector().add("costAmt");
/* 409*/            view.getSelector().add("costAmtOri");
/* 410*/            view.getSelector().add("deductedAmt");
/* 411*/            view.getSelector().add("deductedAmtOri");
/* 412*/            view.getSelector().add("compensatedAmt");
/* 413*/            view.getSelector().add("compensatedAmtOri");
/* 414*/            view.getSelector().add("guerdonAmt");
/* 415*/            view.getSelector().add("guerdonAmtOri");
/* 416*/            view.getSelector().add("completedAmt");
/* 417*/            view.getSelector().add("notCompletedAmt");
/* 418*/            view.getSelector().add("invoicedAmt");
/* 419*/            view.getSelector().add("partAMatlAmt");
/* 420*/            view.getSelector().add("partAMatLoaAmt");
/* 421*/            FilterInfo filter = new FilterInfo();
/* 422*/            filter.getFilterItems().add(new FilterItemInfo("contractBill", contractId));
/* 423*/            view.setFilter(filter);
/* 424*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 425*/            ContractExecInfosInfo info = null;
/* 426*/            if(coll != null && coll.size() == 1)
/* 427*/                info = coll.get(0);

/* 429*/            if(info == null)
/* 430*/                return;


/* 433*/            BigDecimal deductedAmt = FDCHelper.ZERO;
/* 434*/            BigDecimal deductedAmtOri = FDCHelper.ZERO;

/* 436*/            BigDecimal compensatedAmt = FDCHelper.ZERO;
/* 437*/            BigDecimal compensatedAmtOri = FDCHelper.ZERO;

/* 439*/            BigDecimal guerdonAmt = FDCHelper.ZERO;
/* 440*/            BigDecimal guerdonAmtOri = FDCHelper.ZERO;

/* 442*/            BigDecimal invoicedAmt = FDCHelper.ZERO;

/* 444*/            BigDecimal partAMatlAmt = FDCHelper.ZERO;
/* 445*/            BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
/* 446*/            Map ortherAmt = getOrtherAmt(ctx, contractId);
/* 447*/            if(ortherAmt != null && ortherAmt.size() > 0)
                    {/* 448*/                deductedAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("deductedAmt").toString());
/* 449*/                deductedAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("deductedAmtOri").toString());
/* 450*/                compensatedAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("compensatedAmt").toString());
/* 451*/                compensatedAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("compensatedAmtOri").toString());
/* 452*/                guerdonAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("guerdonAmt").toString());
/* 453*/                guerdonAmtOri = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("guerdonAmtOri").toString());
/* 454*/                invoicedAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("invoicedAmt").toString());
/* 455*/                partAMatlAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("partAMatlAmt").toString());
/* 456*/                partAMatLoaAmt = (BigDecimal)ortherAmt.get((new StringBuilder()).append(contractId).append("partAMatLoaAmt").toString());
                    }

/* 459*/            if("pay".equals(type) || "unPay".equals(type))
                    {
/* 461*/                ContractBillInfo conBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));

/* 463*/                boolean hasPaySettle = hasPaySettle(ctx, contractId, conBill.isHasSettled());










/* 474*/                if(hasPaySettle)
                        {/* 475*/                    info.setCompletedAmt(conBill.getSettleAmt());
/* 476*/                    info.setNotCompletedAmt(FDCHelper.ZERO);
                        }







/* 485*/                info.setDeductedAmt(deductedAmt);
/* 486*/                info.setDeductedAmtOri(deductedAmtOri);
/* 487*/                info.setCompensatedAmt(compensatedAmt);
/* 488*/                info.setCompensatedAmtOri(compensatedAmtOri);
/* 489*/                info.setGuerdonAmt(guerdonAmt);
/* 490*/                info.setGuerdonAmtOri(guerdonAmtOri);
/* 491*/                info.setInvoicedAmt(invoicedAmt);
/* 492*/                info.setPaidAmt(getPaidAmt(ctx, contractId));
/* 493*/                info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));
/* 494*/                info.setPartAMatlAmt(partAMatlAmt);
/* 495*/                info.setPartAMatLoaAmt(partAMatLoaAmt);
/* 496*/                selector.add("deductedAmt");
/* 497*/                selector.add("deductedAmtOri");
/* 498*/                selector.add("compensatedAmt");
/* 499*/                selector.add("compensatedAmtOri");
/* 500*/                selector.add("guerdonAmt");
/* 501*/                selector.add("guerdonAmtOri");
/* 502*/                selector.add("paidAmt");
/* 503*/                selector.add("paidAmtOri");
/* 504*/                selector.add("completedAmt");
/* 505*/                selector.add("notCompletedAmt");
/* 506*/                selector.add("invoicedAmt");
/* 507*/                selector.add("partAMatlAmt");
/* 508*/                selector.add("partAMatLoaAmt");
/* 509*/                _updatePartial(ctx, info, selector);
                    }
                } else
                {/* 512*/            view.getSelector().add("id");
/* 513*/            view.getSelector().add("costAmt");
/* 514*/            view.getSelector().add("costAmtOri");
/* 515*/            view.getSelector().add("completedAmt");
/* 516*/            view.getSelector().add("notCompletedAmt");
/* 517*/            view.getSelector().add("invoicedAmt");
/* 518*/            FilterInfo filter = new FilterInfo();
/* 519*/            filter.getFilterItems().add(new FilterItemInfo("conWithoutText", contractId));
/* 520*/            view.setFilter(filter);
/* 521*/            ContractExecInfosCollection coll = ice.getContractExecInfosCollection(view);
/* 522*/            ContractExecInfosInfo info = null;
/* 523*/            if(coll != null && coll.size() == 1)
/* 524*/                info = coll.get(0);

/* 526*/            if(info == null)
/* 527*/                return;



/* 531*/            if("pay".equals(type) || "unPay".equals(type))
                    {





/* 538*/                selector.add("paidAmt");
/* 539*/                selector.add("paidAmtOri");
/* 540*/                info.setPaidAmt(getPaidAmt(ctx, contractId));
/* 541*/                info.setPaidAmtOri(getPaidAmtOri(ctx, contractId));





















/* 563*/                _updatePartial(ctx, info, selector);
                    }
                }
            }
            protected BigDecimal getChangeAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {








/* 578*/        if(contractId.equals(""))
/* 579*/            return null;

/* 581*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 582*/        builder.appendSql("select sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt from T_Con_ContractChangeBill where ");


/* 585*/        builder.appendParam("FContractBillID", contractId);
/* 586*/        builder.appendSql(" and (");
/* 587*/        builder.appendParam("FState", "4AUDITTED");
/* 588*/        builder.appendSql(" or ");
/* 589*/        builder.appendParam("FState", "8VISA");
/* 590*/        builder.appendSql(" or ");
/* 591*/        builder.appendParam("FState", "7ANNOUNCE");
/* 592*/        builder.appendSql(" )");
/* 593*/        IRowSet rs = builder.executeQuery();
/* 594*/        BigDecimal changeAmt = null;

/* 596*/        try
                {
/* <-MISALIGNED-> */ /* 596*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 597*/                changeAmt = rs.getBigDecimal("changeAmt");
                }
/* <-MISALIGNED-> */ /* 599*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 600*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 601*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 602*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 604*/        return changeAmt;
            }
            protected BigDecimal getChangeAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {





/* 617*/        if(contractId.equals(""))
/* 618*/            return null;

/* 620*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 621*/        builder.appendSql("select sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");


/* 624*/        builder.appendParam("FContractBillID", contractId);
/* 625*/        builder.appendSql(" and (");
/* 626*/        builder.appendParam("FState", "4AUDITTED");
/* 627*/        builder.appendSql(" or ");
/* 628*/        builder.appendParam("FState", "8VISA");
/* 629*/        builder.appendSql(" or ");
/* 630*/        builder.appendParam("FState", "7ANNOUNCE");
/* 631*/        builder.appendSql(" )");
/* 632*/        IRowSet rs = builder.executeQuery();
/* 633*/        BigDecimal changeAmtOri = null;

/* 635*/        try
                {
/* <-MISALIGNED-> */ /* 635*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 636*/                changeAmtOri = rs.getBigDecimal("changeAmtOri");
                }
/* <-MISALIGNED-> */ /* 638*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 639*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 640*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 641*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 643*/        return changeAmtOri;
            }
            protected Map getChangeAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {





/* 656*/        Map changeMap = new HashMap();
/* 657*/        if(conIds == null || conIds.size() < 0)
/* 658*/            return changeMap;

/* 660*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 661*/        builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when  0 then  famount else fbalanceamount end ) as changeAmt, sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");



/* 665*/        builder.appendParam("FContractBillID", conIds.toArray());
/* 666*/        builder.appendSql(" and (");
/* 667*/        builder.appendParam("FState", "4AUDITTED");
/* 668*/        builder.appendSql(" or ");
/* 669*/        builder.appendParam("FState", "8VISA");
/* 670*/        builder.appendSql(" or ");
/* 671*/        builder.appendParam("FState", "7ANNOUNCE");
/* 672*/        builder.appendSql(" ) group by FContractBillID");
/* 673*/        IRowSet rs = builder.executeQuery();

/* 675*/        try
                {
/* <-MISALIGNED-> */ /* 675*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 676*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 677*/                BigDecimal changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /* 678*/                BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
/* <-MISALIGNED-> */ /* 679*/                changeMap.put(contractId, changeAmt);
/* <-MISALIGNED-> */ /* 680*/                changeMap.put((new StringBuilder()).append(contractId).append("ori").toString(), changeAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /* 682*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 683*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 684*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 685*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 687*/        return changeMap;
            }
            protected Map getChangeAmtOri(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {



/* 700*/        Map changeOriMap = new HashMap();
/* 701*/        if(conIds == null || conIds.size() < 0)
/* 702*/            return changeOriMap;

/* 704*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 705*/        builder.appendSql("select FContractBillID as contractId , sum(case fhasSettled when 0 then foriginalAmount else foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill where ");


/* 708*/        builder.appendParam("FContractBillID", conIds.toArray());
/* 709*/        builder.appendSql("  and (");
/* 710*/        builder.appendParam("FState", "4AUDITTED");
/* 711*/        builder.appendSql(" or ");
/* 712*/        builder.appendParam("FState", "8VISA");
/* 713*/        builder.appendSql(" or ");
/* 714*/        builder.appendParam("FState", "7ANNOUNCE");
/* 715*/        builder.appendSql(" ) group by FContractBillID");
/* 716*/        IRowSet rs = builder.executeQuery();

/* 718*/        try
                {
/* <-MISALIGNED-> */ /* 718*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 719*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 720*/                BigDecimal changeAmtOri = rs.getBigDecimal("changeAmtOri");
/* <-MISALIGNED-> */ /* 721*/                changeOriMap.put(contractId, changeAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /* 723*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 724*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 725*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 726*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 728*/        return changeOriMap;
            }
            protected BigDecimal getProcessAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {



/* 741*/        if(contractId.equals(""))
/* 742*/            return null;

/* 744*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);












/* 757*/        String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
/* 758*/        BigDecimal temp = null;


/* 761*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, "FDC317_SEPARATEFROMPAYMENT"))
                {/* 762*/            temp = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
                } else
                {/* 764*/            builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount  from t_con_payrequestbill where fid in  (  select distinct pay.ffdcpayreqid from t_cas_paymentbill pay  inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId  where ");




/* 769*/            builder.appendParam("pay.fbillstatus", new Integer(15));
/* 770*/            builder.appendSql(" and ");
/* 771*/            builder.appendParam("type.FPayTypeID", "Ga7RLQETEADgAAC6wKgOlOwp3Sw=");
/* 772*/            builder.appendSql(" and ");
/* 773*/            builder.appendParam("pay.fcontractbillid", contractId);
/* 774*/            builder.appendSql(" ) group by fcontractid");
/* 775*/            IRowSet rs = builder.executeQuery();


/* 778*/            try
                    {
/* <-MISALIGNED-> */ /* 778*/                while(rs.next()) 
/* <-MISALIGNED-> */ /* 779*/                    temp = rs.getBigDecimal("amount");
                    }
/* <-MISALIGNED-> */ /* 781*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 782*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 783*/                throw new BOSException(e);
                    }
                }
/* <-MISALIGNED-> */ /* 786*/        return temp;
            }
            protected Map getProcessAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {




/* 798*/        Map processAmt = new HashMap();
/* 799*/        if(conIds == null || conIds.size() < 0)
/* 800*/            return processAmt;

/* 802*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 803*/        String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();












/* 816*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, companyID, "FDC317_SEPARATEFROMPAYMENT"))
                {/* 817*/            processAmt = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(conIds);
                } else
                {/* 819*/            builder.appendSql(" select fcontractid as contractId, sum(FCompletePrjAmt) as amount  from t_con_payrequestbill where fid in  (  select distinct pay.ffdcpayreqid from t_cas_paymentbill pay  inner join T_FDC_PaymentType type on type.fid = pay.ffdcPayTypeId  where ");




/* 824*/            builder.appendParam("pay.fbillstatus", new Integer(15));
/* 825*/            builder.appendSql(" and ");
/* 826*/            builder.appendParam("type.FPayTypeID", "Ga7RLQETEADgAAC6wKgOlOwp3Sw=");
/* 827*/            builder.appendSql(" and  ");
/* 828*/            builder.appendParam("pay.fcontractbillid", conIds.toArray());
/* 829*/            builder.appendSql(" ) group by fcontractid");
/* 830*/            IRowSet rs = builder.executeQuery();

/* 832*/            try
                    {
/* <-MISALIGNED-> */ /* 832*/                do
                        {
/* <-MISALIGNED-> */ /* 832*/                    if(!rs.next())
/* <-MISALIGNED-> */ /* 833*/                        break;
/* <-MISALIGNED-> */ /* 833*/                    String conId = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 834*/                    BigDecimal temp = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /* 835*/                    if(!processAmt.containsKey(conId))
/* <-MISALIGNED-> */ /* 836*/                        processAmt.put(conId, temp);
                        } while(true);
                    }
/* <-MISALIGNED-> */ /* 839*/            catch(SQLException e)
                    {
/* <-MISALIGNED-> */ /* 840*/                e.printStackTrace();
/* <-MISALIGNED-> */ /* 841*/                throw new BOSException(e);
                    }
                }
/* <-MISALIGNED-> */ /* 844*/        return processAmt;
            }
            protected BigDecimal getPaidAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {
/* 855*/        if(contractId.equals(""))
/* 856*/            return null;

/* 858*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 859*/        builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount  from t_cas_paymentbill  where ");

/* 861*/        builder.appendParam("fbillstatus", new Integer(15));
/* 862*/        builder.appendSql(" and ");
/* 863*/        builder.appendParam("fcontractbillid", contractId);
/* 864*/        builder.appendSql(" group by FcontractBillId ");
/* 865*/        IRowSet rs = builder.executeQuery();
/* 866*/        BigDecimal temp = null;

/* 868*/        try
                {
/* <-MISALIGNED-> */ /* 868*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 869*/                temp = rs.getBigDecimal("amount");
                }
/* <-MISALIGNED-> */ /* 871*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 872*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 873*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 874*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 876*/        return temp;
            }
            protected BigDecimal getPaidAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {



/* 887*/        if(contractId.equals(""))
/* 888*/            return null;

/* 890*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 891*/        builder.appendSql("select FContractBillId as contractId , sum( FAmount) as amount  from t_cas_paymentbill  where ");

/* 893*/        builder.appendParam("fbillstatus", new Integer(15));
/* 894*/        builder.appendSql(" and ");
/* 895*/        builder.appendParam("fcontractbillid", contractId);
/* 896*/        builder.appendSql(" group by FcontractBillId ");
/* 897*/        IRowSet rs = builder.executeQuery();
/* 898*/        BigDecimal temp = null;

/* 900*/        try
                {
/* <-MISALIGNED-> */ /* 900*/            while(rs.next()) 
/* <-MISALIGNED-> */ /* 901*/                temp = rs.getBigDecimal("amount");
                }
/* <-MISALIGNED-> */ /* 903*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 904*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 905*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 906*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 908*/        return temp;
            }
            protected Map getPaidAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {



/* 919*/        Map paidAmt = new HashMap();
/* 920*/        if(conIds == null || conIds.size() < 0)
/* 921*/            return paidAmt;

/* 923*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 924*/        builder.appendSql("select FContractBillId as contractId , sum( FLocalAmount) as amount  ,sum(FAmount) as oriAmount from t_cas_paymentbill  where ");

/* 926*/        builder.appendParam("fbillstatus", new Integer(15));
/* 927*/        builder.appendSql(" and  ");
/* 928*/        builder.appendParam("fcontractbillid", conIds.toArray());
/* 929*/        builder.appendSql("  group by FcontractBillId ");
/* 930*/        IRowSet rs = builder.executeQuery();

/* 932*/        try
                {
/* <-MISALIGNED-> */ /* 932*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 933*/                String id = rs.getString("contractId");
/* <-MISALIGNED-> */ /* 934*/                BigDecimal temp = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /* 935*/                BigDecimal tempOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /* 936*/                paidAmt.put(id, temp);
/* <-MISALIGNED-> */ /* 937*/                paidAmt.put((new StringBuilder()).append(id).append("ori").toString(), tempOri);
                    }
                }
/* <-MISALIGNED-> */ /* 939*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 940*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 941*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 942*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 944*/        return paidAmt;
            }
            protected Map getOrtherAmt(Context ctx, String contractId)
                throws BOSException, EASBizException
            {



/* 957*/        Map ortherAmt = new HashMap();
/* 958*/        if(contractId == null || contractId.equals(""))
/* 959*/            return ortherAmt;

/* 961*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


/* 964*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
/* 965*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* 966*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* 967*/        builder.appendSql("where ");
/* 968*/        builder.appendParam("prb.FContractID", contractId);
/* 969*/        builder.appendSql(" and ");
/* 970*/        builder.appendParam(" doprb.FPasPaid", new Integer(1));
/* 971*/        builder.appendSql(" and ");
/* 972*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* 973*/        builder.appendSql(" group by prb.FContractID ");
/* 974*/        IRowSet rs = builder.executeQuery();

/* 976*/        try
                {
/* <-MISALIGNED-> */ /* 976*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /* 977*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /* 978*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /* 979*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /* 980*/                ortherAmt.put((new StringBuilder()).append(id).append("deductedAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /* 981*/                ortherAmt.put((new StringBuilder()).append(id).append("deductedAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /* 983*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 984*/            e.printStackTrace();
/* <-MISALIGNED-> */ /* 985*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /* 986*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /* 989*/        builder.clear();
/* <-MISALIGNED-> */ /* 990*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /* 991*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /* 992*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /* 993*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /* 994*/        builder.appendParam("prb.FContractID", contractId);
/* <-MISALIGNED-> */ /* 995*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /* 996*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /* 997*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /* 998*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /* 999*/        builder.appendSql(" group by prb.FContractID ");


/*1006*/        rs = builder.executeQuery();

/*1008*/        try
                {
/* <-MISALIGNED-> */ /*1008*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1009*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1010*/                BigDecimal compensatedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1011*/                BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1012*/                ortherAmt.put((new StringBuilder()).append(id).append("compensatedAmt").toString(), compensatedAmt);
/* <-MISALIGNED-> */ /*1013*/                ortherAmt.put((new StringBuilder()).append(id).append("compensatedAmtOri").toString(), compensatedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1015*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1016*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1017*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1018*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1021*/        builder.clear();
/* <-MISALIGNED-> */ /*1022*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1023*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1024*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1025*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1026*/        builder.appendParam("prb.FContractID", contractId);
/* <-MISALIGNED-> */ /*1027*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1028*/        builder.appendParam(" doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1029*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1030*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1031*/        builder.appendSql(" group by prb.FContractID ");
/* <-MISALIGNED-> */ /*1032*/        rs = builder.executeQuery();
/* <-MISALIGNED-> */ /*1034*/        try
                {
/* <-MISALIGNED-> */ /*1034*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1035*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1036*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1037*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1038*/                ortherAmt.put((new StringBuilder()).append(id).append("guerdonAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /*1039*/                ortherAmt.put((new StringBuilder()).append(id).append("guerdonAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1041*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1042*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1043*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1044*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1047*/        builder.clear();
/*1055*/        builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
/*1056*/        builder.appendParam("FContractId", contractId);
/*1057*/        builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
/*1058*/        builder.appendParam("fbillstatus", new Integer(15));
/*1059*/        builder.appendSql(" ) group by FContractID ");
/*1060*/        rs = builder.executeQuery();

/*1062*/        try
                {
/* <-MISALIGNED-> */ /*1062*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1063*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1064*/                BigDecimal invoicedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1065*/                ortherAmt.put((new StringBuilder()).append(id).append("invoicedAmt").toString(), invoicedAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1067*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1068*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1069*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1070*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1073*/        builder.clear();/*1077*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC305_CREATEPARTADEDUCT"))
                {
/*1079*/            builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt  from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");

/*1081*/            builder.appendParam("py.FContractBillID", contractId);
/*1082*/            builder.appendSql(" and ");
/*1083*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/*1084*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
                } else
                {/*1086*/            builder.appendSql("select sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as  partAMatlAmt  from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");

/*1088*/            builder.appendParam("py.FContractBillID", contractId);
/*1089*/            builder.appendSql(" and ");
/*1090*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/*1091*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
                }

/*1094*/        rs = builder.executeQuery();

/*1096*/        try
                {
/* <-MISALIGNED-> */ /*1096*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1097*/                BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
/* <-MISALIGNED-> */ /*1098*/                BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
/* <-MISALIGNED-> */ /*1099*/                ortherAmt.put((new StringBuilder()).append(contractId).append("partAMatlAmt").toString(), partAMatlAmt);
/* <-MISALIGNED-> */ /*1100*/                ortherAmt.put((new StringBuilder()).append(contractId).append("partAMatLoaAmt").toString(), partAMatLoaAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1102*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1103*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1104*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1106*/        return ortherAmt;
            }
            protected Map getOrtherAmt(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {

/*1117*/        Map ortherAmt = new HashMap();
/*1118*/        if(conIds == null || conIds.size() < 0)
/*1119*/            return ortherAmt;

/*1121*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


/*1124*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount,sum(doprb.foriginalAmount) as oriAmount from T_CON_DeductOfPayReqBill doprb ");
/*1125*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/*1126*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/*1127*/        builder.appendSql("where  ");
/*1128*/        builder.appendParam("prb.FContractID", conIds.toArray());
/*1129*/        builder.appendSql(" and ");
/*1130*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/*1131*/        builder.appendSql(" and ");
/*1132*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/*1133*/        builder.appendSql(" group by prb.FContractID ");
/*1134*/        IRowSet rs = builder.executeQuery();

/*1136*/        try
                {
/* <-MISALIGNED-> */ /*1136*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1137*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1138*/                BigDecimal deductedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1139*/                BigDecimal deductedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1140*/                ortherAmt.put((new StringBuilder()).append(id).append("deductedAmt").toString(), deductedAmt);
/* <-MISALIGNED-> */ /*1141*/                ortherAmt.put((new StringBuilder()).append(id).append("deductedAmtOri").toString(), deductedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1143*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1144*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1145*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1146*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1149*/        builder.clear();
/* <-MISALIGNED-> */ /*1150*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_CompensationOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1151*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1152*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1153*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1154*/        builder.appendParam("prb.FContractID", conIds.toArray());
/* <-MISALIGNED-> */ /*1155*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1156*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1157*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1158*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1159*/        builder.appendSql(" group by prb.FContractID ");


/*1166*/        rs = builder.executeQuery();

/*1168*/        try
                {
/* <-MISALIGNED-> */ /*1168*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1169*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1170*/                BigDecimal compensatedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1171*/                BigDecimal compensatedAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1172*/                ortherAmt.put((new StringBuilder()).append(id).append("compensatedAmt").toString(), compensatedAmt);
/* <-MISALIGNED-> */ /*1173*/                ortherAmt.put((new StringBuilder()).append(id).append("compensatedAmtOri").toString(), compensatedAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1175*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1176*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1177*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1178*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1181*/        builder.clear();
/* <-MISALIGNED-> */ /*1182*/        builder.appendSql("select prb.FContractID,sum(doprb.famount) as amount ,sum(doprb.foriginalAmount) as oriAmount from T_CON_GuerdonOfPayReqBill doprb ");
/* <-MISALIGNED-> */ /*1183*/        builder.appendSql("inner join T_CON_PayRequestBill prb on prb.fid = doprb.fpayRequestBillId ");
/* <-MISALIGNED-> */ /*1184*/        builder.appendSql("inner join T_CAS_PaymentBill pmb on pmb.FFdcPayReqID = prb.FID ");
/* <-MISALIGNED-> */ /*1185*/        builder.appendSql("where ");
/* <-MISALIGNED-> */ /*1186*/        builder.appendParam("prb.FContractID", conIds.toArray());
/* <-MISALIGNED-> */ /*1187*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1188*/        builder.appendParam("doprb.FPasPaid", new Integer(1));
/* <-MISALIGNED-> */ /*1189*/        builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1190*/        builder.appendParam(" pmb.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1191*/        builder.appendSql(" group by prb.FContractID ");
/* <-MISALIGNED-> */ /*1193*/        rs = builder.executeQuery();
/* <-MISALIGNED-> */ /*1195*/        try
                {
/* <-MISALIGNED-> */ /*1195*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1196*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1197*/                BigDecimal guerdonAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1198*/                BigDecimal guerdonAmtOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1199*/                ortherAmt.put((new StringBuilder()).append(id).append("guerdonAmt").toString(), guerdonAmt);
/* <-MISALIGNED-> */ /*1200*/                ortherAmt.put((new StringBuilder()).append(id).append("guerdonAmtOri").toString(), guerdonAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1202*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1203*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1204*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1205*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1208*/        builder.clear();

/*1216*/        builder.appendSql(" select fcontractid ,sum(finvoiceamt) as amount from t_con_payrequestbill where ");
/*1217*/        builder.appendParam("FContractId", conIds.toArray());
/*1218*/        builder.appendSql(" and fid in ( select ffdcpayreqid from t_cas_paymentbill where ");
/*1219*/        builder.appendParam("fbillstatus", new Integer(15));
/*1220*/        builder.appendSql(" ) group by FContractID ");
/*1221*/        rs = builder.executeQuery();

/*1223*/        try
                {
/* <-MISALIGNED-> */ /*1223*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1224*/                String id = rs.getString("FContractID");
/* <-MISALIGNED-> */ /*1225*/                BigDecimal invoicedAmt = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1226*/                ortherAmt.put((new StringBuilder()).append(id).append("invoicedAmt").toString(), invoicedAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1228*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1229*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1230*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1233*/        builder.clear();
/* <-MISALIGNED-> */ /*1235*/        if(FDCUtils.getDefaultFDCParamByKey(ctx, null, "FDC305_CREATEPARTADEDUCT"))
                {
/* <-MISALIGNED-> */ /*1237*/            builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt, sum(pa.FOriginalAmount) as  partAMatlAmt from T_CON_PartAOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
/* <-MISALIGNED-> */ /*1239*/            builder.appendParam("(py.FContractBillID", conIds.toArray());
/* <-MISALIGNED-> */ /*1240*/            builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1241*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1242*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
/* <-MISALIGNED-> */ /*1243*/            builder.appendSql(" ) group by py.fcontractbillid ");
                } else
                {
/* <-MISALIGNED-> */ /*1245*/            builder.appendSql("select py.fcontractbillid, sum(pa.FAMOUNT) as partAMatLoaAmt,sum(pa.FOriginalAmount) as partAMatlAmt  from T_CON_PartAConfmOfPayReqBill as pa,T_CAS_PaymentBill as py where ");
/* <-MISALIGNED-> */ /*1247*/            builder.appendParam("(py.FContractBillID", conIds.toArray());
/* <-MISALIGNED-> */ /*1248*/            builder.appendSql(" and ");
/* <-MISALIGNED-> */ /*1249*/            builder.appendParam(" py.FBillstatus", new Integer(15));
/* <-MISALIGNED-> */ /*1250*/            builder.appendSql(" and pa.FPaymentBillID=py.FID");
/* <-MISALIGNED-> */ /*1251*/            builder.appendSql(" ) group by py.fcontractbillid ");
                }
/* <-MISALIGNED-> */ /*1253*/        rs = builder.executeQuery();/*1255*/        try
                {
/* <-MISALIGNED-> */ /*1255*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1256*/                String contractId = rs.getString("fcontractbillid");
/* <-MISALIGNED-> */ /*1257*/                BigDecimal partAMatlAmt = rs.getBigDecimal("partAMatlAmt");
/* <-MISALIGNED-> */ /*1258*/                BigDecimal partAMatLoaAmt = rs.getBigDecimal("partAMatLoaAmt");
/* <-MISALIGNED-> */ /*1259*/                ortherAmt.put((new StringBuilder()).append(contractId).append("partAMatlAmt").toString(), partAMatlAmt);
/* <-MISALIGNED-> */ /*1260*/                ortherAmt.put((new StringBuilder()).append(contractId).append("partAMatLoaAmt").toString(), partAMatLoaAmt);
                    }
                }
/* <-MISALIGNED-> */ /*1262*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1263*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1264*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1266*/        return ortherAmt;
            }
            protected boolean hasPaySettle(Context ctx, String contractId, boolean hasSettled)
                throws BOSException, EASBizException
            {


/*1278*/        if(!hasSettled)
/*1279*/            return false;

/*1281*/        boolean hasPaySettle = false;
/*1282*/        EntityViewInfo viewInfo = new EntityViewInfo();
/*1283*/        viewInfo.getSelector().add("FdcPayType.payType.id");
/*1284*/        FilterInfo filterInfo = new FilterInfo();
/*1285*/        filterInfo.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));

/*1287*/        filterInfo.getFilterItems().add(new FilterItemInfo("contractBillId", contractId));

/*1289*/        viewInfo.setFilter(filterInfo);
/*1290*/        PaymentBillCollection pbc = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(viewInfo);


/*1293*/        if(pbc != null && pbc.size() > 0)
                {/*1294*/            int i = 0;/*1294*/            do
                    {
/* <-MISALIGNED-> */ /*1294*/                if(i >= pbc.size())
/* <-MISALIGNED-> */ /*1295*/                    break;
/* <-MISALIGNED-> */ /*1295*/                PaymentBillInfo pbi = pbc.get(i);
/* <-MISALIGNED-> */ /*1296*/                if(pbi != null && pbi.getFdcPayType() != null && pbi.getFdcPayType().getPayType() != null && pbi.getFdcPayType().getPayType().getId() != null && "Ga7RLQETEADgAAC/wKgOlOwp3Sw=".equals(pbi.getFdcPayType().getPayType().getId().toString()))
                        {


/*1303*/                    hasPaySettle = true;
/*1304*/                    break;
                        }
/* <-MISALIGNED-> */ /*1294*/                i++;
                    } while(true);
                }/*1308*/        return hasPaySettle;
            }
            protected Map hasPaySettle(Context ctx, Set conIds, boolean hasSettled)
                throws BOSException, EASBizException
            {







/*1320*/        Map hasPaySettleMap = new HashMap();
/*1321*/        if(!hasSettled)
/*1322*/            return hasPaySettleMap;



























/*1350*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1351*/        builder.appendSql("       select distinct(pay.fcontractbillid) as contractId from t_cas_paymentbill pay inner join \r\n");
/*1352*/        builder.appendSql("       t_fdc_paymenttype payType on payType.Fid = pay.ffdcpaytypeid \r\n");
/*1353*/        builder.appendSql("       where payType.Fpaytypeid = 'Ga7RLQETEADgAAC/wKgOlOwp3Sw=' and \r\n");
/*1354*/        builder.appendParam("pay.fcontractbillid", conIds.toArray());
/*1355*/        builder.appendSql("       and pay.fbillstatus = 15 \r\n");
/*1356*/        IRowSet rs = builder.executeQuery();

/*1358*/        try
                {
/* <-MISALIGNED-> */ /*1358*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1359*/                String contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /*1360*/                hasPaySettleMap.put(contractId, Boolean.TRUE);
                    }
                }
/* <-MISALIGNED-> */ /*1362*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1363*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1364*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1365*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1367*/        return hasPaySettleMap;
            }
            protected void _synOldContract(Context ctx)
                throws BOSException, EASBizException
            {
/* <-MISALIGNED-> */ /*1374*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);













/*1391*/        Set newConIds = new HashSet();

/*1393*/        builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID from T_CON_ContractBill where ");
/*1394*/        builder.appendParam("FState", "4AUDITTED");
/*1395*/        builder.appendSql(" and (fcontractpropert!='SUPPLY' or fisamtwithoutcost!=1 )");

/*1397*/        builder.appendSql(" and fid not in ( select FContractBillId from T_CON_ContractExecInfos ");

/*1399*/        builder.appendSql(" where FContractBillId is not null)");



/*1403*/        String insertSql = "insert into T_Con_ContractExecInfos (FID,FContractBillid,FCurProjectid,FCostAmt ,FCostAmtOri , FControlUnitID,FOrgUnitID,FPeriodID,FisNoText ) values (?,?,?,?,?,?,?,?,?)";


/*1406*/        List insertList = new ArrayList();

/*1408*/        try
                {
                    String id;
                    String contractId;
                    String curProjectId;
                    BigDecimal costAmt;
                    BigDecimal costAmtOri;
                    String controlUnitId;
                    String orgUnitId;
                    String periodId;
/* <-MISALIGNED-> */ /*1408*/            for(IRowSet rs = builder.executeQuery(); rs.next(); insertList.add(Arrays.asList(new Object[] {

/*1420*/    id, contractId, curProjectId, costAmt, costAmtOri, controlUnitId, orgUnitId, periodId, new Integer(0)
})))
                    {
/* <-MISALIGNED-> */ /*1410*/                ContractExecInfosInfo info = new ContractExecInfosInfo();
/* <-MISALIGNED-> */ /*1411*/                id = BOSUuid.create(info.getBOSType()).toString();
/* <-MISALIGNED-> */ /*1412*/                contractId = rs.getString("fid");
/* <-MISALIGNED-> */ /*1413*/                newConIds.add(contractId);
/* <-MISALIGNED-> */ /*1414*/                curProjectId = rs.getString("fcurprojectid").toString();
/* <-MISALIGNED-> */ /*1415*/                costAmt = rs.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1416*/                costAmtOri = rs.getBigDecimal("foriginalAmount");
/* <-MISALIGNED-> */ /*1417*/                controlUnitId = rs.getString("FControlUnitID");
/* <-MISALIGNED-> */ /*1418*/                orgUnitId = rs.getString("FOrgUnitID");
/* <-MISALIGNED-> */ /*1419*/                periodId = rs.getString("FPeriodID");
                    }
/* <-MISALIGNED-> */ /*1424*/            builder.executeBatch(insertSql, insertList);
                }
/* <-MISALIGNED-> */ /*1425*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1426*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1427*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1430*/        if(newConIds != null && newConIds.size() > 0)
                {
/* <-MISALIGNED-> */ /*1431*/            List list = new ArrayList();
/* <-MISALIGNED-> */ /*1432*/            if(newConIds.size() > 500)
                    {
/* <-MISALIGNED-> */ /*1433*/                int i = 0;
/* <-MISALIGNED-> */ /*1434*/                Set mySet = new HashSet();
/* <-MISALIGNED-> */ /*1435*/                for(Iterator iter = newConIds.iterator(); iter.hasNext();)
                        {
/* <-MISALIGNED-> */ /*1436*/                    if(i % 500 == 0)
                            {
/* <-MISALIGNED-> */ /*1437*/                        list.add(mySet);
/* <-MISALIGNED-> */ /*1438*/                        mySet = new HashSet();
                            }
/* <-MISALIGNED-> */ /*1440*/                    mySet.add(iter.next());
/* <-MISALIGNED-> */ /*1435*/                    i++;
                        }
/* <-MISALIGNED-> */ /*1442*/                list.add(mySet);
                    } else
                    {
/* <-MISALIGNED-> */ /*1444*/                list.add(newConIds);
                    }
/* <-MISALIGNED-> */ /*1447*/            for(int i = 0; i < list.size(); i++)
                    {
/* <-MISALIGNED-> */ /*1449*/                newConIds = (Set)list.get(i);
/* <-MISALIGNED-> */ /*1450*/                if(newConIds.size() == 0)
/* <-MISALIGNED-> */ /*1451*/                    continue;
/* <-MISALIGNED-> */ /*1454*/                builder.clear();
/* <-MISALIGNED-> */ /*1455*/                String updateChangeSql = " update T_CON_ContractExecInfos set FChangeAmt = ? ,FChangeAmtOri = ?, FCostAmt = ?, FCostAmtOri = ? where FContractBillId = ?";
/* <-MISALIGNED-> */ /*1457*/                List updateChangeList = new ArrayList();
/* <-MISALIGNED-> */ /*1459*/                builder.appendSql("select con.FID as contractId,con.FAmount as amount ,con.FOriginalAmount as oriAmount , sum(case conChange.fhasSettled when  0 then  conChange.famount  else conChange.fbalanceamount end ) as changeAmt , sum(case conChange.fhasSettled when 0 then conChange.foriginalAmount else conChange.foriBalanceAmount end) as changeAmtOri from T_Con_ContractChangeBill conChange inner join T_CON_ContractBill con on  con.FID = conChange.FContractBillID where ");
/* <-MISALIGNED-> */ /*1465*/                builder.appendParam("con.FID", newConIds.toArray());
/* <-MISALIGNED-> */ /*1466*/                builder.appendSql(" and ( ");
/* <-MISALIGNED-> */ /*1467*/                builder.appendParam("conChange.FState", "4AUDITTED");
/* <-MISALIGNED-> */ /*1468*/                builder.appendSql(" or ");
/* <-MISALIGNED-> */ /*1469*/                builder.appendParam("conChange.FState", "8VISA");
/* <-MISALIGNED-> */ /*1470*/                builder.appendSql(" or ");
/* <-MISALIGNED-> */ /*1471*/                builder.appendParam("conChange.FState", "7ANNOUNCE");
/* <-MISALIGNED-> */ /*1472*/                builder.appendSql(" ) ");
/* <-MISALIGNED-> */ /*1473*/                builder.appendSql(" group by con.FID,con.FAmount,con.FOriginalAmount ");
                        IRowSet rs;
/* <-MISALIGNED-> */ /*1475*/                try
                        {
                            String contractId;
                            BigDecimal amount;
                            BigDecimal amountOri;
                            BigDecimal changeAmt;
                            BigDecimal changeAmtOri;
/* <-MISALIGNED-> */ /*1475*/                    for(rs = builder.executeQuery(); rs.next(); updateChangeList.add(Arrays.asList(new Object[] {
/* <-MISALIGNED-> */ /*1482*/    changeAmt, changeAmtOri, FDCHelper.add(changeAmt, amount), FDCHelper.add(changeAmtOri, amountOri), contractId
})))
                            {
/* <-MISALIGNED-> */ /*1477*/                        contractId = rs.getString("contractId");
/* <-MISALIGNED-> */ /*1478*/                        amount = rs.getBigDecimal("amount");
/* <-MISALIGNED-> */ /*1479*/                        amountOri = rs.getBigDecimal("oriAmount");
/* <-MISALIGNED-> */ /*1480*/                        changeAmt = rs.getBigDecimal("changeAmt");
/* <-MISALIGNED-> */ /*1481*/                        changeAmtOri = rs.getBigDecimal("changeAmtOri");
                            }








/*1507*/                    builder.executeBatch(updateChangeSql, updateChangeList);
                        }/*1508*/                catch(SQLException e)
                        {/*1509*/                    e.printStackTrace();
/*1510*/                    throw new BOSException(e);
                        }





/*1517*/                builder.clear();
/*1518*/                String updateSettleSql = " update T_CON_ContractExecInfos set FCostAmt = ? ,FCostAmtOri = ?, FSettleAmt = ? , FSettleAmtOri =? , FHasSettled = ? , FCompleteAmt = ? ,FNotCompletedAmt = ? ,FDeductedAmt = ? , FDeductedAmtOri = ? , FCompensatedAmt = ?, FCompensatedAmtOri = ?, FGuerdonAmt = ?, FGuerdonAmtOri = ? ,FInvoicedAmt = ? , FPaidAmt = ? ,FPaidAmtOri = ?,FpartAMatlAmt=?, FpartAMatLoaAmt=? where FContractBillId = ? ";



/*1522*/                List updateSettleList = new ArrayList();

/*1524*/                Map processAmtMap = getProcessAmt(ctx, newConIds);

/*1526*/                Map hasPaySettleMap = hasPaySettle(ctx, newConIds, true);

/*1528*/                Map changeAmtMap = getChangeAmt(ctx, newConIds);

/*1530*/                Map settleMap = getSettleAmtOri(ctx, newConIds);

/*1532*/                Map ortherMap = getOrtherAmt(ctx, newConIds);

/*1534*/                Map paidMap = getPaidAmt(ctx, newConIds);
/*1535*/                builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount,FOriginalAmount from T_CON_ContractBill where ");
/*1536*/                builder.appendParam("fid", newConIds.toArray());


/*1539*/                rs = builder.executeQuery();

/*1541*/                try
                        {
/* <-MISALIGNED-> */ /*1541*/                    while(rs.next()) 
                            {
/* <-MISALIGNED-> */ /*1543*/                        String id = rs.getString("FID");
/* <-MISALIGNED-> */ /*1544*/                        boolean hasSettled = rs.getBoolean("FHasSettled");
/* <-MISALIGNED-> */ /*1545*/                        boolean hasPaySettle = false;
/* <-MISALIGNED-> */ /*1546*/                        if(hasPaySettleMap != null && hasPaySettleMap.containsKey(id))
/* <-MISALIGNED-> */ /*1547*/                            hasPaySettle = ((Boolean)hasPaySettleMap.get(id)).booleanValue();/*1549*/                        Integer hasSett = new Integer(0);

/*1551*/                        BigDecimal settleAmt = rs.getBigDecimal("FSettleAmt");

/*1553*/                        BigDecimal settleAmtOri = FDCHelper.ZERO;

/*1555*/                        BigDecimal processAmt = FDCHelper.ZERO;

/*1557*/                        BigDecimal completeAmt = FDCHelper.ZERO;

/*1559*/                        BigDecimal notCompleteAmt = FDCHelper.ZERO;

/*1561*/                        BigDecimal costAmt = FDCHelper.ZERO;

/*1563*/                        BigDecimal costAmtOri = FDCHelper.ZERO;

/*1565*/                        BigDecimal deductedAmt = FDCHelper.ZERO;

/*1567*/                        BigDecimal deductedAmtOri = FDCHelper.ZERO;

/*1569*/                        BigDecimal compensatedAmt = FDCHelper.ZERO;

/*1571*/                        BigDecimal compensatedAmtOri = FDCHelper.ZERO;

/*1573*/                        BigDecimal guerdonAmt = FDCHelper.ZERO;

/*1575*/                        BigDecimal guerdonAmtOri = FDCHelper.ZERO;

/*1577*/                        BigDecimal invoicedAmt = FDCHelper.ZERO;

/*1579*/                        BigDecimal paidAmt = FDCHelper.ZERO;

/*1581*/                        BigDecimal paidAmtOri = FDCHelper.ZERO;

/*1583*/                        BigDecimal partAMatlAmt = FDCHelper.ZERO;

/*1585*/                        BigDecimal partAMatLoaAmt = FDCHelper.ZERO;
/*1586*/                        if(settleMap != null && settleMap.containsKey(id))
/*1587*/                            settleAmtOri = (BigDecimal)settleMap.get((new StringBuilder()).append(id).append("ori").toString());

/*1589*/                        if(ortherMap != null)
                                {/*1590*/                            deductedAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("deductedAmt").toString());
/*1591*/                            deductedAmtOri = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("deductedAmtOri").toString());
/*1592*/                            compensatedAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("compensatedAmt").toString());
/*1593*/                            compensatedAmtOri = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("compensatedAmtOri").toString());
/*1594*/                            guerdonAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("guerdonAmt").toString());
/*1595*/                            guerdonAmtOri = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("guerdonAmtOri").toString());
/*1596*/                            invoicedAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("invoicedAmt").toString());
/*1597*/                            partAMatlAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("partAMatlAmt").toString());
/*1598*/                            partAMatLoaAmt = (BigDecimal)ortherMap.get((new StringBuilder()).append(id).append("partAMatLoaAmt").toString());
                                }
/*1600*/                        if(paidMap != null && paidMap.containsKey(id))
                                {/*1601*/                            paidAmt = (BigDecimal)paidMap.get(id);
/*1602*/                            paidAmtOri = (BigDecimal)paidMap.get((new StringBuilder()).append(id).append("ori").toString());
                                }
/*1604*/                        if(processAmtMap != null && processAmtMap.containsKey(id))
/*1605*/                            processAmt = (BigDecimal)processAmtMap.get(id);


/*1608*/                        if(hasSettled)
                                {/*1609*/                            hasSett = new Integer(1);

/*1611*/                            if(hasPaySettle)
                                    {/*1612*/                                costAmt = settleAmt;
/*1613*/                                costAmtOri = settleAmtOri;
/*1614*/                                completeAmt = settleAmt;
/*1615*/                                notCompleteAmt = FDCHelper.ZERO;
                                    } else
                                    {

/*1619*/                                costAmt = settleAmt;
/*1620*/                                costAmtOri = settleAmtOri;
/*1621*/                                completeAmt = processAmt;
/*1622*/                                notCompleteAmt = FDCHelper.subtract(settleAmt, processAmt);
                                    }
/*1624*/                            continue;
                                }

/*1627*/                        BigDecimal amount = rs.getBigDecimal("FAmount");
/*1628*/                        BigDecimal amountOri = rs.getBigDecimal("FOriginalAmount");
/*1629*/                        BigDecimal changeAmt = FDCHelper.ZERO;
/*1630*/                        BigDecimal changeAmtOri = FDCHelper.ZERO;
/*1631*/                        if(changeAmtMap != null && changeAmtMap.containsKey(id))
                                {/*1632*/                            changeAmt = (BigDecimal)changeAmtMap.get(id);
/*1633*/                            changeAmtOri = (BigDecimal)changeAmtMap.get((new StringBuilder()).append(id).append("ori").toString());
                                }
/*1635*/                        costAmt = FDCHelper.add(amount, changeAmt);
/*1636*/                        costAmtOri = FDCHelper.add(amountOri, changeAmtOri);
/*1637*/                        settleAmt = FDCHelper.ZERO;
/*1638*/                        completeAmt = processAmt;
/*1639*/                        notCompleteAmt = FDCHelper.subtract(costAmt, processAmt);

/*1641*/                        updateSettleList.add(Arrays.asList(new Object[] {/*1641*/                            costAmt, costAmtOri, settleAmt, settleAmtOri, hasSett, completeAmt, notCompleteAmt, deductedAmt, deductedAmtOri, compensatedAmt, /*1641*/                            compensatedAmtOri, guerdonAmt, guerdonAmtOri, invoicedAmt, paidAmt, paidAmtOri, partAMatlAmt, partAMatLoaAmt, id
                                }));
                            }

/*1645*/                    builder.executeBatch(updateSettleSql, updateSettleList);



/*1649*/                    continue;
                        }
/* <-MISALIGNED-> */ /*1646*/                catch(SQLException e)
                        {
/* <-MISALIGNED-> */ /*1647*/                    e.printStackTrace();
/* <-MISALIGNED-> */ /*1648*/                    throw new BOSException(e);
                        }
                    }
                }

/*1659*/        synOldNoTextContract(ctx);
            }
            protected BigDecimal getSettleAmtOri(Context ctx, String contractId)
                throws BOSException, EASBizException
            {






/*1670*/        BigDecimal settlePriceOri = FDCHelper.ZERO;
/*1671*/        if(contractId == null || "".equals(contractId))
/*1672*/            return settlePriceOri;
/*1673*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1674*/        builder.appendSql(" select fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
/*1675*/        builder.appendParam("fcontractbillid", contractId);
/*1676*/        IRowSet rs = builder.executeQuery();

/*1678*/        try
                {
/* <-MISALIGNED-> */ /*1678*/            while(rs.next()) 
/* <-MISALIGNED-> */ /*1679*/                settlePriceOri = rs.getBigDecimal("foriginalamount");
                }
/* <-MISALIGNED-> */ /*1681*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1682*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1683*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1684*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1686*/        return settlePriceOri;
            }
            protected Map getSettleAmtOri(Context ctx, Set conIds)
                throws BOSException, EASBizException
            {




/*1698*/        Map settleAmtOriMap = new HashMap();
/*1699*/        if(conIds == null || conIds.size() <= 0)
/*1700*/            return settleAmtOriMap;
/*1701*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/*1702*/        builder.appendSql(" select fcontractbillid, fsettleprice,foriginalamount from t_con_contractsettlementbill where fissettled = 1 and ");
/*1703*/        builder.appendParam("fcontractbillid", conIds.toArray());
/*1704*/        IRowSet rs = builder.executeQuery();

/*1706*/        try
                {
/* <-MISALIGNED-> */ /*1706*/            while(rs.next()) 
                    {
/* <-MISALIGNED-> */ /*1707*/                String contractId = rs.getString("FContractBillID");
/* <-MISALIGNED-> */ /*1708*/                BigDecimal settleAmt = rs.getBigDecimal("fsettleprice");
/* <-MISALIGNED-> */ /*1709*/                BigDecimal settleAmtOri = rs.getBigDecimal("foriginalamount");
/* <-MISALIGNED-> */ /*1710*/                settleAmtOriMap.put(contractId, settleAmt);
/* <-MISALIGNED-> */ /*1711*/                settleAmtOriMap.put((new StringBuilder()).append(contractId).append("ori").toString(), settleAmtOri);
                    }
                }
/* <-MISALIGNED-> */ /*1713*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1714*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1715*/            logger.error(e.getMessage());
/* <-MISALIGNED-> */ /*1716*/            throw new BOSException(e);
                }
/* <-MISALIGNED-> */ /*1718*/        return settleAmtOriMap;
            }
            private boolean isContract(String billID)
            {

/*1728*/        boolean isContract = false;
/*1729*/        BOSObjectType conType = BOSUuid.read(billID).getType();
/*1730*/        if(conType.equals(new BOSObjectType("0D6DD1F4")))
/*1731*/            isContract = true;
/*1732*/        else/*1732*/        if(conType.equals(new BOSObjectType("3D9A5388")))
/*1733*/            isContract = false;
/*1734*/        return isContract;
            }
            protected void _updateSuppliedContract(Context ctx, String type, String contractId)
                throws BOSException, EASBizException
            {






/*1745*/        if(contractId == null)
                {
/* <-MISALIGNED-> */ /*1745*/            return;
                } else
                {
/* <-MISALIGNED-> */ /*1746*/            FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /*1747*/            builder.appendSql("update T_CON_ContractExecInfos set (FCostAmt,FCostAmtOri,Fnotcompletedamt)=(select c.famount,c.fOriginalAmount,c.famount-i.fcompleteamt from t_con_contractbill c,t_con_contractexecinfos i  where c.fid=? and c.fid=i.fcontractbillid)  where FContractBillId=?");/*1751*/            builder.addParam(contractId);
/*1752*/            builder.addParam(contractId);
/*1753*/            builder.executeUpdate(ctx);
/*1754*/            return;
                }
            }
            private void synOldNoTextContract(Context ctx)
                throws BOSException
            {
/* <-MISALIGNED-> */ /*1756*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* <-MISALIGNED-> */ /*1758*/        List newNoTextConIds = new ArrayList();
/* <-MISALIGNED-> */ /*1760*/        builder.appendSql(" select fid, fcurprojectid ,famount,foriginalAmount, FControlUnitID,FOrgUnitID,FPeriodID,FInvoiceAmt from T_CON_ContractWithoutText where ");
/* <-MISALIGNED-> */ /*1761*/        builder.appendParam("FState", "4AUDITTED");
/* <-MISALIGNED-> */ /*1762*/        builder.appendSql(" and fid not in ( select FConWithoutTextID from T_CON_ContractExecInfos ");/*1764*/        builder.appendSql(" where FConWithoutTextID is not null)");


/*1767*/        String insertSQL = "insert into T_Con_ContractExecInfos (FID,FConWithoutTextID,FCurProjectid,FAmount,FOriginalAmount,FCostAmt ,FCostAmtOri , FControlUnitID,FOrgUnitID,FPeriodID,FisNoText,FCompleteAmt,FInvoicedAmt ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";


/*1770*/        List insertList = new ArrayList();

/*1772*/        try
                {
                    String id;
                    String contractId;
                    String curProjectId;
                    BigDecimal costAmt;
                    BigDecimal costAmtOri;
                    BigDecimal invoiceAmt;
                    String controlUnitId;
                    String orgUnitId;
                    String periodId;
/* <-MISALIGNED-> */ /*1772*/            for(IRowSet rs = builder.executeQuery(); rs.next(); insertList.add(Arrays.asList(new Object[] {

/*1785*/    id, contractId, curProjectId, costAmt, costAmtOri, costAmt, costAmtOri, controlUnitId, orgUnitId, periodId, /*1785*/    new Integer(1), costAmt, invoiceAmt
})))
                    {
/* <-MISALIGNED-> */ /*1774*/                ContractExecInfosInfo info = new ContractExecInfosInfo();
/* <-MISALIGNED-> */ /*1775*/                id = BOSUuid.create(info.getBOSType()).toString();
/* <-MISALIGNED-> */ /*1776*/                contractId = rs.getString("fid");
/* <-MISALIGNED-> */ /*1777*/                newNoTextConIds.add(contractId);
/* <-MISALIGNED-> */ /*1778*/                curProjectId = rs.getString("fcurprojectid").toString();
/* <-MISALIGNED-> */ /*1779*/                costAmt = rs.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1780*/                costAmtOri = rs.getBigDecimal("foriginalAmount");
/* <-MISALIGNED-> */ /*1781*/                invoiceAmt = rs.getBigDecimal("FInvoiceAmt");
/* <-MISALIGNED-> */ /*1782*/                controlUnitId = rs.getString("FControlUnitID");
/* <-MISALIGNED-> */ /*1783*/                orgUnitId = rs.getString("FOrgUnitID");
/* <-MISALIGNED-> */ /*1784*/                periodId = rs.getString("FPeriodID");
                    }
/* <-MISALIGNED-> */ /*1788*/            builder.executeBatch(insertSQL, insertList);
/* <-MISALIGNED-> */ /*1790*/            builder.clear();
/* <-MISALIGNED-> */ /*1794*/            String updateSQL = "update t_con_contractexecinfos set FPaidAmt=?,FPaidAmtOri=? where FConWithoutTextID=?";
/* <-MISALIGNED-> */ /*1795*/            List updatePaiedList = new ArrayList();
/* <-MISALIGNED-> */ /*1796*/            if(newNoTextConIds != null && newNoTextConIds.size() > 0)
                    {
/* <-MISALIGNED-> */ /*1797*/                for(int i = 0; i < newNoTextConIds.size(); i++)
                        {
/* <-MISALIGNED-> */ /*1798*/                    String noTextContractId = newNoTextConIds.get(i).toString();
/* <-MISALIGNED-> */ /*1800*/                    builder.clear();
/* <-MISALIGNED-> */ /*1801*/                    builder.appendSql("select fcontractbillid,famount,flocalamount from t_cas_paymentbill where fbillstatus=? and fcontractbillid=?");
/* <-MISALIGNED-> */ /*1802*/                    builder.addParam(new Integer(15));
/* <-MISALIGNED-> */ /*1803*/                    builder.addParam(noTextContractId);
                            BigDecimal paiedAmt;
                            BigDecimal paiedOriAmt;
/* <-MISALIGNED-> */ /*1804*/                    for(IRowSet rs1 = builder.executeQuery(); rs1.next(); updatePaiedList.add(Arrays.asList(new Object[] {
/* <-MISALIGNED-> */ /*1809*/    paiedAmt, paiedOriAmt, noTextContractId
})))
                            {
/* <-MISALIGNED-> */ /*1806*/                        noTextContractId = rs1.getString("fcontractbillid");
/* <-MISALIGNED-> */ /*1807*/                        paiedAmt = rs1.getBigDecimal("famount");
/* <-MISALIGNED-> */ /*1808*/                        paiedOriAmt = rs1.getBigDecimal("flocalamount");
                            }
                        }
/* <-MISALIGNED-> */ /*1812*/                builder.executeBatch(updateSQL, updatePaiedList);
                    }
                }
/* <-MISALIGNED-> */ /*1814*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /*1815*/            e.printStackTrace();
/* <-MISALIGNED-> */ /*1816*/            throw new BOSException(e);
                }
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractExecInfosControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws7\dh7\lib\server\eas\fdc_contract-server.jar
	Total time: 1046 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/