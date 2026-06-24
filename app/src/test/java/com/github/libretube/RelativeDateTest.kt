package com.github.libretube

import com.github.libretube.util.TextUtils
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RelativeDateTest {
    @Test
    fun reportOneWeek() {
        val zone = ZoneId.of("America/New_York")
        val now = LocalDateTime.of(2025, 3, 12, 12, 0)
        val upload = ZonedDateTime.of(2025, 3, 5, 12, 0, 0, 0, zone)
        val sevenago = upload.toInstant().toEpochMilli()

        // Due to the DST spring-forward, less than 7x24h of real time has actually passed
        assertTrue(Duration.between(upload.toInstant(), now.atZone(zone).toInstant()) < Duration.ofDays(7))

        // ...but it is still 7 calendar days, so it must be reported as 1 week (not "0 weeks")
        assertEquals(ChronoUnit.WEEKS to 1L, TextUtils.getRelativeTimeUnit(sevenago, now, zone))
    }
}
