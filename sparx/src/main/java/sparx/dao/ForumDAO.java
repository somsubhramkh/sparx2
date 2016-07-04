package sparx.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import sparx.model.Forum;

@Repository
@Transactional
public class ForumDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void addForum(Forum f) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(f);
		logger.info("Forum saved successfully, Forum Details=" + f);

	}

	
	@SuppressWarnings("unchecked")
	public List<Forum> listForums() {
		Session session = sessionFactory.getCurrentSession();

		List<Forum> forumList = session.createQuery("from Forum").list();
		for (Forum f : forumList) {
			logger.info("Forum List::" + f);
		}

		return forumList;
	}


}
