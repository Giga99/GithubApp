package com.github.githubapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.githubapp.data.local.entities.RepoEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RepoEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_URL)
    val url: String,
    @Embedded(prefix = PREFIX_OWNER)
    val repoOwner: RepoOwnerEntity
) {
    companion object {
        const val TABLE_NAME = "repos"
        const val COLUMN_ID = "${TABLE_NAME}_id"
        const val COLUMN_NAME = "${TABLE_NAME}_name"
        const val COLUMN_URL = "${TABLE_NAME}_url"
        const val PREFIX_OWNER = "${TABLE_NAME}_owner_"
    }

    data class RepoOwnerEntity(
        @ColumnInfo(name = COLUMN_NAME)
        val name: String,
        @ColumnInfo(name = COLUMN_URL)
        val url: String
    ) {
        companion object {
            const val COLUMN_NAME = "name"
            const val COLUMN_URL = "url"
        }
    }
}
