/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class PayRequestViewPayDetailUI extends AbstractPayRequestViewPayDetailUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3550410046609038209L;
	private static final Logger logger = CoreUIObject.getLogger(PayRequestViewPayDetailUI.class);
    
    /**
     * output class constructor
     */
    public PayRequestViewPayDetailUI() throws Exception
    {
        super();
    }

    
    protected boolean isIgnoreCUFilter() {
        return true;
    }

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected void beforeExcutQuery(EntityViewInfo ev) {
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
		
		String contractId = getUIContext().get("contractId").toString();
		Timestamp createTime = (Timestamp)getUIContext().get("createTime");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", contractId);
		filter.getFilterItems().add(
				new FilterItemInfo("createTime", createTime,
						CompareType.LESS));
		view.getSelector().add("entrys.paymentBill.id");
		PayRequestBillCollection c;
		Set ids = new HashSet();
		try {
			c = PayRequestBillFactory.getRemoteInstance()
					.getPayRequestBillCollection(view);
			if(c.size()>0){
				for(Iterator it=c.iterator();it.hasNext();){
					PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
					if(payRequestInfo.getEntrys().size()>0){
						for(int i=0;i<payRequestInfo.getEntrys().size();i++){
							ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId());
						}
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		}
		
		
		filter = new FilterInfo();
		
		if(ids.size()>0)
			filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
		else
			filter.getFilterItems().add(
					new FilterItemInfo("id", null));
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
		
		
		ev.setFilter(filter);
		super.beforeExcutQuery(ev);
	}

	protected String getKeyFieldName() {
		return "";
	}
	protected String getSelectedKeyValue(){
		return "";
	}

	public void onLoad() throws Exception {
		
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				appendFootRow();
			}
			
		});
		super.onLoad();
	}
	

	protected IRow appendFootRow() {
		tblMain.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		IRow footRow = FDCTableHelper.generateFootRow(tblMain);
        BigDecimal sumAmount = FDCHelper.ZERO;
        for(int i=0;i<tblMain.getRowCount();i++){
        	BigDecimal add = FDCHelper.ZERO;
        	if(tblMain.getRow(i).getCell("amount").getValue() instanceof BigDecimal)
        		add = (BigDecimal)tblMain.getRow(i).getCell("amount").getValue();
        	sumAmount = sumAmount.add(add);
        }
        String colFormat = "%{0.##########}f";        
        
    	ICell cell = footRow.getCell("amount");
		cell.getStyleAttributes().setNumberFormat(colFormat);
		cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		cell.getStyleAttributes().setFontColor(Color.BLACK);

        cell.setValue(sumAmount);
        footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
        FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"amount"});
        return footRow;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionView.setVisible(false);
		
		this.actionRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
		
		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionLocate.setEnabled(false);
		this.actionLocate.setVisible(false);
		
		this.actionQuery.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.menuBar.setVisible(false);
		this.toolBar.setVisible(true);
	}

	protected boolean isFootVisible() {
		return true;
	}
}