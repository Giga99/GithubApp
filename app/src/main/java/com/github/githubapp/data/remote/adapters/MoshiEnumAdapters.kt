package com.github.githubapp.data.remote.adapters

import com.github.githubapp.data.common.enums.RepoEventTypeEnum
import com.squareup.moshi.*
import javax.inject.Inject

class RepoEventEnumAdapter @Inject constructor() : JsonAdapter<RepoEventTypeEnum>() {
    @FromJson
    override fun fromJson(reader: JsonReader): RepoEventTypeEnum {
        val str = reader.readJsonValue()?.toString()
        return RepoEventTypeEnum.fromJsonValue(str)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: RepoEventTypeEnum?) {
        if (value != null) writer.value(value.jsonValue)
        else writer.nullValue()
    }
}
