package sparx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sparx.dao.ForumDAO;
import sparx.model.Forum;



@Service
public class ForumService {

	@Autowired
	private ForumDAO forumDAO;

	public void setForumDAO(ForumDAO u) {
		this.forumDAO = u;
	}

	@Transactional
	public void addForum(Forum f) {
		forumDAO.addForum(f);
	}

	
	@Transactional
	public List<Forum> listForums() {
		return forumDAO.listForums();
	}

}

