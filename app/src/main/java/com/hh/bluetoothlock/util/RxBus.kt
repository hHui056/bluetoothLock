package com.hh.bluetoothlock.util

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * 消息发布/订阅。
 * 全局事件使用[RxBus.default]获取全局单例，请确保全局消息类型唯一。
 * 局部事件使用[RxBus.newInstance]创建新的实例，避免全局消息类型冲突，接收到无关的消息。
 *
 * Created by hHui on 2018/11/16.
 */
class RxBus private constructor() {

    companion object {
        val default: RxBus by lazy {
            RxBus()
        }

        val newInstance: RxBus
            get() = RxBus()
    }

    private val bus: Subject<Any> by lazy {
        PublishSubject.create<Any>().toSerialized()
    }
    private val disposables: MutableMap<Any, MutableMap<Any, Disposable>> = mutableMapOf()

    /**
     * 发布消息。
     * @param msg 必须是[EventType]类型及其子类型
     */
    fun <T : EventType> post(msg: T) {
        //XXX:如果订阅者太多，此处会耗时，可以考虑异步调用onNext
        bus.onNext(msg)
    }

    /**
     * 注册消息订阅者
     * @param token 订阅者的宿主对象
     * @param msgType 订阅的消息类型
     */
    fun <T : EventType> register(token: Any, msgType: Class<T>): Observable<T> {
        val subscriber = bus.ofType(msgType)
        return subscriber.doOnSubscribe {
            if (disposables.containsKey(token)) {
                disposables[token]?.put(msgType, it)
            } else {
                disposables.put(token, mutableMapOf(msgType.to(it)))
            }
        }
    }

    /**
     * 注销指定宿主对象和指定消息类型的订阅者
     * @param token 订阅者的宿主对象
     * @param msgType 订阅的消息类型，null表示注销[token]关联的所有订阅者
     */
    fun <T : EventType> unregister(token: Any, msgType: Class<T>? = null) {
        if (msgType != null) {
            disposables[token]?.get(msgType)?.dispose()
            disposables[token]?.remove(msgType)
        } else {
            disposables[token]?.values?.forEach { it.dispose() }
            disposables.remove(token)
        }
    }

    /**
     * [post]方法的参数类型
     */
    open class EventType
}