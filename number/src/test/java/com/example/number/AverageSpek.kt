package com.example.number

import android.content.Context
import android.util.Log
import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.assertj.core.api.Assertions.assertThat

class AverageSpek : Spek({
    describe("Average class") {
        val prestoredList = listOf(10.0, -300.0,-100.0, 400.0)
        val userList = listOf(10.0, 20.0, 30.0)
        val mockContext = mockk<Context>() {
            mockk(){
                every { applicationContext } returns mockk(){
                    every { getSharedPreferences(any(), any()) } returns mockk(){
                        every { getStringSet(any(), any()) } returns setOf<String>("10.0", "20.0", "30.0") as MutableSet<String>
                        every { edit() } returns mockk() {
                            every { clear() } returns mockk(){
                                every { putStringSet(any(), any()) } returns mockk(){
                                    every { apply() } just Runs
                                }
                            }
                        }
                    }
                }
            }
        }
        val mockContentApiService = mockk<ContentApiService>() {
            every { prestored() } returns mockk() {
                every { enqueue(any()) } just io.mockk.Runs
            }
        }
        lateinit var average : Average

        beforeGroup {
            mockkStatic(Log::class)
            every { Log.d( any(), any()) } returns 0
        }

        afterGroup {
            clearAllMocks()
        }

        beforeEachTest {
            average = spyk(Average(mockContext, mockContentApiService))
        }

        it("10, 20 and 30 should be added"){
            assertThat(average.userNumbers).isEqualTo(userList)
        }

        it("1, 2 and 3 should be added"){
            average.addNumber(1.0)
            average.addNumber(2.0)
            average.addNumber(3.0)
            assertThat(average.userNumbers).isEqualTo(listOf(10.0, 20.0, 30.0, 1.0, 2.0, 3.0))
        }

        it("the average should be 2"){
            average.prestoredNumbers.addAll(prestoredList)
            assertThat(average.getAverage()).isEqualTo((prestoredList + userList).average())
        }
    }
})
