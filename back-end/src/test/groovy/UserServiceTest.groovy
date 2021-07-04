import gr.uoa.di.jete.Assemblers.UserModelAssembler
import gr.uoa.di.jete.controllers.UserService
import gr.uoa.di.jete.exceptions.EmailInUseException
import gr.uoa.di.jete.exceptions.UserInUseException
import gr.uoa.di.jete.models.User
import gr.uoa.di.jete.repositories.UserRepository
import spock.lang.Specification

class UserServiceTest extends Specification{
    UserRepository repository
    UserModelAssembler assembler
    UserService service

    def setup(){
        repository = Stub()
        assembler = new UserModelAssembler()
        service = new UserService(repository,assembler)
    }

    def "Get all Users"(){
        when: "Users exist"
        def user1,user2
        user1 = new User()
        user2 = new User()
        user1.setId(1L)
        user2.setId(2L)
        repository.findAll() >> {
            return [user1,user2]
        }
        and: "@getUserCollectionList"
        def list = service.getUserCollectionList()
        then:"A list is returned"
            list.toString().contains("id=1")
            list.toString().contains("id=2")
    }

    def "Get User by Id"(){
        when: "Users exist"
        def user1
        user1 = new User()
        user1.setId(1L)
        repository.findById(_ as Long) >> {
            return Optional.of(user1)
        }
        and: "@getUserById"
        def model = service.getUserById(1)
        then:"A user is returned"
        model.toString().contains("id=1")
    }
    def "Get User by Login"(){
        when: "Users exist"
        def user1
        user1 = new User()
        user1.setId(1L)
        repository.findByLogin(_ as String,_ as String) >> {
            return Optional.of(user1)
        }
        and: "@getUserByLogin"
        def model = service.getUserByLogin("kati","pass")
        then:"A user is returned"
        model.toString().contains("id=1")
    }
    def "Get User by Username"(){
        when: "Users exist"
        def user1
        user1 = new User()
        user1.setId(1L)
        repository.findByUsername(_ as String) >> {
            return Optional.of(user1)
        }
        and: "@getUserById"
        def model = service.getUserByUsername("kati")
        then:"A user is returned"
        model.toString().contains("id=1")
    }
    def "Get Users by ProjectId"(){
        when: "Users exist"
        def user1,user2
        user1 = new User()
        user2 = new User()
        user1.setId(1L)
        user2.setId(2L)
        repository.findAllByProjectId(_ as Long) >> {
            return [user1,user2]
        }
        and: "@getUsersByProjectId"
        def model = service.getUsersByProjectId(1)
        then:"A list is returned"
        model.toString().contains("id=1")
        model.toString().contains("id=2")
    }

    def "Set Users To enabled"(){
        when: "Users is set to enabled"
        repository.setEnabledToTrue(_ as String, _ as String) >> 1
        and: "@setUserToEnabled"
        def model = service.setUserToEnabled(_ as String,_ as String)
        then:"A list is returned"
        model == 1

    }

    def "Add New User"(){
        when: "New User Is Created"
            def cnt = 0
            User user = new User("username","pass","email","bio","location",1L,"pronouns","first","last")
            repository.findUserByEmail(_ as String) >> Optional.empty()
            repository.findByUsername(_ as String) >> Optional.empty()
            repository.save(user) >> {
                cnt++
                return user
            }
        and: "@addNewUser"
            def ret = service.addNewUser(user)
        then: "User Is saved and returned and cnt == 1 "
            cnt == 1
            ret == user
    }

