package db.service

import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.database.Database
import org.greenrobot.greendao.query.QueryBuilder

/**
 *Create By hHui on 2018/11/16
 */
open class DaoService<T, K>(dao: AbstractDao<T, K>) : IService<T, K> {

    private val mDao: AbstractDao<T, K> = dao

    override fun save(item: T): Long {
        return mDao.insert(item)
    }

    override fun save(vararg items: T) {
        mDao.insertInTx(items.toList())
    }

    override fun save(items: List<T>) {
        mDao.insertInTx(items)
    }

    override fun saveOrUpdate(item: T): Long {
        return mDao.insertOrReplace(item)
    }

    override fun saveOrUpdate(vararg items: T) {
        mDao.insertOrReplaceInTx(items.toList())
    }

    override fun saveOrUpdate(items: List<T>) {
        mDao.insertOrReplaceInTx(items)
    }

    override fun deleteByKey(key: K) {
        mDao.deleteByKey(key)
    }

    override fun delete(item: T) {
        mDao.delete(item)
    }

    override fun delete(vararg items: T) {
        mDao.deleteInTx(items.toList())
    }

    override fun delete(items: List<T>) {
        mDao.deleteInTx(items)
    }

    override fun deleteAll() {
        mDao.deleteAll()
    }

    override fun update(item: T) {
        mDao.update(item)
    }

    override fun update(vararg items: T) {
        mDao.updateInTx(items.toList())
    }

    override fun update(items: List<T>) {
        mDao.updateInTx(items)
    }

    override fun query(key: K): T {
        return mDao.load(key)
    }

    override fun queryAll(): List<T> {
        return mDao.loadAll()
    }

    override fun queryBuilder(): QueryBuilder<T> {
        return mDao.queryBuilder()
    }

    override fun getDataBase(): Database {
        return mDao.database
    }

    override fun count(): Long {
        return mDao.count()
    }

    override fun refresh(item: T) {
        mDao.refresh(item)
    }

    override fun detach(item: T) {
        mDao.detach(item)
    }
}