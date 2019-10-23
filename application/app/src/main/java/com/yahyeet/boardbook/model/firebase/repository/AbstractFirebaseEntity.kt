package com.yahyeet.boardbook.model.firebase.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.yahyeet.boardbook.model.entity.AbstractEntity
import java.util.function.Function

abstract class AbstractFirebaseEntity<T : AbstractEntity> {
    var id: String? = null
    var createdAt: Timestamp? = null
    var updatedAt: Timestamp? = null

    abstract fun toMap(): Map<String, Any>

    abstract fun toModelType(): T

    constructor() {}

    constructor(id: String) {
        this.id = id
    }
}