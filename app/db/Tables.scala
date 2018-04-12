package db

import play.api.libs.json.{Json, Writes}
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Attachment.schema, Blacklist.schema, Dialog.schema, Friendrequest.schema, Message.schema, Messageattachment.schema, User.schema, Usercomplaint.schema, Userdialog.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Attachment
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param imageurl Database column imageUrl SqlType(VARCHAR), Length(2000,true), Default() */
  case class AttachmentRow(id: Long, imageurl: String = "")
  /** GetResult implicit for fetching AttachmentRow objects using plain SQL queries */
  implicit def GetResultAttachmentRow(implicit e0: GR[Long], e1: GR[String]): GR[AttachmentRow] = GR{
    prs => import prs._
      AttachmentRow.tupled((<<[Long], <<[String]))
  }
  /** Table description of table Attachment. Objects of this class serve as prototypes for rows in queries. */
  class Attachment(_tableTag: Tag) extends profile.api.Table[AttachmentRow](_tableTag, Some("ais"), "Attachment") {
    def * = (id, imageurl) <> (AttachmentRow.tupled, AttachmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(imageurl)).shaped.<>({r=>import r._; _1.map(_=> AttachmentRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column imageUrl SqlType(VARCHAR), Length(200,true), Default() */
    val imageurl: Rep[String] = column[String]("imageUrl", O.Length(200,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Attachment */
  lazy val Attachment = new TableQuery(tag => new Attachment(tag))

  /** Entity class storing rows of table Blacklist
   *  @param blacklisteduser Database column blacklistedUser SqlType(INT UNSIGNED)
   *  @param blacklistedby Database column blacklistedBy SqlType(INT UNSIGNED) */
  case class BlacklistRow(blacklisteduser: Long, blacklistedby: Long)
  /** GetResult implicit for fetching BlacklistRow objects using plain SQL queries */
  implicit def GetResultBlacklistRow(implicit e0: GR[Long]): GR[BlacklistRow] = GR{
    prs => import prs._
      BlacklistRow.tupled((<<[Long], <<[Long]))
  }
  /** Table description of table Blacklist. Objects of this class serve as prototypes for rows in queries. */
  class Blacklist(_tableTag: Tag) extends profile.api.Table[BlacklistRow](_tableTag, Some("ais"), "Blacklist") {
    def * = (blacklisteduser, blacklistedby) <> (BlacklistRow.tupled, BlacklistRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(blacklisteduser), Rep.Some(blacklistedby)).shaped.<>({r=>import r._; _1.map(_=> BlacklistRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column blacklistedUser SqlType(INT UNSIGNED) */
    val blacklisteduser: Rep[Long] = column[Long]("blacklistedUser")
    /** Database column blacklistedBy SqlType(INT UNSIGNED) */
    val blacklistedby: Rep[Long] = column[Long]("blacklistedBy")

    /** Foreign key referencing User (database name blacklist_ibfk_1) */
    lazy val userFk1 = foreignKey("blacklist_ibfk_1", blacklisteduser, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name blacklist_ibfk_2) */
    lazy val userFk2 = foreignKey("blacklist_ibfk_2", blacklistedby, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)

    /** Uniqueness Index over (blacklistedby,blacklisteduser) (database name blacklistedBy) */
    val index1 = index("blacklistedBy", (blacklistedby, blacklisteduser), unique=true)
    /** Uniqueness Index over (blacklisteduser,blacklistedby) (database name blacklistedUser) */
    val index2 = index("blacklistedUser", (blacklisteduser, blacklistedby), unique=true)
  }
  /** Collection-like TableQuery object for table Blacklist */
  lazy val Blacklist = new TableQuery(tag => new Blacklist(tag))

  /** Entity class storing rows of table Dialog
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(500,true), Default() */
  case class DialogRow(id: Long, name: String = "")
  /** GetResult implicit for fetching DialogRow objects using plain SQL queries */
  implicit def GetResultDialogRow(implicit e0: GR[Long], e1: GR[String]): GR[DialogRow] = GR{
    prs => import prs._
      DialogRow.tupled((<<[Long], <<[String]))
  }
  /** Table description of table Dialog. Objects of this class serve as prototypes for rows in queries. */
  class Dialog(_tableTag: Tag) extends profile.api.Table[DialogRow](_tableTag, Some("ais"), "Dialog") {
    def * = (id, name) <> (DialogRow.tupled, DialogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> DialogRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(500,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(500,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Dialog */
  lazy val Dialog = new TableQuery(tag => new Dialog(tag))

  /** Entity class storing rows of table Friendrequest
   *  @param friendrequestto Database column friendRequestTo SqlType(INT UNSIGNED)
   *  @param friendrequestby Database column friendRequestBy SqlType(INT UNSIGNED)
   *  @param isconfirmed Database column isConfirmed SqlType(BIT), Default(false) */
  case class FriendrequestRow(friendrequestto: Long, friendrequestby: Long, isconfirmed: Boolean = false)
  /** GetResult implicit for fetching FriendrequestRow objects using plain SQL queries */
  implicit def GetResultFriendrequestRow(implicit e0: GR[Long], e1: GR[Boolean]): GR[FriendrequestRow] = GR{
    prs => import prs._
      FriendrequestRow.tupled((<<[Long], <<[Long], <<[Boolean]))
  }
  /** Table description of table FriendRequest. Objects of this class serve as prototypes for rows in queries. */
  class Friendrequest(_tableTag: Tag) extends profile.api.Table[FriendrequestRow](_tableTag, Some("ais"), "FriendRequest") {
    def * = (friendrequestto, friendrequestby, isconfirmed) <> (FriendrequestRow.tupled, FriendrequestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(friendrequestto), Rep.Some(friendrequestby), Rep.Some(isconfirmed)).shaped.<>({r=>import r._; _1.map(_=> FriendrequestRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column friendRequestTo SqlType(INT UNSIGNED) */
    val friendrequestto: Rep[Long] = column[Long]("friendRequestTo")
    /** Database column friendRequestBy SqlType(INT UNSIGNED) */
    val friendrequestby: Rep[Long] = column[Long]("friendRequestBy")
    /** Database column isConfirmed SqlType(BIT), Default(false) */
    val isconfirmed: Rep[Boolean] = column[Boolean]("isConfirmed", O.Default(false))

    /** Foreign key referencing User (database name friendrequest_ibfk_1) */
    lazy val userFk1 = foreignKey("friendrequest_ibfk_1", friendrequestto, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name friendrequest_ibfk_2) */
    lazy val userFk2 = foreignKey("friendrequest_ibfk_2", friendrequestby, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)

    /** Uniqueness Index over (friendrequestby,friendrequestto) (database name friendRequestBy) */
    val index1 = index("friendRequestBy", (friendrequestby, friendrequestto), unique=true)
    /** Uniqueness Index over (friendrequestto,friendrequestby) (database name friendRequestTo) */
    val index2 = index("friendRequestTo", (friendrequestto, friendrequestby), unique=true)
  }
  /** Collection-like TableQuery object for table Friendrequest */
  lazy val Friendrequest = new TableQuery(tag => new Friendrequest(tag))

  /** Entity class storing rows of table Message
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param dialogid Database column dialogId SqlType(INT UNSIGNED)
   *  @param userid Database column userId SqlType(INT UNSIGNED)
   *  @param messagetext Database column messageText SqlType(VARCHAR), Length(500,true), Default() */
  case class MessageRow(id: Long, dialogid: Long, userid: Long, messagetext: String = "")
  /** GetResult implicit for fetching MessageRow objects using plain SQL queries */
  implicit def GetResultMessageRow(implicit e0: GR[Long], e1: GR[String]): GR[MessageRow] = GR{
    prs => import prs._
      MessageRow.tupled((<<[Long], <<[Long], <<[Long], <<[String]))
  }
  /** Table description of table Message. Objects of this class serve as prototypes for rows in queries. */
  class Message(_tableTag: Tag) extends profile.api.Table[MessageRow](_tableTag, Some("ais"), "Message") {
    def * = (id, dialogid, userid, messagetext) <> (MessageRow.tupled, MessageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(dialogid), Rep.Some(userid), Rep.Some(messagetext)).shaped.<>({r=>import r._; _1.map(_=> MessageRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column dialogId SqlType(INT UNSIGNED) */
    val dialogid: Rep[Long] = column[Long]("dialogId")
    /** Database column userId SqlType(INT UNSIGNED) */
    val userid: Rep[Long] = column[Long]("userId")
    /** Database column messageText SqlType(VARCHAR), Length(500,true), Default() */
    val messagetext: Rep[String] = column[String]("messageText", O.Length(500,varying=true), O.Default(""))

    /** Foreign key referencing Dialog (database name message_ibfk_1) */
    lazy val dialogFk = foreignKey("message_ibfk_1", dialogid, Dialog)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name message_ibfk_2) */
    lazy val userFk = foreignKey("message_ibfk_2", userid, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Message */
  lazy val Message = new TableQuery(tag => new Message(tag))

  /** Entity class storing rows of table Messageattachment
   *  @param messageid Database column messageId SqlType(INT UNSIGNED)
   *  @param attachmentid Database column attachmentId SqlType(INT UNSIGNED) */
  case class MessageattachmentRow(messageid: Long, attachmentid: Long)
  /** GetResult implicit for fetching MessageattachmentRow objects using plain SQL queries */
  implicit def GetResultMessageattachmentRow(implicit e0: GR[Long]): GR[MessageattachmentRow] = GR{
    prs => import prs._
      MessageattachmentRow.tupled((<<[Long], <<[Long]))
  }
  /** Table description of table MessageAttachment. Objects of this class serve as prototypes for rows in queries. */
  class Messageattachment(_tableTag: Tag) extends profile.api.Table[MessageattachmentRow](_tableTag, Some("ais"), "MessageAttachment") {
    def * = (messageid, attachmentid) <> (MessageattachmentRow.tupled, MessageattachmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(messageid), Rep.Some(attachmentid)).shaped.<>({r=>import r._; _1.map(_=> MessageattachmentRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column messageId SqlType(INT UNSIGNED) */
    val messageid: Rep[Long] = column[Long]("messageId")
    /** Database column attachmentId SqlType(INT UNSIGNED) */
    val attachmentid: Rep[Long] = column[Long]("attachmentId")

    /** Foreign key referencing Attachment (database name messageattachment_ibfk_1) */
    lazy val attachmentFk = foreignKey("messageattachment_ibfk_1", attachmentid, Attachment)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Message (database name messageattachment_ibfk_2) */
    lazy val messageFk = foreignKey("messageattachment_ibfk_2", messageid, Message)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Messageattachment */
  lazy val Messageattachment = new TableQuery(tag => new Messageattachment(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param phone Database column phone SqlType(VARCHAR), Length(20,true), Default()
   *  @param password Database column password SqlType(CHAR), Length(32,false), Default()
   *  @param name Database column name SqlType(VARCHAR), Length(20,true), Default(None)
   *  @param description Database column description SqlType(VARCHAR), Length(500,true), Default()
   *  @param avatarid Database column avatarId SqlType(INT), Default(None)
   *  @param isbanned Database column isBanned SqlType(BIT), Default(false)
   *  @param ismoderator Database column isModerator SqlType(BIT), Default(false)
   *  @param authToken Database column authToken SqlType(VARCHAR), Length(32,true), Default() */
  case class UserRow(id: Long, phone: String = "", password: String = "", name: Option[String] = None, description: String = "", avatarid: Option[Int] = None, isbanned: Boolean = false, ismoderator: Boolean = false, authToken: String = "")
  import playUtils.OWritesOps._
  val userRowWrites: Writes[UserRow] = Json.writes[UserRow].removeField("password")
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Int]], e4: GR[Boolean]): GR[UserRow] = GR{
    prs => import prs._
      UserRow.tupled((<<[Long], <<[String], <<[String], <<?[String], <<[String], <<?[Int], <<[Boolean], <<[Boolean], <<[String]))
  }
  /** Table description of table User. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("ais"), "User") {
    def * = (id, phone, password, name, description, avatarid, isbanned, ismoderator, authtoken) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(phone), Rep.Some(password), name, Rep.Some(description), avatarid, Rep.Some(isbanned), Rep.Some(ismoderator), Rep.Some(authtoken)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column phone SqlType(VARCHAR), Length(20,true), Default() */
    val phone: Rep[String] = column[String]("phone", O.Length(20,varying=true), O.Default(""))
    /** Database column password SqlType(CHAR), Length(32,false), Default() */
    val password: Rep[String] = column[String]("password", O.Length(32,varying=false), O.Default(""))
    /** Database column name SqlType(VARCHAR), Length(20,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(20,varying=true), O.Default(None))
    /** Database column description SqlType(VARCHAR), Length(500,true), Default() */
    val description: Rep[String] = column[String]("description", O.Length(500,varying=true), O.Default(""))
    /** Database column avatarId SqlType(INT), Default(None) */
    val avatarid: Rep[Option[Int]] = column[Option[Int]]("avatarId", O.Default(None))
    /** Database column isBanned SqlType(BIT), Default(false) */
    val isbanned: Rep[Boolean] = column[Boolean]("isBanned", O.Default(false))
    /** Database column isModerator SqlType(BIT), Default(false) */
    val ismoderator: Rep[Boolean] = column[Boolean]("isModerator", O.Default(false))
    /** Database column authToken SqlType(VARCHAR), Length(32,true), Default() */
    val authtoken: Rep[String] = column[String]("authToken", O.Length(32,varying=true), O.Default(""))

    /** Index over (description) (database name description) */
    val index1 = index("description", description)
    /** Index over (name) (database name name) */
    val index2 = index("name", name)
    /** Uniqueness Index over (phone) (database name phone) */
    val index3 = index("phone", phone, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))

  /** Entity class storing rows of table Usercomplaint
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param userwhowasaccused Database column userWhoWasAccused SqlType(INT UNSIGNED)
   *  @param userwhocomplained Database column userWhoComplained SqlType(INT UNSIGNED)
   *  @param reason Database column reason SqlType(VARCHAR), Length(500,true), Default()
   *  @param assignedtomoderator Database column assignedToModerator SqlType(INT UNSIGNED), Default(None)
   *  @param isresolved Database column isResolved SqlType(BIT) */
  case class UsercomplaintRow(id: Long, userwhowasaccused: Long, userwhocomplained: Long, reason: String = "", assignedtomoderator: Option[Long] = None, isresolved: Boolean)
  /** GetResult implicit for fetching UsercomplaintRow objects using plain SQL queries */
  implicit def GetResultUsercomplaintRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Long]], e3: GR[Boolean]): GR[UsercomplaintRow] = GR{
    prs => import prs._
      UsercomplaintRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<?[Long], <<[Boolean]))
  }
  /** Table description of table UserComplaint. Objects of this class serve as prototypes for rows in queries. */
  class Usercomplaint(_tableTag: Tag) extends profile.api.Table[UsercomplaintRow](_tableTag, Some("ais"), "UserComplaint") {
    def * = (id, userwhowasaccused, userwhocomplained, reason, assignedtomoderator, isresolved) <> (UsercomplaintRow.tupled, UsercomplaintRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userwhowasaccused), Rep.Some(userwhocomplained), Rep.Some(reason), assignedtomoderator, Rep.Some(isresolved)).shaped.<>({r=>import r._; _1.map(_=> UsercomplaintRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userWhoWasAccused SqlType(INT UNSIGNED) */
    val userwhowasaccused: Rep[Long] = column[Long]("userWhoWasAccused")
    /** Database column userWhoComplained SqlType(INT UNSIGNED) */
    val userwhocomplained: Rep[Long] = column[Long]("userWhoComplained")
    /** Database column reason SqlType(VARCHAR), Length(500,true), Default() */
    val reason: Rep[String] = column[String]("reason", O.Length(500,varying=true), O.Default(""))
    /** Database column assignedToModerator SqlType(INT UNSIGNED), Default(None) */
    val assignedtomoderator: Rep[Option[Long]] = column[Option[Long]]("assignedToModerator", O.Default(None))
    /** Database column isResolved SqlType(BIT) */
    val isresolved: Rep[Boolean] = column[Boolean]("isResolved")

    /** Foreign key referencing User (database name usercomplaint_ibfk_2) */
    lazy val userFk1 = foreignKey("usercomplaint_ibfk_2", userwhocomplained, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name usercomplaint_ibfk_3) */
    lazy val userFk2 = foreignKey("usercomplaint_ibfk_3", userwhowasaccused, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name usercomplaint_ibfk_4) */
    lazy val userFk3 = foreignKey("usercomplaint_ibfk_4", assignedtomoderator, User)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Usercomplaint */
  lazy val Usercomplaint = new TableQuery(tag => new Usercomplaint(tag))

  /** Entity class storing rows of table Userdialog
   *  @param userid Database column userId SqlType(INT UNSIGNED), AutoInc
   *  @param dialogid Database column dialogId SqlType(INT UNSIGNED)
   *  @param lastreadmessageid Database column lastReadMessageId SqlType(INT UNSIGNED), Default(None)
   *  @param isadmin Database column isAdmin SqlType(INT UNSIGNED), Default(None) */
  case class UserdialogRow(userid: Long, dialogid: Long, lastreadmessageid: Option[Long] = None, isadmin: Boolean = false)
  /** GetResult implicit for fetching UserdialogRow objects using plain SQL queries */
  implicit def GetResultUserdialogRow(implicit e0: GR[Long], e1: GR[Option[Long]]): GR[UserdialogRow] = GR{
    prs => import prs._
      UserdialogRow.tupled((<<[Long], <<[Long], <<?[Long], <<[Boolean]))
  }
  /** Table description of table UserDialog. Objects of this class serve as prototypes for rows in queries. */
  class Userdialog(_tableTag: Tag) extends profile.api.Table[UserdialogRow](_tableTag, Some("ais"), "UserDialog") {
    def * = (userid, dialogid, lastreadmessageid, isadmin) <> (UserdialogRow.tupled, UserdialogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(userid), Rep.Some(dialogid), lastreadmessageid, isadmin).shaped.<>({r=>import r._; _1.map(_=> UserdialogRow.tupled((_1.get, _2.get, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userId SqlType(INT UNSIGNED), AutoInc */
    val userid: Rep[Long] = column[Long]("userId")
    /** Database column dialogId SqlType(INT UNSIGNED) */
    val dialogid: Rep[Long] = column[Long]("dialogId")
    /** Database column lastReadMessageId SqlType(INT UNSIGNED), Default(None) */
    val lastreadmessageid: Rep[Option[Long]] = column[Option[Long]]("lastReadMessageId", O.Default(None))
    /** Database column isAdmin SqlType(INT UNSIGNED), Default(None) */
    val isadmin: Rep[Boolean] = column[Boolean]("isAdmin", O.Default(false))

    /** Foreign key referencing Dialog (database name userdialog_ibfk_2) */
    lazy val dialogFk = foreignKey("userdialog_ibfk_2", dialogid, Dialog)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing Message (database name userdialog_ibfk_3) */
    lazy val messageFk = foreignKey("userdialog_ibfk_3", lastreadmessageid, Message)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name userdialog_ibfk_1) */
    lazy val userFk = foreignKey("userdialog_ibfk_1", userid, User)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)

    /** Uniqueness Index over (dialogid,userid) (database name userDialog) */
    val index1 = index("userDialog", (dialogid, userid), unique=true)
    /** Uniqueness Index over (userid,dialogid) (database name userId) */
    val index2 = index("userId", (userid, dialogid), unique=true)
  }
  /** Collection-like TableQuery object for table Userdialog */
  lazy val Userdialog = new TableQuery(tag => new Userdialog(tag))
}
