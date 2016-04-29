package at.tuwien.ase;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import at.tuwien.ase.dao.CategoryDaoInterface;
import at.tuwien.ase.model.Category;

@Stateless
public class CategoryDaoImpl implements CategoryDaoInterface {
	@PersistenceContext
	private Session session;



	@Override
	public List<Category> listAll() {
		Query q = session.createQuery("select c from Category as c where c.deleted!=true or c.deleted is null");//i sugest we use hql where possible
		
		return q.list();
	}

	@Override
	public Category get(Long id) {
		Query q = session.createQuery("from Category as c where (c.deleted!=true or c.deleted is null ) and c.id=?");//i sugest we use hql where possible
		q.setLong(0, id);
		return (Category) q.uniqueResult();
	
	}

	@Override
	public void delete(Long id) {
		Transaction tx = session.beginTransaction();
		Category o = (Category) session.load(Category.class, id);
		o.setDeleted(true);
		session.persist(o);
		tx.commit();
		
	}

	

}
