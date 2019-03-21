package com.angelsheaven.teremdemoapp.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.angelsheaven.teremdemoapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val appCompatImageButton = onView(
            allOf(
                withId(R.id.btn_sort),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.tv_title_sort_news_filter_dialog), withText("Filter by:"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Filter by:")))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_title_sort_news_filter_dialog), withText("Filter by:"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Filter by:")))

        val constraintLayout = onView(
            allOf(
                withId(R.id.linearLayout4),
                childAtPosition(
                    allOf(
                        withId(android.R.id.content),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout.perform(click())

        val viewGroup = onView(
            allOf(
                withId(R.id.parent_layout_product_item_full_width),
                childAtPosition(
                    allOf(
                        withId(R.id.card_view),
                        childAtPosition(
                            withId(R.id.lv_news_items),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.btn_filter),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.tv_title_sort_news_filter_dialog), withText("Sort News Type"),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout4),
                        childAtPosition(
                            withId(android.R.id.content),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Sort News Type")))

        val constraintLayout2 = onView(
            allOf(
                withId(R.id.linearLayout4),
                childAtPosition(
                    allOf(
                        withId(android.R.id.content),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        constraintLayout2.perform(click())

        val viewGroup2 = onView(
            allOf(
                withId(R.id.parent_layout_product_item_full_width),
                childAtPosition(
                    allOf(
                        withId(R.id.card_view),
                        childAtPosition(
                            withId(R.id.lv_news_items),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        viewGroup2.check(matches(isDisplayed()))

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.btn_refresh),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val appCompatImageView = onView(
            allOf(
                withClassName(`is`("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"),
                childAtPosition(
                    allOf(
                        withClassName(`is`("android.widget.LinearLayout")),
                        childAtPosition(
                            withId(R.id.search_bar),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
