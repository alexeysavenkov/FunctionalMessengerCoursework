package db.dao

import javax.inject.{Inject, Singleton}

import db.Tables.profile.api._
import db.Tables.{Attachment, AttachmentRow, Dialog, DialogRow, UserRow, Userdialog, UserdialogRow}
import db.Db._
import _root_.db.Tables
import playUtils.{NotFound, PlayError}

@Singleton
class DialogDao @Inject()(messageDao: MessageDao) {
  def getByUser(user: UserRow): Seq[DialogRow] = {
    (for {
      (ud, d) <- Userdialog.filter(_.userid === user.id) join Dialog on (_.dialogid === _.id)
    } yield d).result.get()
  }

  def getByAttachment(attachmentRow: AttachmentRow): Query[Tables.Dialog, Tables.DialogRow, Seq] = {
    for {
      (message, dialog) <- messageDao.getByAttachment(attachmentRow) join Dialog on (_.dialogid === _.id)
    } yield dialog
  }

  def getByUserAndAttachment(attachmentRow: AttachmentRow, userRow: UserRow) = {
    (for {
      (d, ud) <- getByAttachment(attachmentRow) join Userdialog on (_.id === _.dialogid)
      if ud.userid === userRow.id
    } yield d).result.get()
  }
}
