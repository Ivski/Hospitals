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

}