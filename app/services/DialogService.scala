package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.{DialogDao, UserDao}
import db.Tables.{AttachmentRow, DialogRow, UserRow}
import playUtils._
import db.Db._
import db.Tables._
import db.Tables.profile.api._



@Singleton
class DialogService @Inject()(dialogDao: DialogDao) {

  def getAllDialogsByUserMember(user: UserRow): Seq[DialogRow] = {
    dialogDao.getByUser(user)
  }

  def getAllDialogsByUserAndAttachment(userRow: UserRow, attachment: AttachmentRow): Seq[DialogRow] = {
    dialogDao.getByUserAndAttachment(attachment, userRow)
  }

  def getDialogByAttachment(attachmentRow: AttachmentRow): Option[DialogRow] = {
    dialogDao.getByAttachment(attachmentRow).result.headOption.get()
  }

  def create(name: String, admin: UserRow, members: Seq[Long]): Unit = {
    dialogDao.create(name, admin, members)
  }

  def getAllMessages(dialogId: Long): Seq[(UserRow, String)] = {
    dialogDao.getAllMessages(dialogId)
  }

}
