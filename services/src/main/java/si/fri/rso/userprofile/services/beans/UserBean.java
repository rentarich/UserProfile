package si.fri.rso.userprofile.services.beans;

import com.kumuluz.ee.logs.LogManager;
import org.eclipse.persistence.oxm.annotations.XmlNameTransformer;
import si.fri.rso.userprofile.models.Borrow;
import si.fri.rso.userprofile.models.Item;
import si.fri.rso.userprofile.models.Person;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ApplicationScoped
public class UserBean {
    private Logger log = Logger.getLogger(UserBean.class.getName());
    private String idBean;
    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(UserBean.class.getName());

    @PostConstruct
    private void init() {
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + UserBean.class.getSimpleName() + " idBean: " + idBean);
        logger.info("Init bean: " + UserBean.class.getSimpleName() + " idBean: " + idBean);

    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    @PreDestroy
    private void destroy(){
        log.info("Deinit bean: " + UserBean.class.getSimpleName() + " idBean: " + idBean);
        logger.info("Deinit bean: " + UserBean.class.getSimpleName() + " idBean: " + idBean);
    }



    public Person getPerson(Integer userId) {
        logger.info("Get info about person with id"+userId);
        TypedQuery<Person> query = em.createNamedQuery("Person.getOnID", Person.class);
        Person p = query.setParameter("id", userId).getResultList().get(0);
        log.info(p.getBorrows().stream().toString());
        return p;

    }

    public List<Borrow> getBorrows(Integer userId) {
        TypedQuery<Person> query = em.createNamedQuery("Person.getOnID", Person.class);
        Person p = query.setParameter("id", userId).getResultList().get(0);
        List<Borrow> b = p.getBorrows();

        return b;
    }

    @Transactional
    public Person updateUser(Integer userId, Person person) {
        Person c = em.find(Person.class, userId);

        if (c == null) {
            return null;
        }

        Person updatedPerson = person;


        updatedPerson.setId(c.getId());
        updatedPerson = em.merge(updatedPerson);

        return updatedPerson;

    }
}

