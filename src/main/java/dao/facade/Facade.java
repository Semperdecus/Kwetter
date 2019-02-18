/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author teren
 */
public abstract class Facade<T> {

    private final Class<T> entityClass;

    public Facade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T findById(int id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> getAll() {
        return getEntityManager().createQuery("Select t from " + entityClass.getSimpleName() + " t").getResultList();
    }
}
