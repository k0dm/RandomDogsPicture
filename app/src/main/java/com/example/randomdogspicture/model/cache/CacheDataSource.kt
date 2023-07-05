package com.example.randomdogspicture.model.cache

import com.example.randomdogspicture.model.ManageResources
import com.example.randomdogspicture.model.PictureSelection
import com.example.randomdogspicture.model.cloud.DogCloud
import com.example.randomdogspicture.presentation.DogUI

interface CacheDataSource {

    fun fetch(selection: PictureSelection, cacheCallback: DogCacheCallback)

    fun addOrRemove(dogCloud: DogCloud): DogUI

    class Fake(
        manageResources: ManageResources,

        ) : CacheDataSource {

        private val noSavedPictures =
            com.example.randomdogspicture.presentation.Error.NoSavedPictures(manageResources)

        private var list = ArrayList<DogCloud>()
        private var position: Int = 0


        override fun fetch(selection: PictureSelection, cacheCallback: DogCacheCallback) {
            if (list.isNotEmpty()) {
                cacheCallback.provide(
                    when (selection) {
                        PictureSelection.CURRENT -> {
                            list[position]
                        }
                        PictureSelection.NEXT -> {
                            position++
                            list.getOrElse(position){
                                position = 0
                                list.first()
                            }
                        }
                        else -> {
                            position--
                            list.getOrElse(position) {
                                position = list.lastIndex
                                list.last() }
                        }
                    }
                )
            } else {
                cacheCallback.provideError(noSavedPictures)
            }

        }

        override fun addOrRemove(dogCloud: DogCloud): DogUI {
            return if (list.contains(dogCloud)) {
                if (list.indexOf(dogCloud) <= position) {
                    if (position != 0)
                        position--
                }
                list.remove(dogCloud)
                dogCloud.toDogUi()
            } else {
                list.add(dogCloud)
                dogCloud.toFavoriteDogUi()
            }
        }
    }

}