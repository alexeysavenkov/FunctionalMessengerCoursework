package db.dao

import javax.inject.Singleton

import db.Tables.profile.api._
import db.Tables.{AttachmentRow, Dialog, DialogRow, Message, MessageRow, Messageattachment, UserRow, Userdialog}
import db.Db._
import _root_.db.Tables

@Singleton
class MessageDao {
  def getByAttachment(attachment : AttachmentRow): Query[Tables.Message, Tables.MessageRow, Seq] = {
    for {
      (msg, msgAtt) <- Message join Messageattachment on (_.id === _.messageid)
      if msgAtt.attachmentid === attachment.id
    } yield msg
  }
}
