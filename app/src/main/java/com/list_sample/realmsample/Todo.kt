package com.list_sample.realmsample

import io.realm.RealmObject

/**
 * Created by monkey on 2017/10/03.
 */
open class Todo(
        open var name: String = "",
        open var status: Int = 0
        ): RealmObject() {}