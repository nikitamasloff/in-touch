package com.nikitamaslov.repository.storage

import com.nikitamaslov.repository.storage.model.Id
import com.nikitamaslov.repository.storage.model.Url
import io.reactivex.Completable
import io.reactivex.Single

interface Storage {

    fun create(id: Id, filePath: String): Completable

    fun deleteById(id: Id): Completable

    fun getUrlById(id: Id): Single<Url>
}