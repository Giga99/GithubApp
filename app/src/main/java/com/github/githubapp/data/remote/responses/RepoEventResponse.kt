package com.github.githubapp.data.remote.responses

import com.github.githubapp.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoEventResponse(
    @Json(name = "type") val type: RepoEventTypeEnum,
    @Json(name = "actor") val repoEventActor: RepoEventActorResponse
)

enum class RepoEventTypeEnum(val jsonValue: String, val stringRes: Int) {
    WatchEvent("WatchEvent", R.string.repo_event_type_watch_event),
    IssueCommentEvent("IssueCommentEvent", R.string.repo_event_type_issue_comment_event),
    ForkEvent("ForkEvent", R.string.repo_event_type_fork_event),
    IssuesEvent("IssuesEvent", R.string.repo_event_type_issues_event),
    Unknown("", R.string.repo_event_type_unknown);

    companion object {
        fun fromJsonValue(str: String?) = when (str) {
            WatchEvent.jsonValue -> WatchEvent
            IssueCommentEvent.jsonValue -> IssueCommentEvent
            ForkEvent.jsonValue -> ForkEvent
            IssuesEvent.jsonValue -> IssuesEvent
            else -> Unknown
        }
    }
}
