package com.github.githubapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.githubapp.data.local.entities.RepoEntity

@Database(
    entities = [RepoEntity::class],
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {

    abstract val reposDao: ReposDao
}
