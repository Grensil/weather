package com.example.data

import com.example.data.model.Article
import com.example.domain.ArticleDto
import com.example.domain.SourceDto

fun Article.toArticleDto(): ArticleDto {
    return ArticleDto(
        source = SourceDto(id = this.source.id, name = this.source.name),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}
