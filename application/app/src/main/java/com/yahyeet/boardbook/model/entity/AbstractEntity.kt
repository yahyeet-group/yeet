package com.yahyeet.boardbook.model.entity

abstract class AbstractEntity {
    var id: String? = null

    constructor(id: String) {
        this.id = id
    }

    constructor() {}

    override fun equals(obj: Any?): Boolean {
        if (obj === this) {
            return true
        }

        if (obj !is AbstractEntity) {
            return false
        }

        val entity = obj as AbstractEntity?
        return id == entity!!.id
    }
}
