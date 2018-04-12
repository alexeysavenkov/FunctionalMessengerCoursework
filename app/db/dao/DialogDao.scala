package db.dao

import javax.inject.{Inject, Singleton}

import db.Tables.profile.api._
import db.Tables.{Attachment, AttachmentRow, Dialog, DialogRow, Message, MessageRow, Messageattachment, MessageattachmentRow, User, UserRow, Userdialog, UserdialogRow}
import db.Db._
import _root_.db.Tables
import com.sun.xml.internal.ws.wsdl.writer.document.Message
import playUtils.{NotFound, PlayError}

@Singleton
class DialogDao @Inject()(messageDao: MessageDao) {
  def getById(dialogId: Long): DialogRow = {
    Dialog.filter(_.id === dialogId).result.head.get()
  }

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

  def create(name: String, admin: UserRow, members: Seq[Long]): Unit = {
    val dialogId = (Dialog returning Dialog.map(_.id) += DialogRow(id = -1, name = name)).get()
    insertDialogMember(isAdmin = true, userId = admin.id, dialogId)
    members.foreach(userId => insertDialogMember(isAdmin = false, userId, dialogId))
  }

  private def insertDialogMember(isAdmin: Boolean, userId: Long, dialogId: Long): Unit = {
    (Userdialog += UserdialogRow(userId, dialogId, lastreadmessageid = None, isadmin = false)).exec()
  }

  def getAllMessages(dialogId: Long): Seq[(UserRow, String)] = {
    (for {
      (message, user) <- Tables.Message join User on (_.userid === _.id)
      if message.dialogid === dialogId
    } yield (user, message.messagetext)).result.get()
  }

  def createMessage(dialogId: Long, userId: Long, messageText: String, attachments: Seq[AttachmentRow]): Unit = {
    val messageId = (Tables.Message returning Tables.Message.map(_.id) += MessageRow(id = -1, dialogId, userId, messageText)).get()

    attachments.foreach(at =>
      (Messageattachment += MessageattachmentRow(messageId, at.id)).exec()
    )
  }
}
