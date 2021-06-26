package com.cahyana.asep.basicrxjavakotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cahyana.asep.basicrxjavakotlin.persistence.User
import com.cahyana.asep.basicrxjavakotlin.persistence.UserDao
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.capture
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @get:Rule
    var instantTastExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataSource: UserDao

    @Captor
    private lateinit var userArgumentCaptor: ArgumentCaptor<User>

    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserViewModel(dataSource)
    }

    @Test
    fun getUserName_whenNoUserSaved() {
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.empty())

        viewModel.userName()
            .test()
            .assertNoValues()
    }

    @Test
    fun getUserName_whenUserSaved() {
        val user = User(userName = "user name")
        `when`(dataSource.getUserById(UserViewModel.USER_ID)).thenReturn(Flowable.just(user))

        viewModel.userName()
            .test()
            .assertValue("user name")
    }

    @Test
    fun updateUserName_updatesNameInDataSource() {
        viewModel.updateUserName("new user name")
            .test()
            .assertComplete()

        verify(dataSource).insertUser(capture(userArgumentCaptor))
        assertEquals(userArgumentCaptor.value.userName, "new user name")
    }
}