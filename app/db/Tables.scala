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
  lazy val schema: profile.SchemaDescription = Array(Blacklist.schema, Dialog.schema, Friendrequest.schema, Message.schema, Attachment.schema, Publicpost.schema, User.schema, Usercomplaint.schema, Userdialog.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Blacklist
   *  @param blacklisteduser Database column blacklistedUser SqlType(INT)
   *  @param blacklistedby Database column blacklistedBy SqlType(INT) */
  case class BlacklistRow(blacklisteduser: Int, blacklistedby: Int)
  /** GetResult implicit for fetching BlacklistRow objects using plain SQL queries */
  implicit def GetResultBlacklistRow(implicit e0: GR[Int]): GR[BlacklistRow] = GR{
    prs => import prs._
    BlacklistRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table Blacklist. Objects of this class serve as prototypes for rows in queries. */
  class Blacklist(_tableTag: Tag) extends profile.api.Table[BlacklistRow](_tableTag, Some("ais"), "Blacklist") {
    def * = (blacklisteduser, blacklistedby) <> (BlacklistRow.tupled, BlacklistRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(blacklisteduser), Rep.Some(blacklistedby)).shaped.<>({r=>import r._; _1.map(_=> BlacklistRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column blacklistedUser SqlType(INT) */
    val blacklisteduser: Rep[Int] = column[Int]("blacklistedUser")
    /** Database column blacklistedBy SqlType(INT) */
    val blacklistedby: Rep[Int] = column[Int]("blacklistedBy")

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
   *  @param friendrequestto Database column friendRequestTo SqlType(INT)
   *  @param friendrequestby Database column friendRequestBy SqlType(INT)
   *  @param isconfirmed Database column isConfirmed SqlType(BIT) */
  case class FriendrequestRow(friendrequestto: Int, friendrequestby: Int, isconfirmed: Boolean)
  /** GetResult implicit for fetching FriendrequestRow objects using plain SQL queries */
  implicit def GetResultFriendrequestRow(implicit e0: GR[Int], e1: GR[Boolean]): GR[FriendrequestRow] = GR{
    prs => import prs._
    FriendrequestRow.tupled((<<[Int], <<[Int], <<[Boolean]))
  }
  /** Table description of table FriendRequest. Objects of this class serve as prototypes for rows in queries. */
  class Friendrequest(_tableTag: Tag) extends profile.api.Table[FriendrequestRow](_tableTag, Some("ais"), "FriendRequest") {
    def * = (friendrequestto, friendrequestby, isconfirmed) <> (FriendrequestRow.tupled, FriendrequestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(friendrequestto), Rep.Some(friendrequestby), Rep.Some(isconfirmed)).shaped.<>({r=>import r._; _1.map(_=> FriendrequestRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column friendRequestTo SqlType(INT) */
    val friendrequestto: Rep[Int] = column[Int]("friendRequestTo")
    /** Database column friendRequestBy SqlType(INT) */
    val friendrequestby: Rep[Int] = column[Int]("friendRequestBy")
    /** Database column isConfirmed SqlType(BIT) */
    val isconfirmed: Rep[Boolean] = column[Boolean]("isConfirmed")

    /** Uniqueness Index over (friendrequestby,friendrequestto) (database name friendRequestBy) */
    val index1 = index("friendRequestBy", (friendrequestby, friendrequestto), unique=true)
    /** Uniqueness Index over (friendrequestto,friendrequestby) (database name friendRequestTo) */
    val index2 = index("friendRequestTo", (friendrequestto, friendrequestby), unique=true)
  }
  /** Collection-like TableQuery object for table Friendrequest */
  lazy val Friendrequest = new TableQuery(tag => new Friendrequest(tag))

  /** Entity class storing rows of table Message
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param dialogid Database column dialogId SqlType(INT)
   *  @param userid Database column userId SqlType(INT)
   *  @param messagetext Database column messageText SqlType(VARCHAR), Length(500,true), Default() */
  case class MessageRow(id: Long, dialogid: Int, userid: Int, messagetext: String = "")
  /** GetResult implicit for fetching MessageRow objects using plain SQL queries */
  implicit def GetResultMessageRow(implicit e0: GR[Long], e1: GR[Int], e2: GR[String]): GR[MessageRow] = GR{
    prs => import prs._
    MessageRow.tupled((<<[Long], <<[Int], <<[Int], <<[String]))
  }
  /** Table description of table Message. Objects of this class serve as prototypes for rows in queries. */
  class Message(_tableTag: Tag) extends profile.api.Table[MessageRow](_tableTag, Some("ais"), "Message") {
    def * = (id, dialogid, userid, messagetext) <> (MessageRow.tupled, MessageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(dialogid), Rep.Some(userid), Rep.Some(messagetext)).shaped.<>({r=>import r._; _1.map(_=> MessageRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column dialogId SqlType(INT) */
    val dialogid: Rep[Int] = column[Int]("dialogId")
    /** Database column userId SqlType(INT) */
    val userid: Rep[Int] = column[Int]("userId")
    /** Database column messageText SqlType(VARCHAR), Length(500,true), Default() */
    val messagetext: Rep[String] = column[String]("messageText", O.Length(500,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table Message */
  lazy val Message = new TableQuery(tag => new Message(tag))

  /** Entity class storing rows of table Attachment
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param messageid Database column messageId SqlType(INT), Default(None)
   *  @param publicpostid Database column publicPostId SqlType(INT), Default(None)
   *  @param imageurl Database column imageUrl SqlType(VARCHAR), Length(100,true), Default() */
  case class AttachmentRow(id: Long, messageid: Option[Int] = None, publicpostid: Option[Int] = None, imageurl: String = "")
  /** GetResult implicit for fetching AttachmentRow objects using plain SQL queries */
  implicit def GetResultAttachmentRow(implicit e0: GR[Long], e1: GR[Option[Int]], e2: GR[String]): GR[AttachmentRow] = GR{
    prs => import prs._
    AttachmentRow.tupled((<<[Long], <<?[Int], <<?[Int], <<[String]))
  }
  /** Table description of table Attachment. Objects of this class serve as prototypes for rows in queries. */
  class Attachment(_tableTag: Tag) extends profile.api.Table[AttachmentRow](_tableTag, Some("ais"), "Attachment") {
    def * = (id, messageid, publicpostid, imageurl) <> (AttachmentRow.tupled, AttachmentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), messageid, publicpostid, Rep.Some(imageurl)).shaped.<>({r=>import r._; _1.map(_=> AttachmentRow.tupled((_1.get, _2, _3, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column messageId SqlType(INT), Default(None) */
    val messageid: Rep[Option[Int]] = column[Option[Int]]("messageId", O.Default(None))
    /** Database column publicPostId SqlType(INT), Default(None) */
    val publicpostid: Rep[Option[Int]] = column[Option[Int]]("publicPostId", O.Default(None))
    /** Database column imageUrl SqlType(VARCHAR), Length(100,true), Default() */
    val imageurl: Rep[String] = column[String]("imageUrl", O.Length(100,varying=true), O.Default(""))

    /** Index over (messageid) (database name messageId) */
    val index1 = index("messageId", messageid)
    /** Index over (publicpostid) (database name publicPostId) */
    val index2 = index("publicPostId", publicpostid)
  }
  /** Collection-like TableQuery object for table Attachment */
  lazy val Attachment = new TableQuery(tag => new Attachment(tag))

  /** Entity class storing rows of table Publicpost
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param userid Database column userId SqlType(INT)
   *  @param publicposttext Database column publicPostText SqlType(VARCHAR), Length(1000,true), Default() */
  case class PublicpostRow(id: Long, userid: Int, publicposttext: String = "")
  /** GetResult implicit for fetching PublicpostRow objects using plain SQL queries */
  implicit def GetResultPublicpostRow(implicit e0: GR[Long], e1: GR[Int], e2: GR[String]): GR[PublicpostRow] = GR{
    prs => import prs._
    PublicpostRow.tupled((<<[Long], <<[Int], <<[String]))
  }
  /** Table description of table PublicPost. Objects of this class serve as prototypes for rows in queries. */
  class Publicpost(_tableTag: Tag) extends profile.api.Table[PublicpostRow](_tableTag, Some("ais"), "PublicPost") {
    def * = (id, userid, publicposttext) <> (PublicpostRow.tupled, PublicpostRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userid), Rep.Some(publicposttext)).shaped.<>({r=>import r._; _1.map(_=> PublicpostRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userId SqlType(INT) */
    val userid: Rep[Int] = column[Int]("userId")
    /** Database column publicPostText SqlType(VARCHAR), Length(1000,true), Default() */
    val publicposttext: Rep[String] = column[String]("publicPostText", O.Length(1000,varying=true), O.Default(""))

    /** Index over (userid) (database name userId) */
    val index1 = index("userId", userid)
  }
  /** Collection-like TableQuery object for table Publicpost */
  lazy val Publicpost = new TableQuery(tag => new Publicpost(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param phone Database column phone SqlType(VARCHAR), Length(20,true), Default()
   *  @param password Database column password SqlType(CHAR), Length(32,false), Default()
   *  @param name Database column name SqlType(VARCHAR), Length(20,true), Default(None)
   *  @param description Database column description SqlType(VARCHAR), Length(500,true), Default()
   *  @param isbanned Database column isBanned SqlType(BIT), Default(false)
   *  @param ismoderator Database column isModerator SqlType(BIT), Default(false) */
  case class UserRow(id: Long, phone: String = "", password: String = "", name: Option[String] = None, description: String = "", isbanned: Boolean = false, ismoderator: Boolean = false, authToken: String = "")
  val userRowWrites : Writes[UserRow] = Json.writes[UserRow]
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Boolean]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Long], <<[String], <<[String], <<?[String], <<[String], <<[Boolean], <<[Boolean], <<[String]))
  }
  /** Table description of table User. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("ais"), "User") {
    def * = (id, phone, password, name, description, isbanned, ismoderator, authtoken) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(phone), Rep.Some(password), name, Rep.Some(description), Rep.Some(isbanned), Rep.Some(ismoderator), Rep.Some(authtoken)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

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
    /** Database column isBanned SqlType(BIT), Default(false) */
    val isbanned: Rep[Boolean] = column[Boolean]("isBanned", O.Default(false))
    /** Database column isModerator SqlType(BIT), Default(false) */
    val ismoderator: Rep[Boolean] = column[Boolean]("isModerator", O.Default(false))

    val authtoken: Rep[String] = column[String]("authToken", O.Length(32,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))

  /** Entity class storing rows of table Usercomplaint
   *  @param id Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey
   *  @param userwhowasaccused Database column userWhoWasAccused SqlType(INT)
   *  @param userwhocomplained Database column userWhoComplained SqlType(INT)
   *  @param reason Database column reason SqlType(VARCHAR), Length(500,true), Default()
   *  @param resolvedby Database column resolvedBy SqlType(INT), Default(None) */
  case class UsercomplaintRow(id: Long, userwhowasaccused: Int, userwhocomplained: Int, reason: String = "", resolvedby: Option[Int] = None)
  /** GetResult implicit for fetching UsercomplaintRow objects using plain SQL queries */
  implicit def GetResultUsercomplaintRow(implicit e0: GR[Long], e1: GR[Int], e2: GR[String], e3: GR[Option[Int]]): GR[UsercomplaintRow] = GR{
    prs => import prs._
    UsercomplaintRow.tupled((<<[Long], <<[Int], <<[Int], <<[String], <<?[Int]))
  }
  /** Table description of table UserComplaint. Objects of this class serve as prototypes for rows in queries. */
  class Usercomplaint(_tableTag: Tag) extends profile.api.Table[UsercomplaintRow](_tableTag, Some("ais"), "UserComplaint") {
    def * = (id, userwhowasaccused, userwhocomplained, reason, resolvedby) <> (UsercomplaintRow.tupled, UsercomplaintRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userwhowasaccused), Rep.Some(userwhocomplained), Rep.Some(reason), resolvedby).shaped.<>({r=>import r._; _1.map(_=> UsercomplaintRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column userWhoWasAccused SqlType(INT) */
    val userwhowasaccused: Rep[Int] = column[Int]("userWhoWasAccused")
    /** Database column userWhoComplained SqlType(INT) */
    val userwhocomplained: Rep[Int] = column[Int]("userWhoComplained")
    /** Database column reason SqlType(VARCHAR), Length(500,true), Default() */
    val reason: Rep[String] = column[String]("reason", O.Length(500,varying=true), O.Default(""))
    /** Database column resolvedBy SqlType(INT), Default(None) */
    val resolvedby: Rep[Option[Int]] = column[Option[Int]]("resolvedBy", O.Default(None))
  }
  /** Collection-like TableQuery object for table Usercomplaint */
  lazy val Usercomplaint = new TableQuery(tag => new Usercomplaint(tag))

  /** Entity class storing rows of table Userdialog
   *  @param userid Database column userId SqlType(INT UNSIGNED), AutoInc
   *  @param userdialog Database column userDialog SqlType(INT) */
  case class UserdialogRow(userid: Long, userdialog: Int)
  /** GetResult implicit for fetching UserdialogRow objects using plain SQL queries */
  implicit def GetResultUserdialogRow(implicit e0: GR[Long], e1: GR[Int]): GR[UserdialogRow] = GR{
    prs => import prs._
    UserdialogRow.tupled((<<[Long], <<[Int]))
  }
  /** Table description of table UserDialog. Objects of this class serve as prototypes for rows in queries. */
  class Userdialog(_tableTag: Tag) extends profile.api.Table[UserdialogRow](_tableTag, Some("ais"), "UserDialog") {
    def * = (userid, userdialog) <> (UserdialogRow.tupled, UserdialogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(userid), Rep.Some(userdialog)).shaped.<>({r=>import r._; _1.map(_=> UserdialogRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column userId SqlType(INT UNSIGNED), AutoInc */
    val userid: Rep[Long] = column[Long]("userId", O.AutoInc)
    /** Database column userDialog SqlType(INT) */
    val userdialog: Rep[Int] = column[Int]("userDialog")

    /** Uniqueness Index over (userdialog,userid) (database name userDialog) */
    val index1 = index("userDialog", (userdialog, userid), unique=true)
    /** Uniqueness Index over (userid,userdialog) (database name userId) */
    val index2 = index("userId", (userid, userdialog), unique=true)
  }
  /** Collection-like TableQuery object for table Userdialog */
  lazy val Userdialog = new TableQuery(tag => new Userdialog(tag))
}
