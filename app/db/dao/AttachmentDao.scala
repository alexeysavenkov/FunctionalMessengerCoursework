package db.dao

import javax.inject.Singleton

import db.Tables.{Attachment, AttachmentRow, User}
import db.Tables._
import db.Tables.profile.api._
import db.Db._
import playUtils.PlayError
import playUtils.NotFound

@Singleton
class AttachmentDao {
  def insert(row: AttachmentRow): AttachmentRow = {
    val id = ((Attachment returning Attachment.map(_.id)) += row).get()
    getById(id).getOrElse(throw new Exception("Attachment.insert DB error"))
  }
  def getById(id: Long): Either[PlayError, AttachmentRow] = {
    Attachment.filter(_.id === id).result.headOption.get() match {
      case Some(attachment) => Right(attachment)
      case None => Left(new NotFound("Attachment not found"))
    }
  }
}
