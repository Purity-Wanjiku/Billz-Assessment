package com.collections.billz.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters.nextOrSame
import java.time.temporal.TemporalAdjusters.previousOrSame
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.of
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import java.time.temporal.TemporalAdjusters.lastDayOfMonth


class DateTimeUtils {
    companion object {
        fun getFirstDayOfWeek(): String {
            val now = LocalDateTime.now()
            val first = now.with(previousOrSame(MONDAY))
            return formatDate(first)
        }

        fun getLastDayOfWeek(): String {
            val now = LocalDateTime.now()
            val first = now.with(nextOrSame(SUNDAY))
            return formatDate(first)

        }

        fun formatDate(LocalDate: LocalDateTime): String {
            val sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return sdf.format(LocalDate)
        }

        fun getDateOfWeekDay(day: String): String {
            val now = LocalDateTime.now()
            val date = now.with(nextOrSame(of(day.toInt())))
            return formatDate(date)
        }

        fun getCurrentYear(): String {
            val now = LocalDateTime.now()
            return now.year.toString()
        }

        fun getFirstDayOfMonth(): String {
            val now = LocalDateTime.now()
            val firstDate = now.with(TemporalAdjusters.firstDayOfMonth())
            return formatDate(firstDate)
        }

        fun getLatsDateOfMOnth(): String {
            val now = LocalDateTime.now()
            val lastDate = now.with(TemporalAdjusters.lastDayOfMonth())
            return formatDate(lastDate)

        }

        fun getFullDateFromDay(day: String): String {
            val now = LocalDateTime.now()
            val fullDate = now.withDayOfMonth(day.toInt())
            return formatDate(fullDate)
        }

        fun getDateFromDayAndMonth(day: Int, month: Int): String {
            val now = LocalDateTime.now()
            val date = now.withMonth(month).withDayOfMonth(day)
            return formatDate(date)
        }
    }
}