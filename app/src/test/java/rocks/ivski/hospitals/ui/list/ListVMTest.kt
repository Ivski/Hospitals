package rocks.ivski.hospitals.ui.list

import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import rocks.ivski.hospitals.data.model.Hospital
import rocks.ivski.hospitals.data.repository.HospitalRepo
import rocks.ivski.hospitals.utils.Network

class ListVMTest {

    private var repo = mock(HospitalRepo::class.java)
    private var util = mock(Network::class.java)
    private lateinit var viewModel: ListVM

    private val fakeResponse =
        Hospital(
            1,
            "RV9HE",
            "Hospital",
            "Mental Health Hospital",
            "NHS Sector",
            "Visible",
            true,
            "East Riding Community Hospital",
            "Swinemoor Lane",
            "",
            "",
            "Beverley",
            "East Yorkshire",
            "HU17 0FA",
            "53.85313415527344",
            "-0.4114723205566406",
            "RV9",
            "Humber NHS Foundation Trust",
            "01482 886600",
            "newhospital@nhs.net",
            "http://www.humber.nhs.uk",
            ""
        )
    private val fakeResponse2 =
        Hospital(
            1,
            "RV9HE",
            "Hospital",
            "Mental Health Hospital",
            "NHS Sector",
            "Visible",
            true,
            "East West Home's Best",
            "Swinemoor Lane",
            "",
            "",
            "Beverley",
            "East Yorkshire",
            "HU17 0FA",
            "53.85313415527344",
            "-0.4114723205566406",
            "RV9",
            "Humber NHS Foundation Trust",
            "01482 886600",
            "newhospital@nhs.net",
            "http://www.humber.nhs.uk",
            ""
        )
    private val fakeResponse3 =
        Hospital(
            1,
            "RV9HE",
            "Hospital",
            "Mental Health Hospital",
            "NHS Sector",
            "Visible",
            true,
            "West Riding Community Hospital",
            "Swinemoor Lane",
            "",
            "",
            "Beverley",
            "East Yorkshire",
            "HU17 0FA",
            "53.85313415527344",
            "-0.4114723205566406",
            "RV9",
            "Humber NHS Foundation Trust",
            "01482 886600",
            "newhospital@nhs.net",
            "http://www.humber.nhs.uk",
            ""
        )

    @Test
    fun `when no network available no api call is made`() {
        `when`(util.isConnected()).thenReturn(false)
        viewModel = ListVM(repo, util)

        runBlocking {
            viewModel.getHospitals()
            verify(repo, never()).getHospitals()
        }
    }

    @Test
    fun `when internet is available api call is made`() {
        `when`(util.isConnected()).thenReturn(true)
        runBlocking {
            `when`(repo.getHospitals()).thenReturn(Response.success(200, listOf(fakeResponse)))
            viewModel = ListVM(repo, util)
            viewModel.getHospitals()
            verify(repo, times(1)).getHospitals()
        }
    }

    @Test
    fun `when api returns error characters list is empty`() {
        `when`(util.isConnected()).thenReturn(true)
        runBlocking {
            `when`(repo.getHospitals()).thenReturn(
                Response.error(
                    400,
                    "{\"error\":[\"BAD REQUEST\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                )
            )
            viewModel = ListVM(repo, util)
            viewModel.getHospitals()
            assertTrue(repo.getData().isEmpty())
        }
    }

    @Test
    fun `if no names start with keyword return empty list`() {
        `when`(repo.getData()).thenReturn(listOf(fakeResponse))
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByName("B").isEmpty())
    }

    @Test
    fun `return only names starting with keyword`() {
        `when`(repo.getData()).thenReturn(
            listOf(
                fakeResponse,
                fakeResponse2,
                fakeResponse3
            )
        )
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByName("Eas").size == 2)
    }

    @Test
    fun `search filtering is case insensitive`() {
        `when`(repo.getData()).thenReturn(listOf(fakeResponse))
        viewModel = ListVM(repo, util)
        assert(viewModel.filterByName("e").size == 1)
    }

}