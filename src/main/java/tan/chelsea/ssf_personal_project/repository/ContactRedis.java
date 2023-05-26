package tan.chelsea.ssf_personal_project.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import tan.chelsea.ssf_personal_project.model.Contact;

// for storing and querying data
@Repository
public class ContactRedis {

    final String CONTACT_LIST = "contactList";

    // Inject an instance of RedisTemplate into this service object
    // RedisTemplate is created by @Bean method
    @Autowired
    RedisTemplate<String, Object> template;

    public void saveContact(Contact contact, Model model){
        // to insert record into list
        // template.opsForList().leftPush(CONTACT_LIST, contact.getId());

        // to insert record into hash
        template.opsForHash().put(CONTACT_LIST+"_HASH", contact.getId(), contact);
        model.addAttribute("contact", contact);
    }

    public Contact getContactbyId(String contactID){
        Contact contact = (Contact) template.opsForHash().get(CONTACT_LIST+"_HASH",contactID);
        return contact;
    }

    public boolean isUniqueEmail(String email){
        List<Contact> contacts = getAllContacts();
        for (Contact contact : contacts){
            if (contact.getEmail().equals(email)){
                return false;
            }
        }
       
        return true;
    }

    public List<Contact> getAllContacts(){
        return template.opsForHash().values(CONTACT_LIST+"_HASH").stream()
        .filter(Contact.class :: isInstance)
        .map(Contact.class :: cast)
        .collect(Collectors.toList());
    }
    
}
