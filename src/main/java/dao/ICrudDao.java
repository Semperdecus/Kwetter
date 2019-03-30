/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author teren
 */
public interface ICrudDao<T> {

    T add(T object);

    T update(T t);

    void delete(T object);

    void deleteById(Long id);

    T findById(Long id);

    T findObject(T object);
}
