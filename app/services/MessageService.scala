package services

import javax.inject.{Inject, Singleton}

import _root_.db.dao.DialogDao
import db.Tables.{AttachmentRow, DialogRow, UserRow}
import db.Db._
import _root_.db.Tables.profile.api._


@Singleton
class MessageService @Inject()(dialogDao: DialogDao, attachmentService: AttachmentService) {

  def createMessage(userId: Long, dialogId: Long, messageText: String, attachmentsBase64: Seq[String]): Unit = {
    val attachments : Seq[AttachmentRow] = attachmentsBase64.map(attachmentService.add)
    dialogDao.createMessage(dialogId, userId, messageText, attachments)
  }
}
