package com.example.randomdogspicture.presentation

import androidx.annotation.StringRes
import com.example.randomdogspicture.R
import com.example.randomdogspicture.model.ManageResources


interface Error {

    fun message(): String

    abstract class Abstract(
        private val manageResources: ManageResources,
        @StringRes private val messageId: Int
    ) : Error {
        override fun message() = manageResources.getString(messageId)
    }

    class NoConnection(manageResources: ManageResources) :
        Abstract(manageResources, R.string.no_connection)

    class ServiceUnavailable(manageResources: ManageResources) :
        Abstract(manageResources, R.string.service_unavailable)

    class FailedLoading(manageResources: ManageResources) :
        Abstract(manageResources, R.string.failed_loading)

    class BadPicture(manageResources: ManageResources) :
        Abstract(manageResources, R.string.bad_picture)

    class NoSavedPictures(manageResources: ManageResources) :
        Abstract(manageResources, R.string.no_saved_pictures)

    class FailedSaving(manageResources: ManageResources) :
        Abstract(manageResources, R.string.fail_save)

    class FailedCreateMediaStore(manageResources: ManageResources) :
        Abstract(manageResources, R.string.fail_create_media)
}