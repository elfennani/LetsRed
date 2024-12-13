package com.elfen.letsred.data.remote.models

import com.elfen.letsred.models.Comment
import com.elfen.letsred.utilities.decodeEntities
import com.squareup.moshi.Json
import java.util.regex.Pattern

data class RemoteComment(
    // Common properties
    val id: String,
    val depth: Int,

    // Normal comment properties
    // @Json(name = "body_html")
    val body: String,
    val author: String?,
    val score: Int,
    @Json(name = "link_id") val postFullId: String,
    @Json(name = "author_fullname") val authorFullId: String?,
    @Json(name = "created_utc") val createdUTC: Long,
)

data class RemoteMoreComment(
    val id: String,
    val depth: Int,
    val count: Int,
    val children: List<String>,
)


fun RemotePage<RemoteDataType>.asAppModel(): List<Comment>{
    val comments = children.mapIndexed { index, comment ->
        if(comment is RemoteDataType.Comment){
            comment.data.run {
                Comment.Content(
                    id = id,
                    body = body.decodeEntities().let { body ->
                        val urlPattern = "(https?://)?(www\\.)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?"

                        // Compile the regex pattern
                        val pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE)
                        val matcher = pattern.matcher(body)

                        // Collect all the URLs into a list
                        val links = mutableListOf<String>()
                        while (matcher.find()) {
                            links.add(matcher.group())
                        }
                        var newBody = body

                        links.filter { it.contains("preview.redd.it") || it.contains("i.redd.it") }
                            .distinct()
                            .forEach { newBody = newBody.replace(it, "![image]($it)") }

                        newBody
                    },
                    authorUsername = if (author.isNullOrEmpty() || authorFullId.isNullOrEmpty()) null else author,
                    authorId = if (author.isNullOrEmpty() || authorFullId.isNullOrEmpty()) null
                    else authorFullId.substring(3),
                    depth = depth,
                    order = index
                )
            }
        } else if (comment is RemoteDataType.MoreComment){
            comment.data.run {
                Comment.More(
                    id = id,
                    depth = depth,
                    order = index
                )
            }
        } else throw Exception()
    }

    return comments;
}