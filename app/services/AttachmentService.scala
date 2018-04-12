package services

import javax.inject.{Inject, Singleton}

import db.Tables.{AttachmentRow, UserRow}
import db.dao.{AttachmentDao, MessageDao}
import playUtils.{Errors, Forbidden, PlayError, SystemError}
import java.io.File
import java.nio.file.Paths

import play.api.{Application, Play}
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.FilePart

import scala.util.Random


@Singleton
class AttachmentService @Inject() (attachmentDao: AttachmentDao, dialogService: DialogService, messageDao: MessageDao) {

    def upload(tmpFile: FilePart[TemporaryFile]): Either[PlayError, AttachmentRow] = {
        val filename = Paths.get(tmpFile.filename).getFileName

        val currentDir = System.getProperty("user.dir")


        val file =
          this.synchronized {
              val filePath: String =
                  Stream.continually(new String(Random.alphanumeric.take(16).toArray))
                    .map(s"$currentDir/" + _)
                    .map(new File(_))
                    .filterNot(_.exists())
                    .map(_.getAbsolutePath)
                    .head

              tmpFile.ref.moveTo(new File(filePath))

              new File(filePath)
          }

        if(!file.exists()) {
            Left(SystemError("Failed to save file on HDD"))
        } else {
            this.add(file)
        }
    }

    def add(file: File): Either[PlayError, AttachmentRow] = {
        if(file.exists()) {
            Right(attachmentDao.insert(AttachmentRow(id = 0, imageurl = file.getAbsolutePath)))
        } else {
            Left(SystemError("File was not uploaded"))
        }
    }

    def add(url: String): AttachmentRow = {
        attachmentDao.insert(AttachmentRow(id = 0, imageurl = url))

    }

    def getById(attachmentId: Long, user: UserRow): Either[PlayError, AttachmentRow] = {
        attachmentDao.getById(attachmentId) match {
            case Right(res) if attachmentIsAvatar(res) => Right(res)
            case Right(res) if dialogService.getAllDialogsByUserAndAttachment(user, res).nonEmpty => Right(res)
            case Right(_) => Left(new Forbidden("You don't have access to this message"))
        }
    }

    def attachmentIsAvatar(attachmentRow: AttachmentRow): Boolean = {
        dialogService.getDialogByAttachment(attachmentRow).isEmpty
    }
}
