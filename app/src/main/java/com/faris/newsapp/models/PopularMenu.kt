package com.faris.newsapp.models

import androidx.annotation.StringRes
import com.faris.newsapp.R

enum class PopularMenu(@StringRes val nameRes: Int) {
    MostViewed(R.string.most_viewed),
    MostShared(R.string.most_shared),
    MostEmailed(R.string.most_emailed)
}