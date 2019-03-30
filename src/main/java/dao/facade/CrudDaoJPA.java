/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.ICrudDao;
import dao.JPA;
import java.lang.reflect.ParameterizedType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author teren
 * @param <T>
 */
@Stateless
@JPA
public class CrudDaoJPA<T> implements ICrudDao<T> {

    @PersistenceContext(name = "persistence/kwetterPU", unitName = "kwetterPU")
    protected EntityManager em;

    private Class<T> type;

    public CrudDaoJPA() {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
    }

    @Override
    public T add(T object) {
        this.em.persist(object);
        return object;
    }

    @Override
    public T update(T t) {
        return this.em.merge(t);
    }

    @Override
    public void delete(T t) {
        t = em.merge(t);
        em.remove(t);
    }

    public void deleteById(Long id) {
        this.delete(findById(id));
    }

    @Override
    public T findById(Long id) {
        return em.find(type, id);
    }

    @Override
    public T findObject(T object) {
        return em.find(type, object);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
