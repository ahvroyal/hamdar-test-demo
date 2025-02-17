package com.example.hamdartestdemo.domain.model

import java.io.Serializable

data class App(
    val appName: String,
    val cat: String,
    val createdAt: String,
    val iconUrl: String,
    val id: String,
    val pkgName: String,
    val updatedAt: String
): Serializable {

    override fun toString(): String {
        return "App(\n\t appName='$appName',\n\t cat='$cat',\n\t createdAt='$createdAt',\n\t iconUrl='$iconUrl',\n\t id='$id',\n\t pkgName='$pkgName',\n\t updatedAt='$updatedAt'\n)"
    }

}