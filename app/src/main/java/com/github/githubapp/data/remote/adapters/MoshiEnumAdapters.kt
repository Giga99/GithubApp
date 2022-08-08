package com.github.githubapp.data.remote.adapters

import com.github.githubapp.data.common.enums.RepoEventTypeEnum
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import javax.inject.Inject

class RepoEventEnumAdapter @Inject constructor() : JsonAdapter<RepoEventTypeEnum>() {
    override fun fromJson(reader: JsonReader): RepoEventTypeEnum? {
        val str = reader.readJsonValue()?.toString()
        return RepoEventTypeEnum.fromJsonValue(str)
    }

    override fun toJson(writer: JsonWriter, value: RepoEventTypeEnum?) {
        if (value != null) writer.value(value.jsonValue)
        else writer.nullValue()
    }
}
