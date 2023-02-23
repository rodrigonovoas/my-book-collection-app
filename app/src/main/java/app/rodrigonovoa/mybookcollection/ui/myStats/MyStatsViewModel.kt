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

    fun getCurrentWeekHoursByBookId(calendar: Calendar,bookId: Int) {
        hoursPerWeekAux.clear()
        runBlocking {
            val mondayJob = getHoursForMonday(calendar, bookId)
            mondayJob.join()
            val tuesdayJob = getHoursForTuesday(calendar, bookId)
            tuesdayJob.join()
            val wednesdayJob = getHoursForWednesday(calendar, bookId)
            wednesdayJob.join()
            val thursdayJob = getHoursForThursday(calendar, bookId)
            thursdayJob.join()
            val fridayJob = getHoursForFriday(calendar, bookId)
            fridayJob.join()
            val saturdayJob = getHoursForSaturday(calendar, bookId)
            saturdayJob.join()
            val sundayJob = getHoursForSunday(calendar, bookId)
            sundayJob.join()
        }
    }

    private fun getHoursForMonday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForTuesday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForWednesday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForThursday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForFriday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForSaturday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

        return getHoursFromLocalDb(bookId, startOfTheDay, endOfTheDay)
    }

    private fun getHoursForSunday(calendar: Calendar, bookId: Int): Job {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startOfTheDay = getStartOfTheDayInMillis(calendar)
        val endOfTheDay = getEndOfTheDayInMillis(calendar)

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

    private fun getEndOfTheDayInMillis(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        val endOfTheDay = calendar.timeInMillis

        return endOfTheDay
    }

    private fun getStartOfTheDayInMillis(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        val startOfTheDay = calendar.timeInMillis

        return startOfTheDay
    }

    private fun getBooksFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = bookCollectionRepository.getAllBooks()
            _localDbBooks.postValue(books)
        }
    }
}