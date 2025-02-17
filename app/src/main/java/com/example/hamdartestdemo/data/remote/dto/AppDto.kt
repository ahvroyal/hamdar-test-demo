package com.example.hamdartestdemo.data.remote.dto

import com.example.hamdartestdemo.domain.model.App

data class AppDto(
    val appName: String,
    val cat: String,
    val createdAt: String,
    val iconUrl: String,
    val id: String,
    val pkgName: String,
    val updatedAt: String
)

fun AppDto.toApp(): App {
    return App(
        appName = appName,
        cat = cat,
        createdAt = createdAt,
        iconUrl = iconUrl,
        id = id,
        pkgName = pkgName,
        updatedAt = updatedAt
    )
}