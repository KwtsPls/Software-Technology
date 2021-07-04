import gr.uoa.di.jete.Assemblers.WalletModelAssembler
import gr.uoa.di.jete.controllers.WalletController
import gr.uoa.di.jete.models.User
import gr.uoa.di.jete.models.Wallet
import gr.uoa.di.jete.repositories.UserRepository
import gr.uoa.di.jete.repositories.WalletRepository
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@WebMvcTest(WalletController.class)
class WalletControllerTest extends Specification {
    def mvc
    WalletRepository repository
    UserRepository userRepository
    WalletController controller
    WalletModelAssembler assembler

    def setup()
    {
        repository = Stub()
        userRepository = Stub()
        assembler = new WalletModelAssembler()
        controller = new WalletController(repository,assembler,userRepository)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .build()
    }

    def "Get Wallet from user"() {
        when: "Wallet exists"
        repository.findByUsername(_ as String) >> {
            def proj = new Wallet()
            proj.setId(1L)
            return Optional.of(proj)
        }
        and: "Get @ /users/username/wallet"
        def url = "/users/username/wallet"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND wallet with id 1 is returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
    }

    def "Create Wallet for user"() {
        when: "Wallet create"
        def cnt = 0
        def body = new String("{\n" +
                "\"card1\":\"12341234\",\n" +
                "\"subscription_starts\":\"2021-09-01\",\n" +
                "\"subscription_ends\":\"2021-10-02\"\n}")
        userRepository.findByUsername(_ as String) >> {
            def user = new User()
            user.setId(1L)
            return Optional.of(user)
        }
        repository.save(_ as Wallet) >> {
            def wallet = new Wallet()
            wallet.setId(1L)
            cnt++
            wallet.setCard1("12341234")
            return wallet
        }
        and: "Post @ /users/username/wallet"
        def url = "/users/username/wallet"
        MockHttpServletResponse response = mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Response status is Ok AND wallet with id 1 is returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"card1\":\"12341234\"")
        cnt == 1
    }

    def "Update  Wallet from user"() {
        when: "Wallet create"
        def cnt = 0
        def body = new String("{\n" +
                "\"id\":1,\n" +
                "\"card1\":\"12341234\",\n" +
                "\"subscription_starts\":\"2021-09-01\",\n" +
                "\"subscription_ends\":\"2021-10-02\"\n}")
        userRepository.findById(1L) >> {
            def user = new User()
            user.setId(1L)
            return Optional.of(user)
        }
        repository.save(_ as Wallet) >> {
            def wallet = new Wallet()
            wallet.setId(1L)
            cnt++
            wallet.setCard1("12341234")
            return wallet
        }
        and: "Post @ /wallets"
        def url = "/wallets"
        MockHttpServletResponse response = mvc.perform(
                post(url).content(body).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse()
        then: "Response status is Ok AND wallet with id 1 is returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
        response.getContentAsString().contains("\"card1\":\"12341234\"")
        cnt == 1
    }

    def "Get Wallet By id"() {
        when: "Wallet exists"
        repository.findById(_ as Long) >> {
            def proj = new Wallet()
            proj.setId(1L)
            return Optional.of(proj)
        }
        and: "Get @ /wallets/1"
        def url = "/wallets/1"
        MockHttpServletResponse response = mvc.perform(
                get(url)
        ).andReturn().getResponse()
        then: "Response status is Ok AND wallet with id 1 is returned"
        response.getStatus() == 200
        response.getContentAsString().contains("\"id\":1")
    }


}
