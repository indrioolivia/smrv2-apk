package com.id.smrv2.domain.model

data class Schedule(
    val day: String,
    val code: String,
    val course: String,
    val className: String,
    val credits: String,
    val hours: String,
    val semester: String,
    val lecturer: String,
    val room: String
) 