    def "Add New User Failed userName null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User(null,"pass","email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
            IllegalStateException exception = thrown()
            exception.message == "Invalid Input"
    }


    def "Add New User Failed userName length == 0"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("","pass","email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed pass null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("user",null,"email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed email null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("user","pass",null,"bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }

    def "Add New User Failed status null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("user","p","email","bio","location",null,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed firstname null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("user","p","email","bio","location",1L,"pronouns",null,"last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed lastname null"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("user","p","email","bio","location",1L,"pronouns","first",null)
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }
    def "Add New User Failed pass length == 0"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("u","","email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed email length == 0"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("u","p","","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed firstname length == 0"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("u","p","email","bio","location",1L,"pronouns","","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }


    def "Add New User Failed lastname length == 0"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("u","p","email","bio","location",1L,"pronouns","first","")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Invalid Input Error message "
        IllegalStateException exception = thrown()
        exception.message == "Invalid Input"
    }

    def "Add New User Failed EmailInUseException"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("username","pass","email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.of(user)
        repository.findByUsername(_ as String) >> Optional.empty()
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Email in use "
        EmailInUseException exception = thrown()
        exception.message == "Email email already in use"
    }

    def "Add New User Failed Username In Use"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("username","pass","email","bio","location",1L,"pronouns","first","last")
        repository.findUserByEmail(_ as String) >> Optional.empty()
        repository.findByUsername(_ as String) >> Optional.of(user)
        repository.save(user) >> {
            cnt++
            return user
        }
        and: "@addNewUser"
        service.addNewUser(user)
        then: "Username in use "
        UserInUseException exception = thrown()
        exception.message == "Username username already in use"
    }


    def "Update new User"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("username","pass","email","bio","location",1L,"pronouns","first","last")
        User newUser = new User("newusername","newpass","newemail","newbio","newlocation",2L,"newpronouns","newfirst","newlast")
        repository.save(user) >> {
            cnt++
            return user
        }
        repository.save(newUser) >> {
            cnt++
            return newUser
        }
        repository.findById(1) >> Optional.of(user)
        and: "@addNewUser"
        def ret = service.updateUser(newUser,1)
        then: "newUser should be user"
            ret.getUsername() == newUser.getUsername()
            ret.getPassword() != newUser.getPassword()
            ret.getEmail() == newUser.getEmail()
            ret.getFirstName() == newUser.getFirstName()
            ret.getLastName() == newUser.getLastName()
            ret.getPronouns() == newUser.getPronouns()
            ret.getLocation() == newUser.getLocation()
            ret.getBio() == newUser.getBio()
            ret.getStatus() == newUser.getStatus()


    }

    def "Update new User some vals are null to check if there are not being changed)"(){
        when: "New User Is Created"
        def cnt = 0
        User user = new User("username","pass","email","bio","location",1L,"pronouns","first","last")
        User newUser = new User(newUsername, newPass, newEmail, newBio, newLocation, newStatus,newPronouns,newFirstname,newLastname)
        repository.save(user) >> {
            cnt++
            return user
        }
        repository.save(newUser) >> {
            cnt++
            return newUser
        }
        repository.findById(1) >> Optional.of(user)
        and: "@updateUser"
        def ret = service.updateUser(newUser,1)
        then: "newUser should be updated Correctly"
        if(newUsername == null || newUsername.length() == 0 )
            ret.getUsername() == user.getUsername()
        else
            ret.getUsername() == newUsername

        if(newPass == null || newPass.length() == 0)
            ret.getPassword() == user.getPassword()
        else
            ret.getPassword() == newPass

        if(newEmail == null || newEmail.length() == 0)
            ret.getEmail() == user.getEmail()
        else
            ret.getEmail() == newEmail

        if( newFirstname == null || newFirstname.length() == 0)
            ret.getFirstName() == user.getFirstName()
        else
            ret.getFirstName() == newFirstname

        if(newLastname == null || newLastname.length() == 0 )
            ret.getLastName() == user.getLastName()
        else
            ret.getLastName() == newLastname

        if(newPronouns == null || newPronouns.length() == 0)
            ret.getPronouns() == user.getPronouns()
        else
            ret.getPronouns() == newPronouns

        if(newLocation == null || newLocation.length() == 0)
            ret.getLocation() == user.getLocation()
        else
            ret.getLocation() == newLocation

        if(newBio == null || newBio.length() == 0)
            ret.getBio() == user.getBio()
        else
            ret.getBio() == newBio
        if(newStatus == null)
            ret.getStatus() == user.getStatus()
        else
            ret.getStatus() == newStatus



        where:
            newUsername    | newPass   | newEmail  |    newBio      | newLocation   | newStatus  | newPronouns   | newFirstname | newLastname
            "username"     | "pass"    | "email"   |    "bio"       | "location"    | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "pass"    | "email"   |    "bio"       | "location"    | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "email"   |    "bio"       | "location"    | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "bio"       | "location"    | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "location"    | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "newlocation" | 1L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "pronouns"   | "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "first"      | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "last"
            "newusername"  | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | ""        |    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | ""        |    ""          | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | ""        |    ""          | ""            | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | ""        |    ""          | ""            | null       |  "newpronouns"| "newfirst"   | "newlast"
                ""         | ""        | ""        |    ""          | ""            | null       |  ""           | "newfirst"   | "newlast"
                ""         | ""        | ""        |    ""          | ""            | null       |  ""           | ""           | "newlast"
                ""         | ""        | ""        |    ""          | ""            | null       |  ""           | ""           | ""
                null       | "newpass" | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | "newemail"|    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | null      |    "newbio"    | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | null      |    null        | "newlocation" | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | null      |    null        |   null        | 2L         |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | null      |    null        |   null        | null       |  "newpronouns"| "newfirst"   | "newlast"
                null       | null      | null      |    null        |   null        | null       |  null         | "newfirst"   | "newlast"
                null       | null      | null      |    null        |   null        | null       |  null         | null         | "newlast"
                null       | null      | null      |    null        |   null        | null       |  null         | null         |  null

    }

    def "Delete By Id"(){
        when: "User With id exists"
        def cnt = 0
        repository.deleteById(_ as Long) >> {cnt++}
        and: "@deleteById"
        service.deleteById(1)
        then:"Cnt == 1"
        cnt==1
    }


}
