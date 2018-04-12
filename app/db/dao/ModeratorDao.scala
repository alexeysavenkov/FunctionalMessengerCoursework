package db.dao

import javax.inject.Singleton

import db.Tables.profile.api._
import db.Tables.{Attachment, AttachmentRow, UserRow, Usercomplaint, User}
import db.Db._
import playUtils.{NotFound, PlayError}

@Singleton
class ModeratorDao {
  def unresolvedComplaints(): Seq[(UserRow, String, Int)] = {
    sql"""
          |SELECT User.*, GROUP_CONCAT(reason SEPARATOR '; ') AS reasons, COUNT(*) AS cnt
          |	FROM User JOIN UserComplaint ON User.id = UserComplaint.userWhoWasAccused
          |	GROUP BY User.id
          |	ORDER BY COUNT(*) DESC
        """.stripMargin.as[(UserRow, String, Int)].get()
  }

  def countOfModeratorsWithAllResolvedComplaints() : Int = {
    sql"""
         |SELECT COUNT(*) FROM User AS User1
         |	WHERE isModerator
         |	AND NOT EXISTS(
         |		SELECT * FROM UserComplaint WHERE NOT isResolved AND NOT EXISTS (
         |			SELECT * FROM User AS User2 WHERE User1.id = User2.id AND UserComplaint.assignedToModerator = User1.id
         |		)
         |	)
      """.stripMargin.as[Int].get().headOption.getOrElse(0)
  }

  def countOfUsersWithAtLeastNMessages(n: Int) : Int = {
    sql"""
         |SELECT COUNT(*)
         |  FROM User
         |    JOIN Message ON Message.userId = User.id
         |    GROUP BY User.id
         |    HAVING COUNT(*) >= $n
      """.stripMargin.as[Int].get().headOption.getOrElse(0)
  }

  def countOfUsersWithAtLeastNFriends(n: Int): Int = {
    sql"""
        | SELECT COUNT(*)
        |     FROM User
        |       JOIN FriendRequest ON
        |         (friendRequestTo = User.id OR friendRequestBy = User.id) AND isConfirmed
        |     GROUP BY User.id
        |     HAVING COUNT(*) >= $n
      """.stripMargin.as[Int].get().headOption.getOrElse(0)
  }

  def countNumberOfDialogsWhereEveryoneHasWrittenAtLeastNTimes(n: Int) = {
    sql"""
        | SELECT COUNT(*) FROM Dialog
        |   WHERE Dialog.id NOT IN (
        |     SELECT DISTINCT(dialogId) FROM Message GROUP BY (dialogId, userId) HAVING COUNT(*) < $n
        |   )
      """.stripMargin.as[Int].get().headOption.getOrElse(0)
  }

  def banUser(userId: Long, moderator: UserRow) : Unit = {
    (for {
      user <- User
      if user.id === userId
    } yield user.isbanned).update(true)

    resolveAllComplaintsByUser(userId, moderator)
  }

  def forgiveUser(userId: Long, moderator: UserRow) : Unit = {
    resolveAllComplaintsByUser(userId, moderator)
  }

  def resolveAllComplaintsByUser(userId: Long, moderator: UserRow): Unit = {
    val moderatorId = moderator.id
    sql"""
         | UPDATE UserComplaint
         | SET isResolved = 1, assignedToModerator = $moderatorId
         | WHERE userWhoWasAccused = $userId""".stripMargin.asUpdate.exec()
  }
}
