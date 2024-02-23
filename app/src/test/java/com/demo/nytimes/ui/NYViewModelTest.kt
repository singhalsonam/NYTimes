import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.nytimes.data.model.Articles
import com.demo.nytimes.data.model.Results
import com.demo.nytimes.ui.NYRepository
import com.demo.nytimes.ui.NYViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class NYViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: NYViewModel
    private lateinit var repository: NYRepository
    private lateinit var articlesObserver: Observer<List<Results>?>
    private lateinit var articleDetailObserver: Observer<Results?>
    private lateinit var responseObserver: Observer<Response<Articles?>>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = NYViewModel()

        // Mock LiveData observers
        articlesObserver = mockk(relaxed = true)
        articleDetailObserver = mockk(relaxed = true)
        responseObserver = mockk(relaxed = true)
        viewModel.articles.observeForever(articlesObserver)
        viewModel.articleDetail.observeForever(articleDetailObserver)
    }

    @Test
    fun `fetchMostViewedArticles should update articles LiveData with fetched data`() = runTest {
        // Given
        val mockResponse = mockk<Response<Articles?>>()
        val mockResults = arrayListOf<Results>(mockk())
        coEvery { repository.fetchMostViewedArticles() } returns mockResponse
        every { mockResponse.body()?.results } returns mockResults

        // When
        viewModel.fetchMostViewedArticles()
        // Then
        verify(exactly = 1) { articlesObserver.onChanged(mockResults) }
    }

    @Test
    fun `getArticleById should update articleDetail LiveData with selected article`() = runTest {
        // Given
        val id = 123.0
        val mockResult = mockk<Results>()
        coEvery { mockResult.id } returns id
        viewModel.articleListLiveData.value = listOf(mockResult)

        // When
        viewModel.getArticleById(id)

        // Then
        verify { articleDetailObserver.onChanged(mockResult) }}

}
