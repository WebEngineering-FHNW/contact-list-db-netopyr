package ch.fhnw.webec.contactlistdb;

import ch.fhnw.webec.contactlistdb.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ContactControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    void selectContactShouldReturnCorrectData() throws Exception {
        // given
        final Contact contact = new Contact();
        contact.setFirstName("Jon");
        contact.setLastName("Snow");
        contact.getEmails().add("jon.snow@wall.com");
        contact.getEmails().add("jon@snow.xom");
        entityManager.persist(contact);
        entityManager.flush();

        // when
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/?select=" + contact.getId()));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(containsString("Jon")))
                .andExpect(content().string(containsString("Snow")));

    }

}
