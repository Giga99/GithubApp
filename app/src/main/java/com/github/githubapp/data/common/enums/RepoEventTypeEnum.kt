package com.github.githubapp.data.common.enums

import com.github.githubapp.R

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
