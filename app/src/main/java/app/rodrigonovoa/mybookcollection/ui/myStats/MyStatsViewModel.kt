package app.rodrigonovoa.mybookcollection.ui.myStats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import app.rodrigonovoa.mybookcollection.utils.DateUtils.fromMillisecondsToHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class MyStatsViewModel(val bookCollectionRepository: BookCollectionRepository) : ViewModel() {

    private val _hoursPerWeek = MutableLiveData<List<Float>>().apply { postValue(listOf()) }
    val hoursPerWeek: LiveData<List<Float>> get() = _hoursPerWeek

    private val hoursPerWeekAux = mutableListOf<Float>()

    private val _localDbBooks = MutableLiveData<List<BookEntity>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<BookEntity>> get() = _localDbBooks

    init {
        getBooksFromDatabase()
    }

    fun getCurrentWeekHoursByBookId(bookId: Int) {
        hoursPerWeekAux.clear()
        runBlocking {
            val mondayJob = getHoursForMonday(bookId)
            mondayJob.join()
            val tuesdayJob = getHoursForTuesday(bookId)
            tuesdayJob.join()
            val wednesdayJob = getHoursForWednesday(bookId)
            wednesdayJob.join()
            val thursdayJob = getHoursForThursday(bookId)
            thursdayJob.join()
            val fridayJob = getHoursForFriday(bookId)
            fridayJob.join()
            val saturdayJob = getHoursForSaturday(bookId)
            saturdayJob.join()
            val sundayJob = getHoursForSunday(bookId)
            sundayJob.join()
        }
    }

    private fun getHoursForMonday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.MONDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.MONDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForTuesday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.TUESDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.TUESDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForWednesday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.WEDNESDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.WEDNESDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForThursday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.THURSDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.THURSDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForFriday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.FRIDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.FRIDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForSaturday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.SATURDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.SATURDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForSunday(bookId: Int): Job {
        val startOfTheDay = getStartOfTheDayInMillis(Calendar.SUNDAY)
        val endOfTheDay = getEndOfTheDayInMillis(Calendar.SUNDAY)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursFromLocalDb(bookId: Int, startOfTheDay: Long, endOfTheDay: Long): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            var hoursPerBook = 0f
            val records = bookCollectionRepository.getRecordsByBookIdAndInterval(bookId, startOfTheDay, endOfTheDay)

            records.forEach {
                val spentTimeInMinutes = fromMillisecondsToHours(it.spentTime ?: 0L)
                hoursPerBook += spentTimeInMinutes
            }

            hoursPerWeekAux.add(hoursPerBook)
            if(hoursPerWeekAux.size == 7) _hoursPerWeek.postValue(hoursPerWeekAux)
        }
    }

    private fun getEndOfTheDayInMillis(day: Int): Long {
        val calendarFinish: Calendar = Calendar.getInstance(Locale.FRENCH)
        calendarFinish.set(Calendar.DAY_OF_WEEK, day)
        calendarFinish.set(Calendar.HOUR_OF_DAY, 23);
        calendarFinish.set(Calendar.MINUTE, 59);
        calendarFinish.set(Calendar.SECOND, 59);
        calendarFinish.set(Calendar.MILLISECOND, 999);

        val endOfTheDay = calendarFinish.timeInMillis

        return endOfTheDay
    }

    private fun getStartOfTheDayInMillis(day: Int): Long {
        val calendarInit: Calendar = Calendar.getInstance(Locale.FRENCH)
        calendarInit.set(Calendar.DAY_OF_WEEK, day)
        calendarInit.set(Calendar.HOUR_OF_DAY, 0);
        calendarInit.set(Calendar.MINUTE, 0);
        calendarInit.set(Calendar.SECOND, 0);
        calendarInit.set(Calendar.MILLISECOND, 0);

        val startOfTheDay = calendarInit.timeInMillis

        return startOfTheDay
    }

    private fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = bookCollectionRepository.getAllBooks()
            _localDbBooks.postValue(books)
        }
    }
}