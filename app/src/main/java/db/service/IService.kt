package db.service

import org.greenrobot.greendao.database.Database
import org.greenrobot.greendao.query.QueryBuilder

/**
 *Create By hHui on 2018/11/16
 */
interface IService<T, K> {

    fun save(item: T): Long

    fun save(vararg items: T)

    fun save(items: List<T>)

    fun saveOrUpdate(item: T): Long

    fun saveOrUpdate(vararg items: T)

    fun saveOrUpdate(items: List<T>)

    fun deleteByKey(key: K)

    fun delete(item: T)

    fun delete(vararg items: T)

    fun delete(items: List<T>)

    fun deleteAll()

    fun update(item: T)

    fun update(vararg items: T)

    fun update(items: List<T>)

    fun query(key: K): T

    fun queryAll(): List<T>

    fun queryBuilder(): QueryBuilder<T>

    fun getDataBase(): Database

    fun count(): Long

    fun refresh(item: T)

    fun detach(item: T)
}