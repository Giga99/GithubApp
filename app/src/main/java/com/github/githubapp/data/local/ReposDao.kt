package com.github.githubapp.data.local

import androidx.room.*
import com.github.githubapp.data.local.entities.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(reposEntity: List<RepoEntity>)

    @Delete
    suspend fun deleteRepo(repoEntity: RepoEntity)

    @Query(
        """
            SELECT *
            FROM ${RepoEntity.TABLE_NAME}
            WHERE ${RepoEntity.PREFIX_OWNER}${RepoEntity.RepoOwnerEntity.COLUMN_NAME} = :ownerName
        """
    )
    fun getReposForOwner(ownerName: String): Flow<List<RepoEntity>>

    @Query(
        """
            SELECT *
            FROM ${RepoEntity.TABLE_NAME}
            WHERE ${RepoEntity.PREFIX_OWNER}${RepoEntity.RepoOwnerEntity.COLUMN_NAME} = :ownerName AND ${RepoEntity.COLUMN_NAME} = :repoName
        """
    )
    fun getRepo(ownerName: String, repoName: String): Flow<RepoEntity>
}
