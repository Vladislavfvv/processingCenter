package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class DaoAbstractSpring {
    @PersistenceContext
    protected EntityManager em;

    public DaoAbstractSpring(EntityManager em) {
        this.em = em;
    }


    public boolean dropTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName + " CASCADE;";
        try {
            em.createNativeQuery(sql).executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAll(String tableName) {
        String sql = "DELETE FROM " + tableName + ";";
        try {
            em.createQuery(sql).executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
