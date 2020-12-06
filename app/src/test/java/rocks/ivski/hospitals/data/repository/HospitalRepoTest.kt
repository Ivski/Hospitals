package rocks.ivski.hospitals.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import rocks.ivski.hospitals.data.api.ApiService
import rocks.ivski.hospitals.data.model.Hospital

class HospitalRepoTest {

    private val service: ApiService = mock(ApiService::class.java)
    lateinit var repo: HospitalRepo

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
    fun `when no characters available make api call`() {
        runBlocking {
            `when`(service.getHospitals()).thenReturn(Response.success(200, emptyList()))
            repo = HospitalRepo(service)
            assertTrue(repo.getData().isEmpty())
            repo.getHospitals()
            verify(service, times(1)).getHospitals()
        }
    }

    @Test
    fun `when characters available do not make api call`() {
        runBlocking {
            `when`(service.getHospitals()).thenReturn(Response.success(200, listOf(fakeResponse)))
            repo = HospitalRepo(service)
            // initial getCharacters() call
            repo.getHospitals()
            verify(service, times(1)).getHospitals()
            // subsequent getCharacters() call
            repo.getHospitals()
            verify(service, times(1)).getHospitals()
        }
    }

}