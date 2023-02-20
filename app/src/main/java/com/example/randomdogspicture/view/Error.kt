package com.example.randomdogspicture.view

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
}