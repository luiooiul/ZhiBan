package com.zhixue.lite.core.domain

import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class FormatDateUseCase @Inject constructor() {

    private val _formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    operator fun invoke(date: Long): String {
        return _formatter.format(date)
    }
}