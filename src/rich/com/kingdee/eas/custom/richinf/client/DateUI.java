package com.kingdee.eas.custom.richinf.client;

import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.custom.richfacade.EASRichFacadeFactory;
import com.kingdee.eas.custom.richfacade.IEASRichFacade;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.event.ActionEvent;
import java.util.Date;
import org.apache.log4j.Logger;

public class DateUI extends AbstractDateUI
{
  private static final Logger logger = CoreUIObject.getLogger(DateUI.class);

  public DateUI()
    throws Exception
  {
  }

  public void onShow()
    throws Exception
  {
    super.onShow();
    this.btnAddNew.setVisible(false);
    this.btnEdit.setVisible(false);
    this.btnSave.setVisible(false);
    this.btnCopy.setVisible(false);
    this.btnRemove.setVisible(false);
    this.toolBar.removeAll();
  }

  protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
    super.btnNo_actionPerformed(e);
    destroyWindow();
  }
  protected void btnSure_actionPerformed(ActionEvent e) throws Exception {
    super.btnSure_actionPerformed(e);
    Date rptSetDate = this.kDDatePicker1.getSqlDate();
    if (rptSetDate == null) {
      MsgBox.showWarning("请选择业务日期！");
      this.kDDatePicker1.requestFocusInWindow();
      return;
    }
    String[] str = new String[3];
    str = EASRichFacadeFactory.getRemoteInstance().saveExamBill(rptSetDate, "");
    MsgBox.showInfo(str[0] + str[1] + str[2]);
    destroyWindow();
  }

  public void storeFields()
  {
    super.storeFields();
  }

  public void actionPageSetup_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPageSetup_actionPerformed(e);
  }

  public void actionExitCurrent_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExitCurrent_actionPerformed(e);
  }

  public void actionHelp_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionHelp_actionPerformed(e);
  }

  public void actionAbout_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionAbout_actionPerformed(e);
  }

  public void actionOnLoad_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionOnLoad_actionPerformed(e);
  }

  public void actionSendMessage_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionSendMessage_actionPerformed(e);
  }

  public void actionCalculator_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCalculator_actionPerformed(e);
  }

  public void actionExport_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExport_actionPerformed(e);
  }

  public void actionExportSelected_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExportSelected_actionPerformed(e);
  }

  public void actionRegProduct_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionRegProduct_actionPerformed(e);
  }

  public void actionPersonalSite_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPersonalSite_actionPerformed(e);
  }

  public void actionProcductVal_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionProcductVal_actionPerformed(e);
  }

  public void actionExportSave_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExportSave_actionPerformed(e);
  }

  public void actionExportSelectedSave_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExportSelectedSave_actionPerformed(e);
  }

  public void actionKnowStore_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionKnowStore_actionPerformed(e);
  }

  public void actionAnswer_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionAnswer_actionPerformed(e);
  }

  public void actionRemoteAssist_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionRemoteAssist_actionPerformed(e);
  }

  public void actionPopupCopy_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPopupCopy_actionPerformed(e);
  }

  public void actionHTMLForMail_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionHTMLForMail_actionPerformed(e);
  }

  public void actionExcelForMail_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExcelForMail_actionPerformed(e);
  }

  public void actionHTMLForRpt_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionHTMLForRpt_actionPerformed(e);
  }

  public void actionExcelForRpt_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionExcelForRpt_actionPerformed(e);
  }

  public void actionLinkForRpt_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionLinkForRpt_actionPerformed(e);
  }

  public void actionPopupPaste_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPopupPaste_actionPerformed(e);
  }

  public void actionToolBarCustom_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionToolBarCustom_actionPerformed(e);
  }

  public void actionCloudFeed_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCloudFeed_actionPerformed(e);
  }

  public void actionCloudShare_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCloudShare_actionPerformed(e);
  }

  public void actionCloudScreen_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCloudScreen_actionPerformed(e);
  }

  public void actionSave_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionSave_actionPerformed(e);
  }

  public void actionSubmit_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionSubmit_actionPerformed(e);
  }

  public void actionCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCancel_actionPerformed(e);
  }

  public void actionCancelCancel_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCancelCancel_actionPerformed(e);
  }

  public void actionFirst_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionFirst_actionPerformed(e);
  }

  public void actionPre_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPre_actionPerformed(e);
  }

  public void actionNext_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionNext_actionPerformed(e);
  }

  public void actionLast_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionLast_actionPerformed(e);
  }

  public void actionPrint_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPrint_actionPerformed(e);
  }

  public void actionPrintPreview_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionPrintPreview_actionPerformed(e);
  }

  public void actionCopy_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionCopy_actionPerformed(e);
  }

  public void actionAddNew_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionAddNew_actionPerformed(e);
  }

  public void actionEdit_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionEdit_actionPerformed(e);
  }

  public void actionRemove_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionRemove_actionPerformed(e);
  }

  public void actionAttachment_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionAttachment_actionPerformed(e);
  }

  public void actionSubmitOption_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionSubmitOption_actionPerformed(e);
  }

  public void actionReset_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionReset_actionPerformed(e);
  }

  public void actionMsgFormat_actionPerformed(ActionEvent e)
    throws Exception
  {
    super.actionMsgFormat_actionPerformed(e);
  }

  protected IObjectValue createNewData()
  {
    return null;
  }

  protected ICoreBase getBizInterface()
    throws Exception
  {
    return null;
  }
}