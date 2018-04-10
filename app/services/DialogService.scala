package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.{DialogDao, UserDao}
import db.Tables.{AttachmentRow, DialogRow, UserRow}
import playUtils._
import db.Db._



@Singleton
class DialogService @Inject()(dialogDao: DialogDao) {

  def getAllDialogsByUserMember(user: UserRow): Seq[DialogRow] = {
    dialogDao.getByUser(user)
  }

  def getAllDialogsByUserAndAttachment(userRow: UserRow, attachment: AttachmentRow): Seq[DialogRow] = {
    dialogDao.getByUserAndAttachment(attachment, userRow)
  }

}
